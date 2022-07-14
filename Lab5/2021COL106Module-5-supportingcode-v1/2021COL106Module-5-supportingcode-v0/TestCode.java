import Includes.*;
import java.util.*;
import java.io.*;

public class TestCode{
	public static void main(String[] args) {
		List<Pair<String,Integer>> documents = new ArrayList<Pair<String,Integer>> ();
		documents.add(new Pair <String,Integer> ("Himanshu",98));
		documents.add(new Pair <String,Integer> ("Aditya",41));
		documents.add(new Pair <String,Integer> ("Rishabh",63));
		documents.add(new Pair <String,Integer> ("Garima",11));
		documents.add(new Pair <String,Integer> ("Ramesh",91));
		documents.add(new Pair <String,Integer> ("Vaibhav",0));
		documents.add(new Pair <String,Integer> ("Ritika",63));
		documents.add(new Pair <String,Integer> ("Saiyam",100));

		BlockChain al = new BlockChain();
		System.out.println("Checking BlockChain...");
		try
		{
			String proof = "LabModule5";
			String new_proof = al.InsertBlock(documents, 2021);
			boolean correct = (al.lastblock!=null);
			correct &= (al.firstblock!=null);
			correct &= (al.lastblock.previous==null);
			correct &= (al.firstblock.previous==null);
			correct &= (al.firstblock.next==null);
			correct &= (al.lastblock.next==null);
			correct &= (al.firstblock.year==2021);

			CRF crf = new CRF(64);
			String corr_dgst = crf.Fn(crf.Fn(crf.Fn("Himanshu_98#Aditya_41")+"#"+crf.Fn("Rishabh_63#Garima_11"))+"#"+crf.Fn(crf.Fn("Ramesh_91#Vaibhav_0")+"#"+crf.Fn("Ritika_63#Saiyam_100")));
			corr_dgst += "_100";
			String obt_dgst = al.lastblock.value;
			correct &= corr_dgst.equals(obt_dgst);                                 
			corr_dgst = crf.Fn(proof+"#"+corr_dgst); 
			correct &= corr_dgst.equals(new_proof);                                
                                                                 
String new_proof2 = al.InsertBlock(documents, 2022);
String block1value = "1DE31B9E2E11B56C985F971070F28FE2D413D43A6C9397C9A8B165DAF4AC9718_100" ;
String block1dgst  = "F98110FE13EEC5ABC713803BEDAB80955F90D668A8471B02B9B6C12822BD5C03" ;
System.out.println("Expected : "+crf.Fn(block1dgst+"#"+block1value) ) ;
System.out.println(new_proof2) ;

			if (!correct) System.out.println("ERROR: in BlockChain");
			else System.out.println("BlockChain works fine!");
		}
		catch (Exception e)
		{
			System.out.println("ERROR: An exception was thrown");
		}

		System.out.println("Checking ProofofScore...");
		try
		{
			CRF crf = new CRF(64);
			Pair <List<Pair<String,String>>, List<Pair<String,String>>> obt_path = al.ProofofScore(5,2022);
			List<Pair<String,String>> path1 = obt_path.First;
			List<Pair<String,String>> path2 = obt_path.Second;

			boolean correct = (path1.size()==4);
			correct &= ((path1.get(0)).First).equals("Ramesh_91");
			correct &= ((path1.get(0)).Second).equals("Vaibhav_0");
			correct &= ((path1.get(1)).First).equals(crf.Fn("Ramesh_91#Vaibhav_0"));
			correct &= ((path1.get(1)).Second).equals(crf.Fn("Ritika_63#Saiyam_100"));
			correct &= ((path1.get(2)).First).equals(crf.Fn(crf.Fn("Himanshu_98#Aditya_41")+"#"+crf.Fn("Rishabh_63#Garima_11")));
			correct &= ((path1.get(2)).Second).equals(crf.Fn(crf.Fn("Ramesh_91#Vaibhav_0")+"#"+crf.Fn("Ritika_63#Saiyam_100")));
			correct &= ((path1.get(3)).First).equals(crf.Fn(crf.Fn(crf.Fn("Himanshu_98#Aditya_41")+"#"+crf.Fn("Rishabh_63#Garima_11"))+"#"+crf.Fn(crf.Fn("Ramesh_91#Vaibhav_0")+"#"+crf.Fn("Ritika_63#Saiyam_100"))));
			correct &= (((path1.get(3)).Second)==null); 

			String value = crf.Fn(crf.Fn(crf.Fn("Himanshu_98#Aditya_41")+"#"+crf.Fn("Rishabh_63#Garima_11"))+"#"+crf.Fn(crf.Fn("Ramesh_91#Vaibhav_0")+"#"+crf.Fn("Ritika_63#Saiyam_100")));
			value += "_100";
			String digest = crf.Fn("F98110FE13EEC5ABC713803BEDAB80955F90D668A8471B02B9B6C12822BD5C03#"+value);
			correct &= (path2.size()==1);
			correct &= ((path2.get(0)).First).equals(value);
			correct &= ((path2.get(0)).Second).equals(digest);

			if (!correct) System.out.println("ERROR: in ProofofScore");
			else System.out.println("ProofofScore works fine!");
		}
		catch (Exception e)
		{
			System.out.println("ERROR: An exception was thrown");
		}

		System.out.println("\nTests Completed");
	}
}
