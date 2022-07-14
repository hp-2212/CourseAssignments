import Includes.*;
import java.util.*;
import java.io.*;


public class Tester2{
	public static void main(String args[]){
		
        
		System.out.println("Testcase 1: 23 Random Strings");
		MerkleTree tree1=new MerkleTree();
		String s1=null;
		s1=tree1.InsertDocument("C") ;
		s1=tree1.InsertDocument("D") ;
		s1=tree1.InsertDocument("A") ;
		s1=tree1.InsertDocument("B") ;
		s1=tree1.InsertDocument("1") ;
		s1=tree1.InsertDocument("A1") ;

		System.out.println(s1);


	}

}
