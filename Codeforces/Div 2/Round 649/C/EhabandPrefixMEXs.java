
import java.util.*;
import java.io.*;

public class EhabandPrefixMEXs {

	// https://codeforces.com/contest/1364/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("EhabandPrefixMEXs"));

		int n = Integer.parseInt(in.readLine());  
		int[] a = new int[n];
		int[] b = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			a[i] = Integer.parseInt(st.nextToken()); 
			if (a[i] > i+1) {
				System.out.println(-1);
				return;
			}
		}
		PriorityQueue<Integer> need = new PriorityQueue<>();
		for (int i=n-1; i>=0; i--) {
			if (i == 0 && a[i] != 0) {
				b[i] = 0;
			}
			else if (i > 0 && a[i] > a[i-1]) {
				b[i] = a[i-1];
				for (int j=a[i-1]+1; j<a[i]; j++) {
					need.add(j);
				}
			}
			else {
				if (need.size() > 0) {
					b[i] = need.poll();
				}
				else {
					b[i] = (int)1e6;
				}
			}
		}
		for (int i=0; i<n; i++) {
			System.out.print(b[i] + " ");
		}
	}

}
