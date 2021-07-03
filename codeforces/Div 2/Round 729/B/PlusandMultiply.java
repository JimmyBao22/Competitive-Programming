
import java.util.*;
import java.io.*;

public class PlusandMultiply {

	// https://codeforces.com/contest/1542/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PlusandMultiply"));

		StringBuilder s= new StringBuilder();
		int t = Integer.parseInt(in.readLine());
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long n = Long.parseLong(st.nextToken());
			long a = Long.parseLong(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			
			boolean works=false;
			if (a == 1 && (n-1)%b == 0) {
				s.append("Yes");
				s.append("\n");
				continue;
			}
			else if (a == 1) {
				s.append("No");
				s.append("\n");
				continue;
			}
			
			// notice that it will always take the form x*a^k + y*b, for integers x,k,y
				// even if do something like: get a(1+2b), then add b and multiply by a,
					// to get a(a(1+2b)+b), expanding gives a^2+(2a^2+a)b. This is the same as just
					// multiplying to a^2 then adding a bunch of b's
			
			for (long i = 1; i <= n; i *= a) {
				if ((n-i)%b == 0) {
					works=true;
					break;
				}
			}
			
			if (works) {
				s.append("Yes");
				s.append("\n");			
			}
			else {
				s.append("No");
				s.append("\n");			
			}
		}
		System.out.print(s);
	}
	
	public static long gcd(long a, long b) { 
	    if (b == 0) return a; 
	    return gcd(b, a%b); 
	} 
}