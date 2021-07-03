
import java.util.*;
import java.io.*;

public class Animpassionedcirculationofaffection {

	// https://codeforces.com/problemset/problem/814/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Animpassionedcirculationofaffection"));

		int n = Integer.parseInt(in.readLine());
		String s = in.readLine();
		int q = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<q; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int change = Integer.parseInt(st.nextToken());
			char need = st.nextToken().charAt(0);
			
			int max=0;
			int r=0;
			for (int l=0; l<n; l++) {
				if (l != 0 && s.charAt(l-1) != need) change++; // can change one more
				while (r<n && change>=0) {
					if (s.charAt(r) != need) {
						change--; // can change one less
						if (change<0) {
							change++;
							break;
						}
					}
					r++;
				}
				max = Math.max(max, r-l+1);
				if (r == n) break;
				if (n - l + 1 <= max) break;
			}
			sb.append(max-1);
			sb.append("\n");
		}
		
		System.out.print(sb);	
	}
}