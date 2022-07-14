package DSCoinPackage;

import java.util.ArrayList;
import java.util.List;
import HelperClasses.Pair;

public class Moderator
 {

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) throws EmptyQueueException {
	  
	  String nextCoinID = "100000" ;         // The first coinID
	  Members m1 = new Members() ;           // The Moderator Member 
	  m1.UID = "Moderator" ;
	  Transaction t1 ;
	  int k = 0 ;
	  
	  for (int i=0 ; i<coinCount ; ++i ) {
		  t1 = new Transaction() ;
		  t1.coinID = nextCoinID ;
		  t1.Source = m1 ;
		  t1.Destination = DSObj.memberlist[k] ;
		  t1.coinsrc_block = null ;
		  DSObj.pendingTransactions.AddTransactions(t1);   // The new Transaction initialized and added to pendingTransactions
		  
		  k++ ;
		  if ( k >= DSObj.memberlist.length ) k = 0 ;
		  nextCoinID = String.valueOf( 1+Integer.valueOf( nextCoinID )) ; // nextCoinID increased 
	  }
	  
	  for ( int i=0 ; i< coinCount/DSObj.bChain.tr_count ; ++i ) {
		  
		  Transaction[] tr1 = new Transaction[ DSObj.bChain.tr_count ] ;
		  int j=0 ;
		  while ( j < DSObj.bChain.tr_count ) {
			  tr1[j] = DSObj.pendingTransactions.RemoveTransaction() ;
			  j++ ; 
		  }
		  TransactionBlock tB = new TransactionBlock(tr1) ;
		  j = 0 ;
		  while ( j<DSObj.bChain.tr_count ) {
			  Members m2 = tr1[j].Destination ;
			                                           // Adding coins to mycoins for destination members.
			  if ( m2.mycoins == null ) {
				  m2.mycoins = new ArrayList <Pair <String, TransactionBlock>>() ;
				  m2.mycoins.add( new Pair<String, TransactionBlock>(tr1[j].coinID, tB)) ;
			  }
			  else { m2.mycoins.add( new Pair<String, TransactionBlock>(tr1[j].coinID, tB)) ; }     // Simply adding helps. Can be proved that the coins are in ascending Order.
			j++ ;
		  }
		  DSObj.bChain.InsertBlock_Honest(tB) ;
	  }
	// The InsertBlock_Honest also updates the LastBlocks of the BlockChain. Please Check.
	  DSObj.latestCoinID = String.valueOf( Integer.valueOf(nextCoinID)-1 ) ;

  }
    
  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) throws EmptyQueueException {
	  
	  String nextCoinID = "100000" ;         // The first coinID
	  Members m1 = new Members() ;           // The Moderator Member 
	  m1.UID = "Moderator" ;
	  Transaction t1 ;
	  int k = 0 ;
	  
	  for (int i=0 ; i<coinCount ; ++i ) {
		  t1 = new Transaction() ;
		  t1.coinID = nextCoinID ;
		  t1.Source = m1 ;
		  t1.Destination = DSObj.memberlist[k] ;
		  t1.coinsrc_block = null ;
		  DSObj.pendingTransactions.AddTransactions(t1);   // The new Transaction initialized and added to pendingTransactions
		  
		  k++ ;
		  if ( k >= DSObj.memberlist.length ) k = 0 ;
		  nextCoinID = String.valueOf( 1+Integer.valueOf( nextCoinID )) ; // nextCoinID increased 
	  }
	  
	  for ( int i=0 ; i< coinCount/DSObj.bChain.tr_count ; ++i ) {
		  
		  Transaction[] tr1 = new Transaction[ DSObj.bChain.tr_count ] ;
		  int j=0 ;
		  while ( j < DSObj.bChain.tr_count ) {
			  tr1[j] = DSObj.pendingTransactions.RemoveTransaction() ;
			  j++ ;  
		  }
		  TransactionBlock tB = new TransactionBlock(tr1) ;

		  j = 0 ;
		  while ( j<DSObj.bChain.tr_count ) {
			  Members m2 = tr1[j].Destination ;
			                                           // Adding coins to mycoins for destination members.
			  if ( m2.mycoins == null ) {
				  m2.mycoins = new ArrayList <Pair <String, TransactionBlock>>() ;
				  m2.mycoins.add( new Pair<String, TransactionBlock>(tr1[j].coinID, tB)) ;
			  }
			  else m2.mycoins.add( new Pair<String, TransactionBlock>(tr1[j].coinID, tB)) ;     // Simply adding helps. Can be proved that the coins are in ascending Order.
			j++ ;
		  }
		  DSObj.bChain.InsertBlock_Malicious(tB) ;
	  }
	  // The InsertBlock_Malicious also updates the LastBlocks List of the BlockChain. Please Check.
	  DSObj.latestCoinID = String.valueOf( Integer.valueOf(nextCoinID)-1 ) ;
  }
}
