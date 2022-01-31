
import java.util.*;
import java.io.*;

public class photo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=280
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("photo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("photo.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		A[] arr = new A[k];
		PriorityQueue<A> second = new PriorityQueue<A>(new Comparator<A>() {
			public int compare(A a, A b) {
				return a.second-b.second;
			}
		});
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			arr[i] = new A(one,two);
			second.add(arr[i]);
		}
		
		int ans=0;
		int curpos=1;
		while (!second.isEmpty()) {
			A x = second.poll();
			while (!second.isEmpty() && (x.second<curpos || x.first<curpos)) {
				x = second.poll();
			}
			if (second.isEmpty() && (x.second<curpos || x.first<curpos)) break;
			curpos = x.second;
			ans++;
		}
		
		if (curpos<=n) ans++;
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static class A {
		int first, second;
		A (int a, int b) {
			if (a<=b) {
				first = a; 
				second = b;
			}
			else { 
				first = b; 
				second = a;
			}
		}
		void print() {
			System.out.println(first + " " + second);
		}
	}
}