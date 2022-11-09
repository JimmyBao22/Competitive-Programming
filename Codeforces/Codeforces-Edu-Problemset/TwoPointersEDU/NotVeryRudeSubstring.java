
import java.util.*;
import java.io.*;

public class NotVeryRudeSubstring {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/G
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NotVeryRudeSubstring"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long s = Long.parseLong(st.nextToken());
		char[] arr = in.readLine().toCharArray();
		
		int ans=0;
		int left=0;
		long a = 0;
		long b = 0;
		long cursum=0;
		for (int i=0; i<n; i++) {
			if (arr[i] == 'a') a++;
			if (arr[i] == 'b') {
				b++;
				cursum += a;
				
				while (cursum > s) {
					if (arr[left] == 'a') {
						a--;
						cursum -= b;
					}
					if (arr[left] == 'b') b--;
					left++;
				}
			}
			
			ans = Math.max(ans, i - left + 1);
		}
		
		System.out.println(ans);
	}
}