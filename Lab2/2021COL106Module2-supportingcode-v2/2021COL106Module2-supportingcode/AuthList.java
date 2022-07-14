import java.util.Random;

import Includes.*;

public class AuthList{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	public static final String start_string = "2020MT10808";
	public Node firstNode;
	public Node lastNode;

	/*
		Note that the Exceptions have already been defined for you in the includes file,
		you just have to raise them accordingly

	*/

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static boolean CheckList(AuthList current, String proof) throws AuthenticationFailedException {
		CRF obj = new CRF(64);
		Node curr = current.firstNode;
		boolean initial = true;
		while(curr != null){
			if(initial){
				String hsh = obj.Fn(AuthList.start_string + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh)) {
					throw new AuthenticationFailedException();
				}
				initial = false;
				curr = curr.next;
			}else if(curr == current.lastNode){
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh) || !curr.dgst.equals(proof)) {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}else{
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh))  {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}
		}
		return true;
	}


	public String InsertNode(Data datainsert, String proof) throws AuthenticationFailedException {
		
boolean flag = CheckList (this, proof) ;

if ( flag ) {
CRF obj = new CRF(64) ;

if ( proof == null ) {
Node new_node = new Node() ;
this.firstNode = new_node ;
this.lastNode = new_node ;
this.firstNode.previous = null ;
this.firstNode.next = null ;
this.firstNode.data = datainsert ;
this.firstNode.dgst = obj.Fn(AuthList.start_string+"#"+datainsert.value) ;
return new_node.dgst ;
}

else {
Node new_node = new Node() ;
new_node.previous = this.lastNode ;
new_node.next = null ;
this.lastNode.next = new_node ;
new_node.data = datainsert ;  //Is this Correct --> new_node.data = new datainsert ; ??
new_node.dgst = obj.Fn( this.lastNode.dgst+"#"+datainsert.value) ;
this.lastNode = new_node ;
return new_node.dgst ;
}

}
else throw new AuthenticationFailedException() ;
		
	}

	public String DeleteFirst(String proof) throws AuthenticationFailedException, EmptyListException {

if ( this.firstNode != null ){

boolean flag = CheckList (this, proof) ;

if (flag) {

if ( this.firstNode.next == null ) { 
this.firstNode = null ;
return null ;
}

else {

this.firstNode = this.firstNode.next ; 
Node curr_node = new Node() ;
curr_node = this.firstNode ;
CRF obj = new CRF(64) ;
curr_node.dgst = obj.Fn(AuthList.start_string+"#"+curr_node.data.value) ;
curr_node = this.firstNode.next ;

while ( curr_node != null ) {
curr_node.dgst = obj.Fn(curr_node.previous.dgst+"#"+curr_node.data.value) ;
curr_node = curr_node.next ;
}

return this.lastNode.dgst ;
}

}

else throw new AuthenticationFailedException() ;

}

else throw new EmptyListException() ;
	}


	public String DeleteLast(String proof) throws AuthenticationFailedException, EmptyListException {
		
if ( this.firstNode != null ) {

boolean flag = CheckList (this, proof) ;

if (flag) {

if ( this.lastNode.previous != null ) {
this.lastNode = this.lastNode.previous ;
return this.lastNode.dgst ;
}

else {
this.lastNode = null ;
this.firstNode = null ;
return null ;
}

}

else throw new AuthenticationFailedException() ;

}

else throw new EmptyListException() ;
		
	}

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static Node RetrieveNode(AuthList current, String proof, Data data) throws AuthenticationFailedException, DocumentNotFoundException{
		
boolean flag = CheckList(current, proof) ;
if (flag) {

Node currNode = new Node() ;
currNode = current.firstNode ;

while ( currNode != null ) {
if ( currNode.data == data ) { break ; }
currNode = currNode.next ;
}

if ( currNode == null ) throw new DocumentNotFoundException() ;
else return currNode ;
}

else throw new AuthenticationFailedException() ;

	}

	public void AttackList(AuthList current, String new_data)throws EmptyListException{
		
CRF obj = new CRF(5) ;
String s1 = current.firstNode.data.value, temp ="" ;
String req_dgst = obj.Fn(s1) ;
Random ran = new Random() ;
String[] ary = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P" } ;

boolean flag = true ;
while (flag) {
temp = "" ;
for ( int i=0 ; i<obj.outputsize ; ++i ) {
temp = temp + ary[ ran.nextInt(15) ] ;
}

if ( obj.Fn(temp).equals(req_dgst) && !(temp.equals(s1)) ) flag = false ;

}

current.firstNode.data.value = temp ;
System.out.println(current.firstNode.data.value) ;
		
	}

}
