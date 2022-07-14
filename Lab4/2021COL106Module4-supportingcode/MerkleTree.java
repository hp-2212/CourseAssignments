import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{
	//check TreeNode.java for more details
	public TreeNode rootnode;
	public int numdocs;
	

	public String InsertDocument(String document){

if ( this.rootnode == null ) {
TreeNode curr = new TreeNode() ;
curr.val = document ;
this.rootnode = curr ;
}

else if ( this.rootnode.left == null ) {
TreeNode curr_new1 = new TreeNode(), curr_new2 = new TreeNode() ;
curr_new1.val = MerkleTree.Get_Min( this.rootnode.val, document) ;
curr_new2.val = MerkleTree.Get_Max( this.rootnode.val, document) ;

curr_new1.parent = this.rootnode ;
curr_new2.parent = this.rootnode ;
this.rootnode.left   = curr_new1 ;
this.rootnode.right  = curr_new2 ;

}

else {
TreeNode first = new TreeNode(), curr = new TreeNode() ;
first = this.rootnode ;
while ( first.left != null ) first = first.left ;

if ( document.equals( MerkleTree.Get_Min( this.rootnode.minleafval, document ) ) ) {
TreeNode curr_new1 = new TreeNode(), curr_new2 = new TreeNode() ;
curr_new1.val = document ;
curr_new2.val = first.val ;

curr_new1.parent = first ;
curr_new2.parent = first ;
first.left   = curr_new1 ;
first.right  = curr_new2 ;

}

else {
TreeNode curr_new1 = new TreeNode(), curr_new2 = new TreeNode() ;
curr = MerkleTree.geF( this.rootnode, document ) ;
curr_new1.val = document ;
curr_new2.val = curr.val ;

curr_new1.parent = curr ;
curr_new2.parent = curr ;
curr.right  = curr_new1 ;
curr.left   = curr_new2 ;

}

}

MerkleTree.Update_isLeaf( this.rootnode ) ;
MerkleTree.Update_numberLeaves( this.rootnode ) ;
MerkleTree.Update_maxleafval( this.rootnode ) ;
MerkleTree.Update_minleafval( this.rootnode ) ;
MerkleTree.Update_val( this.rootnode ) ;
MerkleTree.Update_balanceFactor( this.rootnode ) ;
this.Recover_Balance( this.rootnode ) ;                      // Also, updates the other attributes.

		return this.rootnode.val; 
	}

public static void Update_isLeaf ( TreeNode root ) {

if ( root == null ) return ;
if ( root.left == null ) { root.isLeaf = true ; return ; }

Update_isLeaf(root.left ) ;
Update_isLeaf(root.right) ;
root.isLeaf = false ;
return ;

}

public static void Update_numberLeaves ( TreeNode root ) {

if ( root.left == null ) { root.numberLeaves = 1 ; return ; }

Update_numberLeaves(root.left ) ;
Update_numberLeaves(root.right) ;
root.numberLeaves = root.left.numberLeaves + root.right.numberLeaves ;
return ;

}

public static void Update_maxleafval ( TreeNode root ) {

if ( root == null ) return ;
if ( root.isLeaf ) { root.maxleafval = root.val ; return ; }

Update_maxleafval( root.left )  ;
Update_maxleafval( root.right ) ;
root.maxleafval = MerkleTree.Get_Max( root.left.maxleafval, root.right.maxleafval ) ;
return ;

}

public static void Update_minleafval ( TreeNode root ) {

if( root == null ) return ;
if ( root.isLeaf ) { root.minleafval = root.val ; return ; }

Update_minleafval( root.left ) ;
Update_minleafval( root.right );
root.minleafval = MerkleTree.Get_Min ( root.left.minleafval, root.right.minleafval ) ;
return ;

}

public static void Update_val ( TreeNode root ) {

CRF obj = new CRF(64) ;

if ( root.isLeaf ) { return ; }

Update_val( root.left ) ;
Update_val( root.right) ;
root.val = obj.Fn (root.left.val+"#"+root.right.val) ;
return ;

}


public static int height_tree ( TreeNode root ) {

if ( root == null ) return -1 ;
if ( root.isLeaf ) return 0 ;
else return 1 + Math.max( height_tree( root.left ) , height_tree( root.right ) ) ;

}

public static void Update_balanceFactor( TreeNode root ) {

if ( root.isLeaf ) { root.balanceFactor = 0 ; return ; }
else root.balanceFactor = height_tree( root.left ) - height_tree( root.right ) ;

Update_balanceFactor( root.left  ) ;
Update_balanceFactor( root.right ) ;
return ;

}

public void Recover_Balance ( TreeNode root ) {
TreeNode curr = badNode(root), new_root = new TreeNode() ;
Boolean flag = false ;
if ( curr == null ) return ;
else if ( curr.parent == null ){ flag = true ;  }

boolean flag1 = false, flag2 = false ;

if ( height_tree(curr.left) < height_tree(curr.right) ) {
flag1 = true ; 
if ( height_tree(curr.right.left) < height_tree(curr.right.right) ) flag2 = true ;
}

else {
if ( height_tree(curr.left.left) < height_tree(curr.left.right) ) { flag2 = true ; }
}

if ( flag1 && flag2 ) { new_root = root.SingleLeftRotation() ; }
if ( flag1 && !flag2 ) { new_root = root.DoubleRightLeftRotation() ;}
if ( flag2 && !flag1 ) { new_root = root.DoubleLeftRightRotation() ;}
if ( !flag1 && !flag2 ) { new_root = root.SingleRightRotation() ;}

if ( flag ) this.rootnode = new_root ;

MerkleTree.Update_isLeaf( this.rootnode ) ; 
MerkleTree.Update_numberLeaves( this.rootnode ) ;
MerkleTree.Update_maxleafval( this.rootnode ) ;
MerkleTree.Update_minleafval( this.rootnode ) ;
MerkleTree.Update_val( this.rootnode ) ;
MerkleTree.Update_balanceFactor( this.rootnode ) ;

}

public static TreeNode badNode ( TreeNode root ) {

if ( root.isLeaf ) return null ;

TreeNode curr1, curr2 ;
curr1 = badNode( root.left ) ;
curr2 = badNode( root.right );

if ( curr1 != null ) return curr1 ;
else if ( curr2 != null ) return curr2 ;
else if ( root.balanceFactor > 1 || root.balanceFactor < -1 ) return root ;
else return null ;

}

public static String Get_Min ( String s1, String s2 ) {

int n = Math.min( s1.length(), s2.length() ) ;
String res = null ;
boolean flag = false ;

for ( int i=0 ; i<n ; ++i ) {
if ( s1.charAt(i) > s2.charAt(i) ) { res = s2 ; flag = true ; break ; }
else if ( s1.charAt(i) < s2.charAt(i) ) { res = s1 ; flag = true ; break ; }
}

if ( flag ) {
return res ;
}

else {
if ( n == s1.length() ) return s1 ; 
else return s2 ; 
}
}

public static String Get_Max ( String s1, String s2 ) {

int n = Math.min( s1.length(), s2.length() ) ;
String res = null ;
boolean flag = false ;

for ( int i=0 ; i<n ; ++i ) {
if ( s1.charAt(i) < s2.charAt(i) ) { res = s2 ; flag = true ; break ; }
else if ( s1.charAt(i) > s2.charAt(i) ) { res = s1 ; flag = true ; break ; }
}

if ( flag ) {
return res ;
}

else {
if ( n == s1.length() ) return s2 ;
else return s1 ;
}

}

public static TreeNode geF ( TreeNode root, String document ) {

if ( root.isLeaf ) return root ;
else if ( document.equals( Get_Min(root.right.minleafval, document ) ) ) return geF(root.left, document) ;
else return geF(root.right, document) ;
 
}
	public String DeleteDocument(String document){
		//Implement your code here
		return "";
	}
}


