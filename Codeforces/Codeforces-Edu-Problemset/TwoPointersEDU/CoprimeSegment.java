
import java.util.*;
import java.io.*;

public class CoprimeSegment {

	// https://codeforces.com/edu/course/2/lesson/9/2/practice/contest/307093/problem/G
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CoprimeSegment"));
		
		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());

			// one = left one, two = right one
		ArrayDeque<Long> one = new ArrayDeque<>(), onegcd = new ArrayDeque<>(),
				two = new ArrayDeque<>(), twogcd = new ArrayDeque<>();
		
		long ans=n+1;
		for (int i=0; i<n; i++) {
			two.push(arr[i]);
			if (twogcd.size() > 0) twogcd.push(gcd(arr[i], twogcd.peek()));
			else twogcd.push(arr[i]);
		
			if (one.isEmpty()) {
				// push everything from two to one
				while (!two.isEmpty()) {
					one.push(two.pop());
					twogcd.pop();
					if (onegcd.size() > 0) onegcd.push(gcd(one.peek(), onegcd.peek()));
					else onegcd.push(one.peek());
				}
			}
			
			long allgcd = onegcd.peek();
			if (twogcd.size() > 0) allgcd = gcd(allgcd, twogcd.peek());
			
			while (!one.isEmpty() && allgcd == 1) {
				
				// try to get rid of next one
				long val = one.pop(); 
				onegcd.pop();
				
				if (one.isEmpty()) {
					// push everything from two to one
					while (!two.isEmpty()) {
						one.push(two.pop());
						twogcd.pop();
						if (onegcd.size() > 0) onegcd.push(gcd(one.peek(), onegcd.peek()));
						else onegcd.push(one.peek());
					}
				}
				
				allgcd = 2;		// random number not 1
				if (onegcd.size() > 0) allgcd = onegcd.peek();
				if (twogcd.size() > 0) allgcd = gcd(allgcd, twogcd.peek());
				
				if (allgcd != 1) {
					// put it back
					one.push(val);
					if (onegcd.size() > 0) onegcd.push(gcd(val, onegcd.peek()));
					else onegcd.push(val);
					allgcd = 1;
					break;
				}
			}
			
			if (allgcd == 1) ans = Math.min(ans, one.size() + two.size());
		}
		if (ans == n+1) ans = -1;
		System.out.println(ans);
	}
	
	public static long gcd(long a, long b) { 
        if (b == 0) return a; 
        return gcd(b, a%b); 
    }
}