import java.util.* ;
import java.io.* ;

public class kdtree {

TreeNode rootnode ;

public static void main ( String[] args ) {

List<IntPair> l1 = new ArrayList<IntPair>() ;

try {

FileInputStream file1 = new FileInputStream("restaurants.txt") ;
Scanner sc1 = new Scanner( file1 ) ;
sc1.nextLine() ;

while ( sc1.hasNextLine() ) {

String s1 = sc1.nextLine() ;
int i=0, j = s1.length() ;
while ( s1.charAt(i) != ',' ) i++ ;
String as = s1.substring(0,i), bs = s1.substring(i+1,j) ;

int a = Integer.valueOf(as) ;
int b = Integer.valueOf(bs) ;

IntPair p1 = new IntPair(a,b) ;
l1.add( p1 ) ;

}
}

catch (FileNotFoundException e ) { System.out.println("File Not Found Exception") ; }

kdtree k1 = new kdtree() ;
k1.rootnode = new TreeNode() ;
kdtree.BuildTree( k1.rootnode, l1, 0 ) ;                                                             // -------------- Function to Build Tree

List<Integer> l2 = new ArrayList<Integer>() ;

try {

FileInputStream file2 = new FileInputStream("queries.txt") ;
Scanner sc2 = new Scanner( file2 ) ;
sc2.nextLine() ;

while ( sc2.hasNextLine() ) {

String s2 = sc2.nextLine() ;
int i=0, j = s2.length() ;
while ( s2.charAt(i) != ',' ) i++ ;
String as = s2.substring(0,i), bs = s2.substring(i+1,j) ;

int a = Integer.valueOf(as) ;
int b = Integer.valueOf(bs) ;

l2.add( Agent.GetRestaurants( new IntPair(a-100,a+100), new IntPair(b-100,b+100), k1.rootnode ) ) ;  // ----------------- Function to get Points.

}
}

catch (FileNotFoundException e ) { System.out.println("File Not Found Exception") ; }

try {

FileOutputStream file3 = new FileOutputStream("output.txt",false) ; 
PrintStream p = new PrintStream(file3) ;
for ( int i=0 ; i<l2.size() ; i++ ) p.println(l2.get(i)) ;

}

catch (FileNotFoundException s ) { System.out.println("File Not Found Exception") ; }

}

public static void BuildTree( TreeNode curr, List<IntPair> l1, int level ) {

if(level%2 == 0 ){

int n = l1.size() ;
l1.sort( Comparator.comparing(p1 -> p1.first) ) ;

if ( n!= 1 ){
curr.isLeaf = false ;
curr.numberPoints = n ;
if ( curr.parent == null )
{curr.x_range = new IntPair( Integer.MIN_VALUE, Integer.MAX_VALUE ); curr.y_range = new IntPair( Integer.MIN_VALUE, Integer.MAX_VALUE ); }

n-- ;
n = n/2 ;
curr.value = l1.get(n).first ;
TreeNode curr_new1 = new TreeNode() , curr_new2 = new TreeNode() ;
curr_new1.parent = curr ; curr_new2.parent = curr ;
curr.left = curr_new1 ; curr.right = curr_new2 ;

curr_new1.x_range = new IntPair ( curr.x_range.first, curr.value ) ;
curr_new2.x_range = new IntPair ( curr.value+1, curr.x_range.second ) ;

curr_new1.y_range = curr.y_range ;
curr_new2.y_range = curr.y_range ;

List l2 = l1.subList(0,n+1) ;
List l3 = l1.subList(n+1,l1.size()) ;

kdtree.BuildTree( curr_new1, l2, level+1) ;
kdtree.BuildTree( curr_new2, l3, level+1) ;

}

else {
curr.point = l1.get(0) ;
curr.isLeaf = true ;
curr.numberPoints = 1 ;
}

}

else {

int n = l1.size() ;
l1.sort( Comparator.comparing(p1 -> p1.second) ) ;

if ( n!= 1 ){
curr.isLeaf = false ;
curr.numberPoints = n ;
if ( curr.parent == null )
{curr.x_range = new IntPair( Integer.MIN_VALUE, Integer.MAX_VALUE ); curr.y_range = new IntPair( Integer.MIN_VALUE, Integer.MAX_VALUE ); }

n-- ;
n = n/2 ;
curr.value = l1.get(n).second ;
TreeNode curr_new1 = new TreeNode() , curr_new2 = new TreeNode() ;
curr_new1.parent = curr ; curr_new2.parent = curr ;
curr.left = curr_new1 ; curr.right = curr_new2 ;

curr_new1.y_range = new IntPair (curr.y_range.first, curr.value ) ;
curr_new2.y_range = new IntPair (curr.value+1, curr.y_range.second );

curr_new1.x_range = curr.x_range ;
curr_new2.x_range = curr.x_range ;

List l2 = l1.subList(0,n+1) ;
List l3 = l1.subList(n+1,l1.size()) ;

kdtree.BuildTree( curr_new1, l2, level+1) ;
kdtree.BuildTree( curr_new2, l3, level+1) ;

}

else {
curr.point = l1.get(0) ;
curr.isLeaf = true ;
curr.numberPoints = 1 ;
}

}

}

}

class Agent {

public static int GetRestaurants( IntPair xRange, IntPair yRange, TreeNode curr ) {

if ( curr.isLeaf ) {

if ( curr.point.first >= xRange.first && curr.point.first <= xRange.second && curr.point.second >= yRange.first && curr.point.second <= yRange.second )
return 1 ;
else return 0 ;
}

else {

int res = 0 ;
if ( curr.left.x_range.first > xRange.second || curr.left.x_range.second < xRange.first || 
     curr.left.y_range.first > yRange.second || curr.left.y_range.second < yRange.first  ) ;

else if ( curr.left.x_range.first >= xRange.first && curr.left.x_range.second <= xRange.second && 
     curr.left.y_range.first <= yRange.first && curr.left.y_range.second <= yRange.second ) res += curr.left.numberPoints ;

else res += Agent.GetRestaurants( xRange, yRange, curr.left) ;

if ( curr.right.x_range.first > xRange.second || curr.right.x_range.second < xRange.first || 
     curr.right.y_range.first > yRange.second || curr.right.y_range.second < yRange.first  ) ;

else if ( curr.right.x_range.first >= xRange.first && curr.right.x_range.second <= xRange.second && 
     curr.right.y_range.first <= yRange.first && curr.right.y_range.second <= yRange.second ) res += curr.right.numberPoints ;

else res += Agent.GetRestaurants( xRange, yRange, curr.right) ;

return res ;
}

}

}

class IntPair {
public int first ;
public int second ;

public IntPair(int a, int b){

this.first = a ;
this.second = b ;

}

}

class TreeNode {

TreeNode parent ;
TreeNode left   ;
TreeNode right  ;
int value       ; // only defined at non-leaf points.
IntPair point   ; // only defined at     leaf points.
int numberPoints;
boolean isLeaf  ;
IntPair x_range ;
IntPair y_range ;

}

class Pair<K, V> {
    public K first;
    public V second;
    public Pair(K _first, V _second) {
        first = _first;
        second = _second;
    }
    public K get_first() {
        return first;
    }
    public V get_second() {
        return second;
    }
}
