
import java.util.*;
import java.io.*;

public class SegmentswithSmallSet {

	// https://codeforces.com/edu/course/2/lesson/9/2/practice/contest/307093/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SegmentswithSmallSet"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		System.out.println(firstway(n,k,arr));
//		System.out.println(secondway(n,k,arr));
	}
	
	public static long firstway(int n, int k, int[] arr) {
		int[] count = new int[(int)(1e5+1)];
		int left=0;
		int num=0;
		long ans=0;
		for (int i=0; i<n; i++) {
			if (count[arr[i]] == 0) num++;
			count[arr[i]]++;
			
			while (num > k) {
				count[arr[left]]--;
				if (count[arr[left]] == 0) num--;
				left++;
			}
			
			ans += (i - left + 1);
		}
		return ans;
	}
	
	public static long secondway(int n, int k, int[] arr) {
		HashMap<Integer, Integer> count = new HashMap<>();
		long ans=0;
		int left=0;
		for (int i=0; i<n; i++) {
			count.put(arr[i], count.getOrDefault(arr[i], 0)+1);
			while (count.size() > k) {
				count.put(arr[left], count.get(arr[left])-1);
				if (count.get(arr[left]) == 0) count.remove(arr[left]);
				left++;
			}
			
			ans += (i - left + 1);
		}
		return ans;
	}
}