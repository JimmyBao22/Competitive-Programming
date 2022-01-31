
import java.util.*;
import java.io.*;

public class piepie {

	// http://usaco.org/index.php?page=viewproblem2&cpid=765
	
	static int n,d;
	static Bessie[] B;
	static Elsie[] E;
	static int[] Bfinished, Efinished;
	static final int INF = (int)(2e9);
	static TreeSet<Bessie> left;
	static TreeSet<Elsie> right;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("piepie.in"));
		PrintWriter out = new PrintWriter(new FileWriter("piepie.out"));
				
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		E = new Elsie[n];
		B = new Bessie[n];
		
		left = new TreeSet<Bessie>();
		right = new TreeSet<Elsie>();
		
		Bfinished = new int[n];
		Efinished = new int[n];
		
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			B[i] = new Bessie(a,b,i);
			left.add(B[i]);
			Bfinished[i] = INF;
			Efinished[i] = INF;
		}
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			E[i] = new Elsie(a,b,i);
			right.add(E[i]);
		}
		
		bfs();
		
		for (int i=0; i<Bfinished.length; i++) {
			if (Bfinished[i] == INF) {
				//System.out.println(-1);
				out.println(-1);
			}
			else {
				//System.out.println(Bfinished[i]);
				out.println(Bfinished[i]);
			}
		}
		
		out.close();
	}
	
	public static void bfs() {
		ArrayDeque<int[]> deque = new ArrayDeque<int[]>();
		for (int i=0; i<n; i++) {
			if (B[i].b == 0) {
				deque.add(new int[] {i, 0, 1});
				//delete
				left.remove(B[i]);
			}
			if (E[i].a == 0) {
				deque.add(new int[] {i, 1, 1});
				//delete
				right.remove(E[i]);
			}
		}
		while (!deque.isEmpty()) {
			int[] cur = deque.poll();
			int i = cur[0]; int cow = cur[1]; int val = cur[2];
			if (cow==0) {
				// look at elsie
				Bfinished[i] = val;
				Elsie c = new Elsie(B[i].a, B[i].b, n);
				while (true) {
					Elsie cc = right.lower(c);
					if (cc==null || B[i].a - cc.a > d) break;
					deque.add(new int[] {cc.i, 1, val+1});
					right.remove(cc);
				}
			}
			else {
				Efinished[i] = val;
				Bessie c = new Bessie(E[i].a, E[i].b, n);
				while (true) {
					Bessie cc = left.lower(c);
					if (cc==null || E[i].b - cc.b > d) break;
					deque.add(new int[] {cc.i, 0, val+1});
					left.remove(cc);
				}
			}
		}
	}
	
	static class Bessie implements Comparable<Bessie> {
		int a; int b; int i;
		Bessie (int a, int b, int c) {
			this.a = a; this.b = b; i =c;
		}
		
		public int compareTo(Bessie o) {
			if (b == o.b) return i-o.i;
			return b-o.b;
		}
		void print() {
			System.out.println(a + " " + b + " " + i);
		}
	}
	static class Elsie implements Comparable<Elsie> {
		int a; int b; int i;
		Elsie (int a, int b, int c) {
			this.a = a; this.b = b; i =c;
		}
		
		public int compareTo(Elsie o) {
			if (a == o.a) return i-o.i;
			return a-o.a;
		}
		void print() {
			System.out.println(a + " " + b + " " + i);
		}
	}
}

/*
pie for a pie
	for bfs, when we add a node then it is good because otherwise the other things have the same distance.
	Use a treeset to store all nodes on left, and one to store all on right
	every time delete something a consecutive segment the right, so you only visit it once. Then, you can add it to a queue.
*/