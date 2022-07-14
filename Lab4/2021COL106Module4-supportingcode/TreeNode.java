public class TreeNode{
	public TreeNode parent;
	public TreeNode left;
	public TreeNode right;
	public String val;
	public boolean isLeaf;
	public int numberLeaves;
	public String maxleafval;
	public String minleafval;
	public int balanceFactor;
		
	public TreeNode SingleLeftRotation(){
TreeNode g1 = MerkleTree.badNode(this), g2 = g1.right, g3 = g2.right ;

if ( g1.parent == null ) {
g1.parent = g2 ;
g1.right = g2.left   ;
g2.left.parent = g1  ;
g2.left = g1 ;
g2.parent = null ;
}

else {
if ( g1.parent.right == g1 ) {
g1.parent.right = g2 ;
g2.parent = g1.parent ;
g1.parent = g2 ;
g1.right = g2.left   ;
g2.left.parent = g1  ;
g2.left = g1 ;
}

else {
g1.parent.left = g2 ;
g2.parent = g1.parent ;
g1.parent = g2 ;
g1.right = g2.left   ;
g2.left.parent = g1  ;
g2.left = g1 ;
}
}
		return g2;
	}
	
	public TreeNode SingleRightRotation(){
TreeNode g1 = MerkleTree.badNode(this), g2 = g1.left, g3 = g2.left ;

if ( g1.parent == null ) {
g1.parent = g2 ;
g1.left = g2.right ;
g2.right.parent = g1 ; 
g2.right = g1 ;
g2.parent = null ;

}

else {
if ( g1.parent.right == g1 ) {
g1.parent.right = g2 ;
g2.parent = g1.parent ;
g1.parent = g2 ;
g1.left = g2.right ;
g2.right.parent = g1 ; 
g2.right = g1 ;
}

else {
g1.parent.left = g2 ;
g2.parent = g1.parent ;
g1.parent = g2 ;
g1.left = g2.right ;
g2.right.parent = g1 ; 
g2.right = g1 ;
}
}     
		return g2;
	}
	
	public TreeNode DoubleLeftRightRotation(){
TreeNode g1 = MerkleTree.badNode(this), g2 = g1.left, g3 = g2.right ;

if ( g1.parent == null ) {
g1.parent  = g3 ;
g1.left = g3.right ;
g3.right.parent = g1 ;
g3.right = g1 ;
g3.left.parent = g2 ;
g2.parent = g3 ;
g2.right = g3.left ;
g3.left = g2 ;
g3.parent = null ;
}

else {
if ( g1.parent.right == g1 ) {
g1.parent.right = g3 ;
g3.parent = g1.parent ;
g1.parent  = g3 ;
g1.left = g3.right ;
g3.right.parent = g1 ;
g3.right = g1 ;
g3.left.parent = g2 ;
g2.parent = g3 ;
g2.right = g3.left ;
g3.left = g2 ;

}

else {
g1.parent.left = g3 ;
g3.parent = g1.parent ;
g1.parent  = g3 ;
g1.left = g3.right ;
g3.right.parent = g1 ;
g3.right = g1 ;
g3.left.parent = g2 ;
g2.parent = g3 ;
g2.right = g3.left ;
g3.left = g2 ;
}
}
		return g3;

	}
	
	public TreeNode DoubleRightLeftRotation(){
TreeNode g1 = MerkleTree.badNode(this), g2 = g1.right, g3 = g2.left ;

if ( g1.parent == null ) {
g1.parent = g3 ;
g1.right = g3.left ;
g3.left.parent = g1 ;
g2.left = g3.right ;
g3.right.parent = g2 ;
g3.left = g1 ;
g3.right = g2 ;
g2.parent = g3 ;
g3.parent = null ;
}

else {
if ( g1.parent.right == g1 ) {
g1.parent.right = g3 ; 
g3.parent = g1.parent ;
g1.parent = g3 ;
g1.right = g3.left ;
g3.left.parent = g1 ;
g2.left = g3.right ;
g3.right.parent = g2 ;
g3.left = g1 ;
g3.right = g2 ;
g2.parent = g3 ;
}

else {
g1.parent.left = g3 ;
g3.parent = g1.parent ;
g1.parent = g3 ;
g1.right = g3.left ;
g3.left.parent = g1 ;
g2.left = g3.right ;
g3.right.parent = g2 ;
g3.left = g1 ;
g3.right = g2 ;
g2.parent = g3 ;
}
}           
		return g3;
	}
}

