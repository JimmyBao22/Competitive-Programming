
import java.util.*;
import java.io.*;

public class CardSubstrings {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/F
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CardSubstrings"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[] one = in.readLine().toCharArray();
		char[] two = in.readLine().toCharArray();
		int[] can = new int[26];
		for (int i=0; i<m; i++) {
			can[two[i]-'a']++;
		}
		
		long ans=0;
		int[] cur = new int[26];
		int left=0;
		for (int i=0; i<n; i++) {
			cur[one[i]-'a']++;
			
			while (cur[one[i]-'a'] > can[one[i]-'a']) {
				cur[one[left]-'a']--;
				left++;
			}
			
			ans += i - left + 1;
		}
		
		System.out.println(ans);
	}
}