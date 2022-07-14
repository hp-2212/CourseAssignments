import Includes.*;
import java.util.*;

public class MerkleTree{
	
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numdocs;

	public String Build(String[] documents){
		// Implement Code here
CRF obj = new CRF(64) ;

int n = documents.length ;
Vector<TreeNode> v1 = new Vector<TreeNode> () ;
for ( int i=0 ; i<n ; ++i ){
v1.add( new TreeNode() ) ;
v1.get(i).val = documents[i] ;
}

while ( n != 1 ) {
Vector<TreeNode> temp = new Vector<TreeNode>() ;

for ( int i=0 ; i<n ; ++i ){
if ( i%2 == 1 ) continue ;

TreeNode par = new TreeNode() ;
v1.get(i).parent = par ;
v1.get(i+1).parent = par ;
par.left = v1.get(i) ;
par.right = v1.get(i+1) ;
par.val = obj.Fn(par.left.val+"#"+par.right.val) ;
temp.add(par) ;

}
n = n/2 ;
v1.clear() ;
for ( int i=0 ; i<n ; ++i ) { v1.add(temp.get(i)) ; }
}
this.rootnode = v1.get(0) ;
this.numdocs = documents.length ;

		return this.rootnode.val ;
	}

	/* 
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second, that can be set either manually or
		using the constructor

		Edit: The constructor is added
	*/
		
	public List<Pair<String,String>> QueryDocument(int doc_idx){
		// Implement Code here
int n = this.numdocs ;
List < Pair< String, String > > ls = new ArrayList< Pair<String, String> >() ;
TreeNode curr_node = new TreeNode() ;
curr_node = this.rootnode ;
int curr_idx = 0 ;

while( n>1 ) {

if ( curr_idx + n/2 < doc_idx ){
curr_idx = curr_idx + n/2 ;
curr_node = curr_node.right ;
}

else {
curr_node = curr_node.left ;
}

n = n/2 ;
}

while ( curr_node != this.rootnode) {
curr_node = curr_node.parent ;
ls.add( new Pair< String, String>(curr_node.left.val, curr_node.right.val) ) ;
}
ls.add( new Pair< String, String>(this.rootnode.val, null) ) ;
		return ls ;
	}

	public static boolean Authenticate(List<Pair<String,String>> path, String summary){
		// Implement Code here
CRF obj = new CRF(64) ;
boolean flag = true ;
int n = path.size() ;

for ( int i=0 ; i<n-1 ; ++i ){
Pair< String, String > p1 ;
p1 = path.get(i) ; 

if ( i == n-2 ) {
if( path.get(i+1).get_first().equals(obj.Fn(p1.get_first()+"#"+p1.get_second())) ) ;
else { flag = false ;}
continue ;
}

if( path.get(i+1).get_first().equals(obj.Fn(p1.get_first()+"#"+p1.get_second())) || 
                                                            path.get(i+1).get_second().equals(obj.Fn(p1.get_first()+"#"+p1.get_second())) ) ;
else { flag = false ;}
}

if ( summary.equals( path.get(n-1).get_first() ) ) ;
else  {flag = false ; }

		return flag;
	}

	public String UpdateDocument(int doc_idx, String new_document){		
		// Implement Code here
TreeNode curr_node = this.rootnode ;
int curr_idx = 0 ;
int n = this.numdocs ;
CRF obj = new CRF(64) ;

while( n>1 ) {

if ( curr_idx + n/2 >= doc_idx ){
curr_node = curr_node.left ;
}

else {
curr_node = curr_node.right ;
curr_idx += n/2 ;
}

n = n/2 ;
}

curr_node.val = new_document ;

while( this.rootnode != curr_node ){
curr_node = curr_node.parent ;
curr_node.val = obj.Fn(curr_node.left.val+"#"+curr_node.right.val) ;
}

		return this.rootnode.val ;
	}
}