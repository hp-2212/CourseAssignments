package DSCoinPackage;

public class Transaction {

  public String coinID;
  public Members Source;
  public Members Destination;
  public TransactionBlock coinsrc_block;
                                         // My Function to compare two Transactions.
  public static boolean ifequaltrans (Transaction tr1, Transaction tr2 ) {
	  if ( tr1.coinID.equals(tr2.coinID) && tr1.Source.UID.equals(tr2.Source.UID) && tr1.Destination.UID.equals(tr2.Destination.UID) && tr1.coinsrc_block.trsummary.equals(tr2.coinsrc_block.trsummary) )
		  return true ;
	  return false ;
  }
}
