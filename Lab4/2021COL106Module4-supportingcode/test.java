import Includes.* ;
import java.util.Scanner ;

public class test {

public static void main ( String[] args ) {

Scanner sc = new Scanner(System.in) ;
String s1, s2 ;

s1 = sc.nextLine() ;
s2 = sc.nextLine() ;

CRF obj = new CRF(64) ;
System.out.println(obj.Fn(s1+"#"+s2)) ;

}

}