
import java.util.*;
import java.io.*;

public class PowerfulSubarray {

	// https://www.hackerrank.com/contests/coding-hub-labor-day-2020/challenges/c-powerful-subarray
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PowerfulSubarray"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++ ) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		int min=2;
		int max=n;
		while (min < max) {
			int middle = (min + max)/2;
			if (middle==n) break;
			if (check(middle, arr, n)) {
				max = middle;
			} else min  =middle+1;
		}
		if (max == n) {
			System.out.println(-1);
		}
		else System.out.println(min);
	}
	
	public static boolean check(int num, int[] arr, int n) {
		HashMap<Integer, Integer> values = new HashMap<>();
		TreeMap<Integer, Integer> occur = new TreeMap<>();
		for (int i=0; i<num; i++) {
			if (values.containsKey(arr[i])) {
				occur.put(values.get(arr[i]), occur.get(values.get(arr[i]))-1);
				if (occur.get(values.get(arr[i])) ==0) occur.remove(values.get(arr[i]));
				values.put(arr[i], values.get(arr[i])+1);
				occur.put(values.get(arr[i]), occur.getOrDefault(values.get(arr[i]), 0)+1);
			} 
			else {
				values.put(arr[i], 1);
				occur.put(1, occur.getOrDefault(1, 0)+1);
			}
		}
		if (occur.get(occur.lastKey())==1) {
			return true;
		}
		for (int i=1; i+num-1<n; i++) {
			occur.put(values.get(arr[i-1]), occur.get(values.get(arr[i-1]))-1);
			if (occur.get(values.get(arr[i-1])) ==0) occur.remove(values.get(arr[i-1]));
			values.put(arr[i-1], values.get(arr[i-1])-1);
			if (values.get(arr[i-1])==0) values.remove(arr[i-1]);
			else occur.put(values.get(arr[i-1]), occur.getOrDefault(values.get(arr[i-1]), 0)+1);
			
			int j=i+num-1;
			if (values.containsKey(arr[j])) {
				occur.put(values.get(arr[j]), occur.get(values.get(arr[j]))-1);
				if (occur.get(values.get(arr[j])) ==0) occur.remove(values.get(arr[j]));
				values.put(arr[j], values.get(arr[j])+1);
				occur.put(values.get(arr[j]), occur.getOrDefault(values.get(arr[j]), 0)+1);
			} 
			else {
				values.put(arr[j], 1);
				occur.put(1, occur.getOrDefault(1, 0)+1);
			}
			if (occur.get(occur.lastKey())==1) {
				return true;
			}
		}
		
		return false;
	}
}