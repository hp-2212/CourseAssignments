package DSCoinPackage;

import java.util.*;
import HelperClasses.* ;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {
	  
	  Transaction tobj = new Transaction() ;
	  tobj.coinID = this.mycoins.get(0).first ;
	  tobj.Source = this ;
	  
	  int i=0 ;              // Finding the Destination Member 
	  while ( true ) {
		  if ( DSobj.memberlist[i].UID.equals(destUID) ) break ;
		  i++ ;
	  }
	  tobj.Destination = DSobj.memberlist[i] ;
	  tobj.coinsrc_block = this.mycoins.get(0).second ;
	  
	  this.mycoins.remove(0) ;
	  
	  DSobj.pendingTransactions.AddTransactions(tobj);
      
	  if ( this.in_process_trans == null ) {
		  this.in_process_trans = new Transaction[100] ;
		  this.in_process_trans[0] = tobj ;
	  }
	  else {
		  int i1=0 ; 
		  while ( this.in_process_trans[i1] != null ) i1++ ;
		  this.in_process_trans[i1] = tobj ;
	  }
	  
  }


  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
	  
	  TransactionBlock tB = new TransactionBlock() ;
	  tB.previous = DSObj.bChain.lastBlock ;
	  boolean flag = true ;
	  int i=0 ;
	  try {
		  while (flag) {
			  tB = tB.previous ;
			  for ( i=0 ; i<tB.trarray.length ; ++i ) {
				  if ( Transaction.ifequaltrans( tB.trarray[i], tobj)) { flag = false ; break ; }
			  }
		  }
	  }
	  catch (Exception e) { throw new MissingTransactionException() ; }
	  
	  TreeNode curr = new TreeNode() ;
	  curr = tB.Tree.rootnode ;
	  int n = tB.trarray.length, temp = 0 ;
	 
	  while ( n>1 ) {
		  if ( temp + n/2 < i ) { temp += n/2 ; curr = curr.right ; }
		  else {  curr = curr.left ;}
		  n = n/2 ;
	  }
	  
	  List < Pair <String,String> > l1 = new ArrayList < Pair <String,String> >() ;
	  while ( curr.parent != null ) {
		  if ( curr.parent.left == curr ) l1.add( new Pair <String,String> ( curr.val, curr.parent.right.val) ) ;
		  else l1.add( new Pair <String,String> ( curr.parent.left.val, curr.val ) ) ;
		  curr = curr.parent ;
	  }
	  l1.add( new Pair <String,String> (curr.val, null ) ) ; // <- The first List.
	  
	  List< Pair<String, String>> l2 = new ArrayList< Pair<String, String>> () ;
	  TransactionBlock tB1 = DSObj.bChain.lastBlock ;
	  
	  while ( tB1 != tB ) {
		  l2.add(new Pair<String, String>(tB1.dgst, tB1.previous.dgst+"#"+tB1.trsummary+"#"+tB1.nonce) ) ;
		  tB1 = tB1.previous ; 
	  }
	  if ( tB.previous == null ) {
		  l2.add( new Pair <String,String>(tB.dgst, DSObj.bChain.start_string+"#"+tB.trsummary+"#"+tB.nonce)) ;
		  l2.add( new Pair <String,String>(DSObj.bChain.start_string,null)) ;
	  }
	  else {
		  l2.add( new Pair <String,String>(tB.dgst, tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce)) ;
		  l2.add( new Pair <String,String>(tB.previous.dgst, null)) ;
	  }
	  Collections.reverse(l2) ;
	  
	  Transaction[] tr1 = new Transaction[100] ;
	  i = 0 ;
	  for ( int i1=0 ; this.in_process_trans[i1] != null  ; ++i1 ) {
		  if ( Transaction.ifequaltrans(this.in_process_trans[i1], tobj) ) continue ;
		  tr1[i] = this.in_process_trans[i1] ;
		  i++ ;
	  }
	  this.in_process_trans = tr1 ;                    // in_process_trans is assigned a new array with no tobj.
	  
	  for ( i=0 ; i<tobj.Destination.mycoins.size() ; ++i ) {
		  if ( Integer.valueOf(tobj.Destination.mycoins.get(i).first) > Integer.valueOf(tobj.coinID) ) break ;
	  }
	  tobj.Destination.mycoins.add(i, new Pair<String,TransactionBlock>(tobj.coinID, tB) ) ;

    return new Pair < List <Pair<String,String>>, List <Pair<String,String>> >(l1,l2) ;
  }

  public void MineCoin(DSCoin_Honest DSObj) throws EmptyQueueException {
	  
	  Transaction[] t1 = new Transaction [DSObj.bChain.tr_count] ;
	  List <String> l1 = new ArrayList<String> () ;
	  l1.add("A") ;                             // Just a dummy to escape Empty List Exception.
	  int i=0, n = DSObj.bChain.tr_count ;
	  while ( i<n-1 ) {
		  Transaction tr = DSObj.pendingTransactions.RemoveTransaction() ;
		  boolean flag = true ; 
		  for ( int j=0 ; j<l1.size() ; ++j ) { if ( l1.get(j).equals(tr.coinID) ) flag = false ; }
		  if ( flag ) { t1[i] = tr ; i++ ; l1.add(tr.coinID) ; }
	  }
	  
	  Transaction mrt = new Transaction() ;           // mrt = Miner Reward Transaction.
	  mrt.coinID = String.valueOf( Integer.valueOf(DSObj.latestCoinID ) + 1 ) ;
	  mrt.Source = null ;                     
	  mrt.Destination = this ;
	  mrt.coinsrc_block = null ;
	  t1[n-1] = mrt ;                                 // Last Transaction of the block.
	  
	  TransactionBlock newBlock = new TransactionBlock(t1) ;
	  DSObj.bChain.InsertBlock_Honest(newBlock);
      DSObj.latestCoinID = mrt.coinID ;
      this.mycoins.add( new Pair<String,TransactionBlock>( mrt.coinID, null ) ) ;
  }  

  public void MineCoin(DSCoin_Malicious DSObj) throws EmptyQueueException {
	  
	  Transaction[] t1 = new Transaction [DSObj.bChain.tr_count] ;
	  List <String> l1 = new ArrayList<String> () ;
	  l1.add("A") ;                             // Just a dummy to escape Empty List Exception.
	  int i=0, n = DSObj.bChain.tr_count ;
	  while ( i<n-1 ) {
		  Transaction tr = DSObj.pendingTransactions.RemoveTransaction() ;
		  boolean flag = true ;
		  for ( int j=0 ; j<l1.size() ; ++j ) { if ( l1.get(j).equals(tr.coinID) ) flag = false ; }
		  if ( flag ) { t1[i] = tr ; i++ ; l1.add(tr.coinID) ; }
	  }
	  
	  Transaction mrt = new Transaction() ;           // mrt = Miner Reward Transaction.
	  mrt.coinID = String.valueOf( Integer.valueOf(DSObj.latestCoinID ) + 1 ) ;
	  mrt.Source = null ;
	  mrt.Destination = this ;
	  mrt.coinsrc_block = null ;
	  t1[n-1] = mrt ;                                 // Last Transaction of the block.
	  
	  TransactionBlock newBlock = new TransactionBlock(t1) ;
	  DSObj.bChain.InsertBlock_Malicious(newBlock);
      DSObj.latestCoinID = mrt.coinID ;
      this.mycoins.add( new Pair<String,TransactionBlock>( mrt.coinID, null ) ) ;

  }  
}
