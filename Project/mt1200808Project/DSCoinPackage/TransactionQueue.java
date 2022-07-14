package DSCoinPackage;
import java.util.* ;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;
  public List <Transaction> tq ;

  public void AddTransactions (Transaction transaction) {
	  
   if ( this.firstTransaction == null ) {
	   tq = new ArrayList<Transaction>() ;
	   tq.add(transaction) ;
	   this.firstTransaction = transaction ;
	   this.lastTransaction = transaction ;
	   this.numTransactions = 1 ;
   }
   else {
	   this.lastTransaction = transaction ;
	   tq.add(transaction) ;
	   this.numTransactions++ ;
   }
  }
  
  public Transaction RemoveTransaction () throws EmptyQueueException {
	  
	  if (this.firstTransaction == null ) throw new EmptyQueueException() ;
	  else {
		Transaction t1 = this.tq.get(0) ;
		this.tq.remove(0) ;
		if ( !this.tq.isEmpty() ) this.firstTransaction = this.tq.get(0) ;
		else this.firstTransaction = null ; 
		this.numTransactions-- ; 
		return t1 ;
	  }
  }

  public int size() {
    return this.numTransactions ;
  }
}
