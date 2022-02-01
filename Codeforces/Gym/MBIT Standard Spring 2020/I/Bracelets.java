
import java.util.*;
import java.io.*;

public class Bracelets {

	// https://codeforces.com/gym/102620/problem/I
	// https://mbit.mbhs.edu/archive/2020/standard.pdf
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Bracelets"));

		int n = Integer.parseInt(in.readLine());
		int[] one = new int[n];
		int[] two = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) one[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) two[i] = Integer.parseInt(st.nextToken());
		
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i=0; i<n; ++i) {
			map.put(one[i], i);
		}
		
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			if (map.get(two[i]) < i) {
				// rotate end then to two[i]
				arr[two[i]-1] = n - i + map.get(two[i]);
			}
			else {
				arr[two[i]-1] = map.get(two[i]) - i;
			}
		}
		
		Arrays.parallelSort(arr);
		
		int cur=arr[0];
		int count=1;
		int max=1;
		for (int i=1; i<n; i++) {
			if (arr[i] != cur) {
				max = Math.max(max, count);
				count=1;
				cur = arr[i];
			}
			else count++;
		}
		max = Math.max(max, count);
		
		System.out.println(max);
	}
}
