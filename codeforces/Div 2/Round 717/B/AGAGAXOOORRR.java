
import java.util.*;
import java.io.*;

public class AGAGAXOOORRR {
 
	// https://codeforces.com/contest/1516/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AGAGAXOOORRR"));

		int t = Integer.parseInt(in.readLine());
		o: while (t-- > 0) {
			int n = Integer.parseInt(in.readLine());
			int[] arr = new int[n];
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			int[] p = new int[n];
			int[] s = new int[n];
			for (int i=0; i<n; i++) {
				if (i == 0) p[i] = arr[i];
				else p[i] = p[i-1] ^ arr[i];
			}
			for (int i=n-1; i>=0; i--) {
				if (i == n-1) s[i] = arr[i];
				else s[i] = s[i+1] ^ arr[i];
			}
			
			// 2 elements
			for (int i=0; i<n-1; i++) {
				if (p[i] == s[i+1]) {
					System.out.println("YES");
					continue o;
				}
			}
			
			// 3 elements (0 ... i), (i+1 ... j-1), (j ... n-1)
			// if 3 elements equal, then xor of all 3 = just one of the elements (xor of two together = 0)
				// so, xor of all elements (p[n-1]) has to equal xor of one of them (e.g. p[i])
			for (int i=0; i<n; i++) {
				for (int j=i+2; j<n; j++) {
					if (p[i] == s[j] && p[i] == p[n-1]) {
						System.out.println("YES");
						continue o;
					}
				}
			}
			
			System.out.println("NO");
		}
	}
}