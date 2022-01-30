import java.util.*;
import java.io.*;

public class measurement {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=761

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("measurement.in"));
		
		int n = in.nextInt();
		
		int[] day = new int[n];
		String[] name = new String[n];
		int[] change = new int[n];
		
		for (int i = 0; i < n; i++) {
			day[i] = in.nextInt();
			name[i] = in.next();
			change[i] = in.nextInt();
		}
		
		in.close();

		int Bessieamount = 7; 
		int Elsieamount = 7;
		int Mildredamount = 7;
		
		boolean Bessie = true;
		boolean Elsie = true;
		boolean Mildred = true;
		// they are all highest rn
		
		boolean BessieN;
		boolean ElsieN;
		boolean MildredN;
		
		int count = 0;
		
		for (int i = 1; i < 101; i++) {
			//100 days
			for (int j = 0; j < n; j++) {
				if (day[j] == i) {
					//same day!
					if (name[j].equals("Bessie")) Bessieamount += change[j];
					if (name[j].equals("Elsie")) Elsieamount += change[j];
					if (name[j].equals("Mildred")) Mildredamount += change[j];
					break;
				}
			}
			
			int highest = Math.max(Math.max(Bessieamount, Elsieamount), Mildredamount);
			BessieN = Bessieamount == highest;
			ElsieN = Elsieamount == highest;
			MildredN = Mildredamount == highest;
			// are they the highest?
			// true = yes; false = no
			
			if (BessieN != Bessie || ElsieN != Elsie || MildredN != Mildred) {
				//they either changed from highest to now not highest, or they became highest
				count++;
			}
			Bessie = BessieN;
			Elsie = ElsieN;
			Mildred = MildredN;
		}
				
		PrintWriter out = new PrintWriter(new File("measurement.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}
}