import java.util.*;
import java.io.*;
import java.lang.*;

class Point{
    int x;
    int y;
    Point(int x,int y){
        this.x=x;
        this.y=y;
    }
}

public class sample {

    public static int query(int x,int y){
        int ans=0;
        for(int i=0;i<pts.size();i++){
            ans+=((Math.abs(x-pts.get(i).x)<=100)&&(Math.abs(y-pts.get(i).y)<=100)?1:0);
        }
        return ans;
    }

    public static Vector<Point> pts=new Vector<Point>();
    public static void main(String[] args){
        try{
            FileInputStream q=new FileInputStream("queries.txt");
            FileInputStream r=new FileInputStream("restaurants.txt");
            FileOutputStream cid=new FileOutputStream("out2.txt",false);
            PrintStream hul=new PrintStream(cid);
            Scanner s = new Scanner(r);
            s.nextLine();
            while(s.hasNext()){
                Scanner n =new Scanner(s.nextLine()).useDelimiter(",");
                pts.add(new Point(n.nextInt(),n.nextInt()));
            }
            s=new Scanner(q);
            s.nextLine();
            while(s.hasNext()){
                Scanner n=new Scanner(s.nextLine()).useDelimiter(",");
                hul.println(query(n.nextInt(),n.nextInt()));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }    
}
