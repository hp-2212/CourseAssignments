import Includes.*;

public class AuthStack{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	private static final String start_string = "2020MT10808";
	private StackNode top;

	/*
		Note that the Exceptions have already been defined for you in the includes file,
		you just have to raise them accordingly

	*/

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class. 	
	*/
	public static boolean CheckStack(AuthStack current, String proof) throws AuthenticationFailedException{
		
if ( current.top.dgst != proof ) throw new AuthenticationFailedException() ;
StackNode curr = new StackNode() ;
curr = current.top ; 
CRF obj = new CRF(64) ;

while (curr.next != null ) {
String s1 = curr.dgst ; 
String s2 = obj.Fn(curr.next.dgst+"#"+curr.data.value) ;
if ( !s1.equals(s2) ) throw new AuthenticationFailedException() ;
curr = curr.next ;
}
String s1 = curr.dgst ; 
String s2 = obj.Fn(AuthStack.start_string+"#"+curr.data.value) ;
if ( !s1.equals(s2)  ) throw new AuthenticationFailedException() ;
return true ;

	}


	public String push(Data datainsert, String proof) throws AuthenticationFailedException{

CRF obj = new CRF(64) ;
if ( this.top == null ) {
this.top = new StackNode() ;
this.top.data = datainsert ;
this.top.dgst = obj.Fn(AuthStack.start_string+"#"+datainsert.value) ;
this.top.next = null ;
return this.top.dgst ;
}

else {

boolean flag = AuthStack.CheckStack(this,proof) ;

if (flag) {
StackNode CurrNode = new StackNode() ;
CurrNode.data = datainsert ;
CurrNode.dgst = obj.Fn(this.top.dgst+"#"+datainsert.value) ;
CurrNode.next = this.top ;
this.top = CurrNode ;
return CurrNode.dgst ;
}

else throw new AuthenticationFailedException() ;
}
		
	}

	public String pop(String proof)throws AuthenticationFailedException, EmptyStackException{
		
if ( this.top != null ) {

if ( AuthStack.CheckStack(this,proof) ) {
this.top = this.top.next ;
return this.top.dgst ;
}

else throw new AuthenticationFailedException() ;

}

else throw new EmptyStackException() ;
		
	}

	public StackNode GetTop(String proof)throws AuthenticationFailedException, EmptyStackException{
if ( this.top != null ) {

if ( AuthStack.CheckStack(this,proof) ) {
return this.top ;
}

else throw new AuthenticationFailedException() ;
}
else throw new EmptyStackException() ;
	}
}