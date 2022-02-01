
import java.util.*;
import java.io.*;

public class FarmSurveillance {

	// https://www.hackerrank.com/contests/ich-christmas-2020/challenges/d-farm-surveillance
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("FarmSurveillance"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[n];
		HashMap<Integer, Integer> key = new HashMap<>();
		int k=0;
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			if (!key.containsKey(arr[i])) {
				key.put(arr[i], k);
				k++;
			}
			arr[i] = key.get(arr[i]);
		}
		
		ArrayList<Integer>[] pos = new ArrayList[k];
		for (int i=0; i<k; i++) pos[i] = new ArrayList<>();
		
		TreeMap<Integer, Integer> done = new TreeMap<>();
		int last = arr[0];
		int lastindex=0;
		for (int i=0; i<n; i++) {
			pos[arr[i]].add(i);
			if (i != 0) {
				if (arr[i] != last) {
					if (i-1-lastindex+1>1) {
						done.put(lastindex, i-1);
					}
					last = arr[i];
					lastindex = i;
				}
			}
		}
		
		dsu s = new dsu(k);
		
		int count=0;
		for (int i=0; i<k; i++) {
			ArrayList<Integer> cur = pos[i];
			
			int start = cur.get(0)+1;
			int end = cur.get(cur.size()-1)-1;
			
			if (end < start) continue;
			
			Integer lower = done.floorKey(start);
			if (lower != null && done.get(lower) >= start) {
				if (s.Union(arr[start], i)) count++;
				start = done.get(lower)+1;
			}
			
			while (true) {
				Integer upper = done.ceilingKey(start);
				if (upper == null || upper > end) break;
				// start to upper
				for (int j=start; j<=upper; j++) {
					if (s.Union(arr[j], i)) count++;
				}
				start = done.get(upper)+1;
				done.remove(upper);
			}
			
			for (int j=start; j<=end; j++) {
				if (s.Union(arr[j], i)) count++;
			}
			
			done.put(cur.get(0), cur.get(cur.size()-1));
		}
		
		System.out.println(count);
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public boolean Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return false;
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
			}
			return true;
		}
	}
}