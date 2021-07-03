
import java.util.*;
import java.io.*;

public class BoboniuChatswithDu {

	// https://codeforces.com/contest/1395/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("BoboniuChatswithDu"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		long m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(arr);
		
		long ans = arr[n-1];
		
		ArrayList<ob> after = new ArrayList<>();
		for (int i=0; i<n-1; i++) {
			if (arr[i] > m) {
				after.add(new ob((double)(arr[i])/ (double)(d), true));
			}
			else {
				after.add(new ob ((double)arr[i], false));
			}
		}
		
		Collections.sort(after);
		
		ans = Math.max(one(ans, after, d), two(ans, after,d));
		
		System.out.println(ans);
	}
	
	public static long two(long ans, ArrayList<ob> after, int d) {
		int startpointer=0;
		int endpointer = after.size()-1;
		long ans1 = ans;
		while (startpointer <= endpointer) {
			ob cur = after.get(endpointer);
			//cur.print();
			if (cur.div && endpointer-startpointer < d) {
				if (startpointer>0) startpointer--;
			}
			else if (cur.div) {
				startpointer += d;
				ans1 += cur.val*d;
			}
			else {
				ans1 += cur.val;
			}
			endpointer--;
		}
		return ans1;
	}
	
	public static long one(long ans, ArrayList<ob> after, int d) {
		ArrayList<ob> non = new ArrayList<>();
		int startpointer=0;
		int endpointer = after.size()-1;
		long ans1 = ans;
		while (startpointer <= endpointer) {
			ob cur = after.get(endpointer);
			//cur.print();
			if (cur.div) {
				startpointer += d;
				ans1 += cur.val*d;
			}
			else {
				ans1 += cur.val;
				non.add(cur);
			}
			endpointer--;
		}
		
		boolean canans1 = true;
		int endpointer2 = endpointer + 1;
		while (endpointer2 < startpointer) {
			if (non.size() == 0) {
				canans1 = false;
				break;
			}
			ans1 -= non.get(non.size()-1).val;
			non.remove(non.size()-1);
			endpointer2++;
		}
		
		if (!canans1) ans1=-1;
		return ans1;
	}
	
	
	static class ob implements Comparable<ob>{
		double val;
		boolean div;
		ob (double a, boolean b) {
			val = a;
			div = b;
		}
		
		public void print() {
			System.out.println(val + " " + div);
		
		}
		
		public int compareTo(ob other) {
			if (val > other.val) {
				return 1;
			}
			else if (val == other.val) {
//				if (div) return -1;
//				else if (!div) return 1;
//				else return 0;
				return 0;
			} else {
				return -1;
			}
		}
	}
}