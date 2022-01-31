
import java.util.*;
import java.io.*;

public class combo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=340
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("combo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("combo.out"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] first = new int[3];
		int[] sec = new int[3];
		for (int i=0; i<3; i++) {
			first[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<3; i++) {
			sec[i] = Integer.parseInt(st.nextToken());
		}
		
		int ans=250;
		
		// I think n=1-5 is just n^3 becaue all cases will work...
		if (n==1) {
			ans=1;
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		if (n==2) {
			ans = 8;
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		if (n==3) {
			ans = 27;
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		if (n == 4) {
			ans = 64;
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		if (n == 5) {
			ans = 125;
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
				
		// overlap
		int[] over = new int[3];
		for (int i=0; i<3; i++) {
			int one=0;
			int two=0;
			if (first[i] > sec[i]) {
				two = first[i];
				one = sec[i];
				if (two - one > n/2) {
					one += n;
					int temp=one;
					one = two;
					two = temp;
				}
			}
			else {
				one = first[i];
				two = sec[i];
				if (two - one > n/2) {
					one += n;
					int temp=one;
					one = two;
					two = temp;
				}
			}
			// two>one
			int min = two-2;
			int max = one+2;
			int diff = max - min + 1;
			if (diff<0) diff=0;
			over[i] = diff;
		}
		
		ans -= over[0] * over[1] * over[2];
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}