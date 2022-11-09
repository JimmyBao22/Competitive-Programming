
import java.util.*;
import java.io.*;

public class LoopedPlaylist {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LoopedPlaylist"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long p = Long.parseLong(st.nextToken());
		long[] arr = new long[2*n];
		long arrsum=0;
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
			arr[i+n] = arr[i];
			arrsum += arr[i];
		}
		
		long ans=0;
		ans += n * (p/arrsum);			// need p/arrsum full loops through
		p %= arrsum;
		
		long curans=n;
		int index = 0;
		
		long sum=0;
		int left=0;
		for (int i=0; i<2*n; i++) {
			sum += arr[i];
			while (left < 2*n && sum - arr[left] >= p) {
				sum -= arr[left];
				left++;
			}
			if (sum >= p && i - left + 1 < curans) {
				curans = i - left + 1;
				index = left%n;
			}
		}
		
		System.out.println(index+1 + " " + (ans + curans));
	}
}