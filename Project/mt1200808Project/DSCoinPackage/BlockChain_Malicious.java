package DSCoinPackage;

import HelperClasses.CRF;
import HelperClasses.Pair;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
	  
	  boolean correct = true ;
	  CRF obj = new CRF(64) ;
	  correct &= tB.dgst.substring(0,4).equals("0000") ;  // Checking the first four digits.
	  
	  if ( tB.previous == null ) correct &= tB.dgst.equals( obj.Fn(start_string+"#"+tB.trsummary+"#"+tB.nonce) ) ;
	  else correct &= tB.dgst.equals( obj.Fn(tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce) ) ;
	  
	  for (int i=0 ; i<tB.trarray.length ; ++i ) correct &= tB.checkTransaction( tB.trarray[i] ) ;
	 
	  int n = tB.trarray.length ;
	  String[] s1 = new String[n], s2 = new String[n] ;
	  // Checking For tB.trsummary using tB.trarray
	  
	  for ( int i=0 ; i<n ; ++i ) {
		  if ( tB.trarray[i].coinsrc_block != null ) s1[i] = obj.Fn(tB.trarray[i].coinID+"#"+tB.trarray[i].Source.UID+"#"+tB.trarray[i].Destination.UID+"#"+tB.trarray[i].coinsrc_block.dgst) ;
		  else s1[i] = obj.Fn(tB.trarray[i].coinID+"#"+tB.trarray[i].Source.UID+"#"+tB.trarray[i].Destination.UID+"#"+"Genesis") ;
	  }
	  
	  n = n/2 ;
	  while( n>0 ) {
		  for ( int i=0 ; i<n ; ++i ) s2[i] = obj.Fn( s1[2*i]+"#"+s1[(2*i)+1] ) ;
		  for ( int i=0 ; i<n ; ++i ) s1[i] = s2[i] ;
		  n = n/2 ;
	  }
	  correct &= tB.trsummary.equals(s1[0]) ;
	  
    return correct ;
  }

  public TransactionBlock FindLongestValidChain () {
	  
	  if ( this.lastBlocksList == null ) return null ;
	  
	  int n = this.lastBlocksList.length , max_count = Integer.MIN_VALUE, max_idx = 0;
	  TransactionBlock[] mylastBlocks = new TransactionBlock[n] ;
	  
	  for ( int i=0 ; i<n ; ++i ) {
		  if ( this.lastBlocksList[0] == null ) return null ;
		  int counter = 0 ;
		  TransactionBlock tb = this.lastBlocksList[i] ;
		  
		  while ( tb != null ) {
			  if ( BlockChain_Malicious.checkTransactionBlock(tb) ) { counter++ ; }
			  else { counter = 0 ; mylastBlocks[i] = null ; }
			  if ( counter == 1 ) mylastBlocks[i] = tb ;
			  tb = tb.previous ;
		  }
		  
		  if ( max_count < counter ) {
			  max_count = counter ;
			  max_idx = i ;
		  }
		  
	  }
	  
    return mylastBlocks[ max_idx ];
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {
	 
	  TransactionBlock last = this.FindLongestValidChain() ; 

	  if ( last == null ) {
		  long n = 1000000000 ;
		  String s1 = null ;
		  CRF obj = new CRF(64) ;
		  boolean flag = true ;
		  while(flag) {
			  n++ ;
			  s1 = obj.Fn("DSCoin"+"#"+newBlock.trsummary+"#"+String.valueOf(n)) ;
			  if ( s1.substring(0,4).equals("0000")) { flag = false ; }
		  }
		  newBlock.dgst = s1 ;
		  newBlock.nonce = String.valueOf(n) ;
		  newBlock.previous = null ;
		  this.lastBlocksList = new TransactionBlock[100] ;
		  this.lastBlocksList[0] = newBlock ;
	  }
	  else {
		  long n = 1000000000 ;
		  String s1 = null ;
		  CRF obj = new CRF(64) ;
		  boolean flag = true ;
		  while(flag) {
			  n++ ;
			  s1 = obj.Fn(last.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n)) ;
			  if ( s1.substring(0,4).equals("0000")) flag = false ;
		  }
		  newBlock.dgst = s1 ;
		  newBlock.nonce = String.valueOf(n) ;
		  newBlock.previous = last ;
		  
		  Pair<Boolean, Integer> p1 = this.myFindLongestValidChain () ;
		  if (p1.first) {
			  this.lastBlocksList[ p1.second ] = newBlock ;
		  }
		  else {
			  for ( int i=0 ; i<n ; ++i ) 
				  if ( this.lastBlocksList[i]  == null ) { this.lastBlocksList[i] = newBlock ; break ; }   
		  }
	  }
  }
  
public Pair<Boolean, Integer> myFindLongestValidChain () {
	  
	  if ( this.lastBlocksList == null ) return null ;
	  
	  int n = this.lastBlocksList.length , max_count = Integer.MIN_VALUE, max_idx = 0;
	  boolean flag3 = true, flag2 = true ;
	  TransactionBlock[] mylastBlocks = new TransactionBlock[n] ;
	  
	  for ( int i=0 ; i<n ; ++i ) {
		  if ( this.lastBlocksList[0] == null ) return null ;
		  int counter = 0 ;
		  flag2 = true ;
		  TransactionBlock tb = this.lastBlocksList[i] ;
		  
		  while ( tb != null ) {
			  if ( BlockChain_Malicious.checkTransactionBlock(tb) ) counter++ ;
			  else { counter = 0 ; mylastBlocks[i] = null ; flag2 = false ; }
			  if ( counter == 1 ) mylastBlocks[i] = tb ;
			  tb = tb.previous ;
		  }
		  
		  if ( max_count < counter ) {
			  max_count = counter ;
			  max_idx = i ;
			  flag3 = flag2 ;
		  }
		  
	  }
	  
    return  new Pair<Boolean, Integer> ( flag3, max_idx) ;
  }
}
