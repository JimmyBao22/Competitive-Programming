
import java.util.*;
import java.io.*;

public class SegmentswithSmallSpread {

	// https://codeforces.com/edu/course/2/lesson/9/2/practice/contest/307093/problem/F
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SegmentswithSmallSpread"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long k = Long.parseLong(st.nextToken());
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());
		
		System.out.println(firstway(n, k, arr));
//		System.out.println(secondway(n, k, arr));				
	}
	
		// O(n)
	public static long firstway(int n, long k, long[] arr) {
		long ans=0;
		
			// one = left one, two = right one
		ArrayDeque<Long> one = new ArrayDeque<>(), onemin = new ArrayDeque<>(), 
				onemax = new ArrayDeque<>(), two = new ArrayDeque<>(), 
				twomin = new ArrayDeque<>(), twomax = new ArrayDeque<>();		
		
		for (int i=0; i<n; i++) {
			two.push(arr[i]);
			if (twomin.size() > 0) twomin.push(Math.min(arr[i], twomin.peek()));
			else twomin.push(arr[i]);
			if (twomax.size() > 0) twomax.push(Math.max(arr[i], twomax.peek()));
			else twomax.push(arr[i]);
			
			if (one.isEmpty()) {
				// move everything over to one
				while (!two.isEmpty()) {
					one.push(two.pop());
					twomin.pop(); twomax.pop();
					if (onemin.size() > 0) onemin.push(Math.min(one.peek(), onemin.peek()));
					else onemin.push(one.peek());
					if (onemax.size() > 0) onemax.push(Math.max(one.peek(), onemax.peek()));
					else onemax.push(one.peek());
				}	
			}
			
			long min = onemin.peek();
			if (twomin.size() > 0) min = Math.min(min, twomin.peek());
			long max = onemax.peek();
			if (twomax.size() > 0) max = Math.max(max, twomax.peek());
			
			while (max - min > k) {
				one.pop();
				onemin.pop(); onemax.pop();
				
				if (one.isEmpty()) {
					// move everything over to one
					while (!two.isEmpty()) {
						one.push(two.pop());
						twomin.pop(); twomax.pop();
						if (onemin.size() > 0) onemin.push(Math.min(one.peek(), onemin.peek()));
						else onemin.push(one.peek());
						if (onemax.size() > 0) onemax.push(Math.max(one.peek(), onemax.peek()));
						else onemax.push(one.peek());
					}	
				}
				
				min = onemin.peek();
				if (twomin.size() > 0) min = Math.min(min, twomin.peek());
				max = onemax.peek();
				if (twomax.size() > 0) max = Math.max(max, twomax.peek());
			}
			
			ans += one.size() + two.size();
			
		}
		return ans;
	}
	
		// O(nlogn)
	public static long secondway(int n, long k, long[] arr) {
		TreeMap<Long, Integer> map = new TreeMap<>();
		int left=0;
		long ans=0;
		for (int i=0; i<n; i++) {
			map.put(arr[i], map.getOrDefault(arr[i], 0)+1);
			while (map.lastKey() - map.firstKey() > k) {
				map.put(arr[left], map.get(arr[left])-1);
				if (map.get(arr[left]) == 0) map.remove(arr[left]);
				left++;
			}
			ans += i - left + 1;
		}
		return ans;
	}
}