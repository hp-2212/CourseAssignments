package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;
  
  TransactionBlock() {
	  // A Constructor Function
  }

  TransactionBlock(Transaction[] t) {
	  
	  this.trarray = new Transaction[ t.length ] ;
	  for (int i=0 ; i<t.length ; ++i ) this.trarray[i] = t[i] ;
	  
	  this.Tree = new MerkleTree() ;
	  this.trsummary = this.Tree.Build(trarray) ;
    
  }

  public boolean checkTransaction (Transaction t) {
	  
	  boolean flag1 = false, flag2 = true ;
	  
	  if ( t.coinsrc_block != null ) {
		  
		TransactionBlock tb = this.previous ; // Because we won't have duplicates in the same block.Miner rejects them.
		while ( tb != t.coinsrc_block ) {
			int i=0 ;
			while ( i<tb.trarray.length ) {  // Check for the coinID in the intermediate blocks.
				if ( tb.trarray[i].coinID == t.coinID ) flag2 = false ;
				i++ ;
			}
			tb = tb.previous ;
		}
		int i1 = 0 ;
		tb = t.coinsrc_block ;
		while ( i1 < tb.trarray.length ) {  // Check if in the coinsrc_block The source received this coin.
			if ( tb.trarray[i1].coinID.equals(t.coinID) && tb.trarray[i1].Destination == t.Source ) { flag1 = true ; break ;}
			i1++ ;
		}
		return flag1 && flag2 ;
		
	  }
	  
	  else return true ;
	  
  }
}
