import Includes.*;
import java.util.*;

public class BlockChain{
	public static final String start_string = "LabModule5";
	public Block firstblock;
	public Block lastblock;

	public String InsertBlock(List<Pair<String,Integer>> Documents, int inputyear){
		/*
			Implement Code here
		*/
Block b1 = new Block() ;
MerkleTree m1 = new MerkleTree() ;
String s1 = m1.Build( Documents ) ;
int s2 = m1.rootnode.maxleafval ;
b1.value = s1+"_"+s2 ;
b1.year = inputyear ;
b1.mtree = m1 ;

CRF obj = new CRF(64) ;

if (  this.firstblock == null ) {

this.firstblock = b1 ;
this.lastblock = b1 ;
b1.dgst = obj.Fn( BlockChain.start_string+"#"+b1.value ) ;

}

else {

this.lastblock.next = b1 ;
b1.previous = this.lastblock ;
this.lastblock = b1 ;
b1.dgst = obj.Fn( b1.previous.dgst+"#"+b1.value ) ;

}

		return b1.dgst;
	}

	public Pair<List<Pair<String,String>>, List<Pair<String,String>>> ProofofScore(int student_id, int year){
		// Implement Code here

Block b1 = this.firstblock ;
while ( b1.year != year ) { b1 = b1.next ;  }

TreeNode curr = b1.mtree.rootnode ;
student_id = student_id + 1 ;
int temp = 0 ;
int n = b1.mtree.numstudents ;

while ( n != 1 ){

if ( student_id > temp + n/2 ) { curr = curr.right ; temp += n/2 ;}
else curr = curr.left ;
n = n/2 ;

}

List< Pair<String, String> >l1 = new ArrayList< Pair<String,String> >(), l2 = new ArrayList< Pair<String,String> >() ;
Pair< String, String > p1 ;

while ( curr != b1.mtree.rootnode ) {

if ( curr == curr.parent.left ) p1 = new Pair<String, String>( curr.val, curr.parent.right.val ) ;
else                            p1 = new Pair<String, String>( curr.parent.left.val, curr.val ) ;
l1.add(p1) ;
curr = curr.parent ;

}
l1.add( new Pair<String,String> (b1.mtree.rootnode.val, null) ) ;

while ( b1 != null ){
p1 = new Pair<String, String>( b1.value, b1.dgst) ;
l2.add(p1) ;
b1 = b1.next ;
}

		return new Pair< List< Pair<String,String> >, List< Pair<String,String> > >(l1,l2) ;
	}
}
