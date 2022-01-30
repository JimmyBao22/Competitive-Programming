import java.util.*;
import java.io.*;

public class notlast {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=687

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("notlast.in"));
		
		int n = in.nextInt();
		
		int[] milk = new int[7];
		/* 
			 each index corresponds to 
			 Bessie, Elsie, Daisy, Gertie, Annabelle, Maggie, and Henrietta
			 in that order for now
		 */
		
		String[] names = {"Bessie", "Elsie", "Daisy", "Gertie", "Annabelle", "Maggie", "Henrietta"};
		
		for (int i = 0; i < n; i++) {
			String k = in.next();
			if (k.equals("Bessie")) milk[0] += in.nextInt();
			if (k.equals("Elsie")) milk[1] += in.nextInt();
			if (k.equals("Daisy")) milk[2] += in.nextInt();
			if (k.equals("Gertie")) milk[3] += in.nextInt();
			if (k.equals("Annabelle")) milk[4] += in.nextInt();
			if (k.equals("Maggie")) milk[5] += in.nextInt();
			if (k.equals("Henrietta")) milk[6] += in.nextInt();
		}
		
		in.close();
		
		for (int i = 0; i < milk.length; i++) {
			int minindex = i;
			for (int j = i+1; j < milk.length; j++) {
				if (milk[minindex] > milk[j]) {
					minindex = j;
				}
			}
			
			int temp = milk[minindex];
			milk[minindex] = milk[i];
			milk[i] = temp;
			
			String t = names[minindex];
			names[minindex] = names[i];
			names[i] = t;
			//sorts!
		}

		int smallest = milk[0];
		
		//int second = 0;
		int index = 1;
		boolean all = true;
		for (int i = 1; i < milk.length; i++) {
			if (milk[i] != smallest) {
				index = i;
				//second = milk[i];
				all = false; // all of them are the same?
				break;
			}
		}
		
		PrintWriter out = new PrintWriter(new File("notlast.out"));
		
		if (all) {
			System.out.println("Tie");
			out.println("Tie");
		}
		else if (index != 6 && milk[index] == milk[index+1]) {
			System.out.println("Tie");
			out.println("Tie");
		}
		else {
			System.out.println(names[index]);
			out.println(names[index]);
		}
		
		out.close();
	}
}