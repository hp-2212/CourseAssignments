import HelperClasses.Pair;
import java.util.* ;
import java.io.*   ;
import HelperClasses.sha256;

public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/

    public Pair<String, String> FindCollDeterministic() {
        Vector <String> v1 = new Vector<String>() ;
        
        String s1 = "000000", temp_str = null ;
        boolean flag = true ;
        CRF obj = new CRF(outputsize) ;
        
        while ( flag ) {
        	temp_str = obj.Fn(s1) ;
        	for( int i=0 ; i<v1.size() ; ++i ) {
        		if ( temp_str.equals( v1.get(i) ) ) 
        		{ temp_str = v1.get(i-1) ; flag = false ; break ;}
        	}
        	if ( flag ) { v1.add(temp_str) ; s1 = temp_str ; }
        }
        
        Pair <String, String> p1 = new Pair<String, String>(s1,temp_str) ;
        return p1;
    }

    public void FindCollRandomized() {
        
       //String attempt_filename = "FindCollRandomizedAttempts.txt";
       //String outcome_filename = "FindCollRandomizedOutcome.txt";
       
    	String[] ary = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
    			        "A", "B", "C", "D", "E", "F"} ;
    	
    	int counter = (int) (1000*(Math.pow(4, outputsize))) +1 ;
    	Vector<Pair <String,String> > v1 = new Vector<Pair <String,String> >() ;
    	boolean flag = true ;
    	CRF obj = new CRF(outputsize) ;
    	String Str_ran = "", Str_res = "", coll1 = "", coll2 = ""  ;
    	
    	while ( counter-- > 0 && flag ) {
    		//System.out.println(counter) ;
    		Str_ran = "" ;
    		Random ran = new Random() ;
        	for ( int i=1 ; i<= outputsize ; ++i ) {
        		Str_ran += ary[ran.nextInt(16)] ;
        	}
        	
        	Str_res = obj.Fn(Str_ran) ;
        	
        	for ( int i=0 ; i<v1.size() ; ++i ) {
        		if ( Str_res.equals(v1.get(i).get_second()) && !Str_ran.equals(v1.get(i).get_first())) {
        			flag = false ;
        			coll1 = Str_ran ;
        			coll2 = v1.get(i).get_first() ;
        		}
        	}
        	
        	v1.add(new Pair<String,String>(Str_ran, Str_res)) ;
    	}
    	
    	try {
   		     FileOutputStream op1 =  new FileOutputStream("FindCollRandomizedAttempts.txt",false) ;
   		     PrintStream p1 = new PrintStream(op1) ;
   		     for ( int i=0 ; i<v1.size(); ++i ) {
   		    	 p1.println(v1.get(i).get_first()) ;
   		     }
   	     }
   	     catch (FileNotFoundException es) {
   		       System.err.println("File Not Found!") ;
   	     }
    	
    	try {
    		FileOutputStream op2 = new FileOutputStream("FindCollRandomizedOutcome.txt",false) ;
       	    PrintStream p2 = new PrintStream(op2) ;
        	
        	if ( flag == false ) {
        		p2.println("Found") ;
        		p2.println(coll1) ;
        		p2.println(coll2) ;
        		//System.out.println(coll1 +" "+coll2) ;
        		//System.out.println(obj.Fn(coll1) +" "+obj.Fn(coll2)) ;
        	}
        	else p2.println("Not Found!") ;
   	}
   	catch (FileNotFoundException es) {
   		System.err.println("File Not Found!") ;
   	  }
    }
}