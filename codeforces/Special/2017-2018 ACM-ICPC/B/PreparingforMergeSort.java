
import java.util.*;
import java.io.*;

public class PreparingforMergeSort {

	// https://codeforces.com/contest/847/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PreparingforMergeSort"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++ ) arr[i] = Integer.parseInt(st.nextToken());
		
		TreeMap<Integer, Integer> map = new TreeMap<>();
			// val, index
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		g.add(new ArrayList<>());
		g.get(0).add(arr[0]);
		map.put(arr[0], 0);
		for (int i=1; i<n; i++) {
			Integer lower = map.floorKey(arr[i]);
			if (lower == null) {
				g.add(new ArrayList<>());
				g.get(g.size()-1).add(arr[i]);
				map.put(arr[i], g.size()-1);
			}
			else {
				int index = map.get(lower);
				map.remove(lower);
				g.get(index).add(arr[i]);
				map.put(arr[i], index);
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<g.size(); i++) {
			for (int j=0; j<g.get(i).size(); j++) {
				s.append(g.get(i).get(j) + " ");
			}
			s.append("\n");
		}
		System.out.print(s);
		
	}

	public static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	public static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

}
