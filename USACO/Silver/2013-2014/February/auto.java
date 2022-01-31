
import java.util.*;
import java.io.*;

public class auto {

	// http://usaco.org/index.php?page=viewproblem2&cpid=397
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("auto.in"));
		PrintWriter out = new PrintWriter(new FileWriter("auto.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int w = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
				
		HashMap<String, Integer> index = new HashMap<>();
		String[] arr = new String[w];
		for (int i=0; i<w; i++) {
			String c = in.readLine();
			index.put(c, i);	// all distinct words?
			arr[i] = c;
		}

		Arrays.parallelSort(arr);
		
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int k = Integer.parseInt(st.nextToken());
			String pref = st.nextToken();
			int start = start(k, pref, arr);
			int end = end(k, pref, arr);
			if (start <= end) {
				int num = end - start+1;
				if (num < k) {
					System.out.println(-1);
					out.println(-1);
				}
				else {
					System.out.println(index.get(arr[start+k-1])+1);
					out.println(index.get(arr[start+k-1])+1);
				}
			}
			else {
				System.out.println(-1);
				out.println(-1);
			}
		}
		out.close();
	}
	
	public static int start(int k, String pref, String[] arr) {
		int low = 0;
		int high = arr.length-1;
		int n = pref.length();
		while (low < high) {
			int middle = (low + high)/2;
			int m = arr[middle].length();
						
			int c=0;
			boolean less=false;
			if (n <= m) {
				c = pref.compareTo(arr[middle].substring(0, n));
			}
			else {
				c = pref.substring(0, m).compareTo(arr[middle]);
				less=true;
			}
			
			if (!less) {
				if (c <= 0) {
					high = middle;
				}
				else {
					low = middle+1;
				}
			}
			else {
				if (c == 0) {
					low = middle + 1;
				}
				else if (c < 0) {
					high = middle;
				}
				else {
					low = middle+1;
				}
			}
		}
		return low;
	}
	
	public static int end(int k, String pref, String[] arr) {
		int low = 0;
		int high = arr.length-1;
		int n = pref.length();
		while (low < high) {
			int middle = (low + high+1)/2;
			
			int m = arr[middle].length();
			int c=0;
			boolean less=false;
			if (n <= m) {
				c= pref.compareTo(arr[middle].substring(0, n));
			}
			else {
				c= pref.substring(0, m).compareTo(arr[middle]);
				less=true;
			}
			
			if (!less) {
				if (c >= 0) {
					low = middle;
				}
				else {
					high = middle-1;
				}
			}
			else {
				if (c == 0) {
					low = middle;
				}
				else if (c > 0) {
					low = middle;
				}
				else {
					high = middle-1;
				}
			}
		}
		return low;
	}
	
	public static int compare(String a, String b) {
		int n = a.length();
		int m = b.length();
		if (n < m) {
			return a.compareTo(b.substring(0, n));
		}
		else {
			return a.substring(0, m).compareTo(b);
		}		
	}	
}