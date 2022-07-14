import Includes.*;
import java.util.*;

public class MerkleTree{

	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numstudents;

	public String Build(List<Pair<String,Integer>> documents){
		// Implement Code here

List < TreeNode > ls1 = new ArrayList< TreeNode >(), ls2 = new Vector< TreeNode >() ;

int n = documents.size() ;
for ( int i=0 ; i<n ; ++i ) {

TreeNode curr = new TreeNode() ;
curr.val = documents.get(i).First+"_"+documents.get(i).Second ;
curr.maxleafval = documents.get(i).Second ;
curr.numberLeaves = 1 ;
curr.isLeaf = true ;
ls1.add( curr ) ;

}

int h = (int) (Math.log(n) / Math.log(2));

for ( int j=0 ; j<h ; ++j ) {
for ( int i=0 ; i<n ; ++i ) {

if ( i%2 == 1 ) continue ;

CRF obj = new CRF(64) ;
TreeNode curr = new TreeNode() ;
curr.val = obj.Fn(ls1.get(i).val + "#" + ls1.get(i+1).val ) ;
curr.maxleafval = Math.max ( ls1.get(i).maxleafval, ls1.get(i+1).maxleafval ) ;
curr.numberLeaves = ls1.get(i).numberLeaves + ls1.get(i+1).numberLeaves ;
curr.isLeaf = false ;
curr.left = ls1.get(i)   ; curr.right = ls1.get(i+1)  ;
ls1.get(i).parent = curr ; ls1.get(i+1).parent = curr ;
ls2.add( curr ) ;

}
ls1.clear() ;
n = n/2 ;
for ( int i=0 ; i<n ; ++i ) ls1.add( ls2.get(i) ) ;
ls2.clear() ;

}

this.rootnode = ls1.get(0) ;
this.numstudents = documents.size() ;

		return this.rootnode.val ;
	}

	/*
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second, that can be set either manually or
		using the constructor

		Edit: The constructor is added
	*/


	public String UpdateDocument(int student_id, int newScore){
		// Implement Code here
student_id = student_id + 1 ;
int n = this.numstudents, temp = 0 ;
TreeNode curr = this.rootnode ;

while ( n != 1 ) {
if ( student_id > temp + n/2 ) { temp = temp+n/2 ; curr = curr.right ; }
else curr = curr.left ;
n = n/2 ;
}

MerkleTree.updateScore ( curr, newScore) ;
MerkleTree.updateAncestors ( curr ) ;

		return this.rootnode.val ;
	}

public static void updateScore( TreeNode curr, int newScore ){

String s1 = curr.val ;
s1 = MerkleTree.my_reverse(s1) ;
int i=0, l1 = s1.length() ;
boolean flag = true ;

while ( flag ) { if ( s1.charAt(i) == '_' ) flag = false ; ++i; } 

s1 = s1.substring(i,l1 ) ;


s1 = MerkleTree.my_reverse(s1) ;
s1 = s1 + "_" + newScore ;
curr.val = s1 ;
curr.maxleafval = newScore ;

}

public static String my_reverse ( String s1 ) {

String s2 = "" ;
int l1 = s1.length()  ;
for ( int i=l1-1 ; i>=0 ; --i ){
s2 += s1.charAt(i) ;
}

return s2 ;
}

public static void updateAncestors( TreeNode curr ) {

CRF obj = new CRF(64) ;
curr = curr.parent ;

while ( curr != null ) {

curr.val = obj.Fn( curr.left.val + "#" + curr.right.val ) ;
curr.maxleafval = Math.max( curr.left.maxleafval, curr.right.maxleafval ) ;
curr = curr.parent ;

}

}
}
