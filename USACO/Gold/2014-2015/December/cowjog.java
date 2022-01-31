
import java.util.*;
import java.io.*;

public class cowjog {

	// http://usaco.org/index.php?page=viewproblem2&cpid=493
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowjog.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowjog.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long t = Integer.parseInt(st.nextToken());

		Cow[] arr= new Cow[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new Cow (Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Arrays.parallelSort(arr);
		
		long[] posafter = new long[n];
		for (int i=0; i<n; i++) {
			posafter[i] = arr[i].pos + arr[i].speed * t;
		}
		
		int groups=1;
		long curpos = posafter[n-1];
		for (int i=n-2; i>=0; i--) {
			if (posafter[i] >= curpos) {
				
			}
			else {
				groups++;
				curpos = posafter[i];
			}
		}
		
		System.out.println(groups);
		out.println(groups);
		out.close();
		
	}
	
	static class Cow implements Comparable<Cow> {
		long pos;
		long speed;
		Cow (long a, long b) {
			pos = a;
			speed = b;
		}
		
		public int compareTo(Cow other) {
			return Long.compare(pos,other.pos);
		}
	}
}