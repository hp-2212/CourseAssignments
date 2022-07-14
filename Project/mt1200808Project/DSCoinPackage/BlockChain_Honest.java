package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
	  
	  if ( this.lastBlock == null ) {
		  long n = 1000000000 ;
		  String s1 = null ;
		  CRF obj = new CRF(64) ;
		  boolean flag = true ;
		  while(flag) {
			  n++ ;
			  s1 = obj.Fn("DSCoin"+"#"+newBlock.trsummary+"#"+String.valueOf(n)) ;
			  if ( s1.substring(0,4).equals("0000")) flag = false ;
		  }
		  newBlock.dgst = s1 ;
		  newBlock.nonce = String.valueOf(n) ;
		  newBlock.previous = null ;
		  this.lastBlock = newBlock ;
	  }
	  else {
		  long n = 1000000000 ;
		  String s1 = null ;
		  CRF obj = new CRF(64) ;
		  boolean flag = true ;
		  while(flag) {
			  n++ ;
			  s1 = obj.Fn(this.lastBlock.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n)) ;
			  if ( s1.substring(0,4).equals("0000")) flag = false ;
		  }
		  newBlock.dgst = s1 ;
		  newBlock.nonce = String.valueOf(n) ;
		  newBlock.previous = this.lastBlock ;
		  this.lastBlock = newBlock ;
	  }
  }
}
