
import java.util.*;
import java.io.*;

public class PinkiePieEatsPattycakes {

	// https://codeforces.com/contest/1393/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PinkiePieEatsPattycakes"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer st = new StringTokenizer(in.readLine());
			HashMap<Integer, Integer> map = new HashMap<>();
			for (int i=0; i<n; i++) {
				int a = Integer.parseInt(st.nextToken());
				map.put(a, map.getOrDefault(a, 0)+1);
			}
			
			ArrayList<Integer> a = new ArrayList<>();		// stores all counts, which is what you care about
			for (Integer x : map.keySet()) {
				a.add(map.get(x));
			}
			
			Collections.sort(a);
			
			int min=0;
			int max = n;
			while (min < max) {
				int middle = (min + max + 1)/2;
				if (works(middle, n, a)) {
					min = middle;
				}
				else max = middle-1;
			}
			
			System.out.println(min);
		}
	}
	
	public static boolean works(int num, int n, ArrayList<Integer> a) {		// num dist apart
		PriorityQueue<E> q = new PriorityQueue<>();
		for (int i=0; i<a.size(); i++) {
			q.add(new E (i, a.get(i)));
		}
		
		int[] fin = new int[n];
		Arrays.fill(fin, -1);
		ArrayList<Integer>  indices = new ArrayList<>();
		for (int i=n-1; i>=0; i--) indices.add(i);
		
		while (q.size() > 0) {
			E cur = q.poll();
			if (cur.lastpos == -1) {
				int index = indices.get(indices.size()-1);
				indices.remove(indices.size()-1);
				fin[index] = cur.num;
				cur.lastpos = index;
			}
			else {
				int min =0;
				int max = indices.size()-1;			// indices.get(min) is furthest pos bc array is decreasing
				if (cur.lastpos + num + 1 > indices.get(min)) return false; 
				while (min < max) {
					int middle = (min + max+1)/2;
					if (indices.get(middle)>=cur.lastpos + num + 1 ) {
						min = middle;
					}
					else max = middle-1;
				}
				fin[indices.get(min)] = cur.num;
				cur.lastpos = indices.get(min);
				indices.remove(min);
			}
			cur.count--;
			if (cur.count <= 0) {
				
			}
			else {
				q.add(cur);
			}
		}
		
		return true;
	}
	
	static class E implements Comparable<E> {
		int num;
		int count;
		int lastpos = -1;
		E (int a, int b) {num = a; count = b;}
		public int compareTo(E other) {
			if (num == other.num) {
				return lastpos - other.lastpos;
			}
			else {
				return other.count - count;
			}
		}
	}
}