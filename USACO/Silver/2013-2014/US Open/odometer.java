
import java.util.*;
import java.io.*;

public class odometer {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=430
	
	static long[] pow10;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("odometer.in"));
		PrintWriter out = new PrintWriter(new FileWriter("odometer.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		long x = Long.parseLong(st.nextToken());
		long y = Long.parseLong(st.nextToken());

		pow10 = new long[18];
		pow10[0] = 1;
		for (int i=1; i<18; i++) {
			pow10[i] = pow10[i-1] * 10;
		}
		
		long ans = f(y) - f(x-1);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	// interesting numbers from 100 to a
	public static long f(long a) {
		String as = a + "";
		int n = as.length();
		long ans=0;
		for (int i=3; i<=n; i++) {
			for (int j=0; j<10; j++) {
				for (int k=0; k<10; k++) {
					if (j == k) continue;
					// j repeated, k once
					
					for (int pos=0; pos<i; pos++) {
						// position of k
						if (pos == i-1 && k == 0) continue;
						if (pos != i-1 && j == 0) continue;
						
						long cur = 0;
						for (int t=0; t<i; t++) {
							if (t == pos) cur += k * pow10[t];
							else cur += j * pow10[t];
						}
						
						if (cur <= a) ans++;
					}
				}
			}
		}	
		return ans;
	}
}