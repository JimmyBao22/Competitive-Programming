
import java.util.*;
import java.io.*;

public class sort {

	// http://usaco.org/index.php?page=viewproblem2&cpid=837
			
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("sort.in"));
		PrintWriter out = new PrintWriter(new FileWriter("sort.out"));

		int n = Integer.parseInt(in.readLine());
		long[] arr = new long[n];
		long[] arr2 = new long[n];
		for (int i=0; i<n; i++ ) {
			arr[i] = Integer.parseInt(in.readLine());
			arr2[i] = arr[i];
		}
		
		Arrays.parallelSort(arr2);
		HashMap<Long, TreeSet<Integer>> map = new HashMap<>();
		for (int i=0; i<n; i++) {
			if (map.containsKey(arr2[i])) {
				map.get(arr2[i]).add(i);
			}
			else {
				TreeSet<Integer> c = new TreeSet<Integer>();
				c.add(i);
				map.put(arr2[i], c);
			}
		}
		
		//int max = Math.max(left(map, arr, arr2), right(map, arr, arr2));
			// left = right
		int max = left(map,arr,arr2);
		if (max==0) max++;	// always goes at least once
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
	
		// find number of elements to the left of some pointer that need
			// to be moved to the right. This equals # on right that needs to be
			// moved to the left. Answer is the max of all these numbers, 
			// because 1 cow moo --> one element crosses
	public static int left(HashMap<Long, TreeSet<Integer>> pos, long[] arr, long[] arr2) {
		int count=0;
		int maxcount=0;
		HashMap<Long, Integer> curleft = new HashMap<>();
		for (int i=0; i<arr.length; i++) {
			if (pos.get(arr[i]).first() > i) {
				curleft.put(arr[i], curleft.getOrDefault(arr[i], 0)+1);
				count++;
			}
			if (curleft.containsKey(arr2[i])) {
				curleft.put(arr2[i], curleft.get(arr2[i])-1);
				if (curleft.get(arr2[i])<=0) curleft.remove(arr2[i]);
				count--;
			}
			maxcount = Math.max(maxcount, count);
		}
		return maxcount;
	}
	
	public static int right(HashMap<Long, TreeSet<Integer>> pos, long[] arr, long[] arr2) {
		int count=0;
		int maxcount=0;
		HashMap<Long, Integer> curright = new HashMap<>();
		for (int i=arr.length-1; i>=0; i--) {
			if (pos.get(arr[i]).first() < i) {
				curright.put(arr[i], curright.getOrDefault(arr[i], 0)+1);
				count++;
			}
			if (curright.containsKey(arr2[i])) {
				curright.put(arr2[i], curright.get(arr2[i])-1);
				if (curright.get(arr2[i])<=0) curright.remove(arr2[i]);
				count--;
			}
			maxcount = Math.max(maxcount, count);
		}
		return maxcount;
	}
	
	public static int brute(long[] arr) {
		long[] copy = new long[arr.length];
		for (int i=0; i<arr.length; i++) copy[i] = arr[i];
		boolean good=false;
		int count=0;
		while (!good) {
			good=true;
			count++;
			for (int i=0; i<arr.length-1; i++) {
				if (copy[i+1] < copy[i]) {
					long t = copy[i];
					copy[i] = copy[i+1];
					copy[i+1] = t;
				}
			}
			for (int i=arr.length-2; i>=0; i--) {
				if (copy[i+1] < copy[i]) {
					long t = copy[i];
					copy[i] = copy[i+1];
					copy[i+1] = t;
				}
			}
			for (int i=0; i<arr.length-1; i++) {
				if (copy[i+1] < copy[i]) good=false;
			}
		}
		return count;
	}
}