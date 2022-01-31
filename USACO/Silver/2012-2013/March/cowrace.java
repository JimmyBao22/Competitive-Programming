
import java.util.*;
import java.io.*;

public class cowrace {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=259
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowrace.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowrace.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		A[] first = new A[n];
		A[] second = new A[m];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			long f = Integer.parseInt(st.nextToken());
			long s = Integer.parseInt(st.nextToken());
			first[i] = new A(f,s);
		}
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			long f = Integer.parseInt(st.nextToken());
			long s = Integer.parseInt(st.nextToken());
			second[i] = new A(f,s);
		}
		
		int count=0;
		int firstpointer=0;
		int secpointer=0;
		long firstpos=0;
		long secpos=0;
		while (secpointer<m && firstpointer<n) {
			long curtime = Math.min(first[firstpointer].time, second[secpointer].time);
			long newfirst = firstpos + curtime*first[firstpointer].speed;
			long newsec = secpos + curtime*second[secpointer].speed;
			
			if (firstpos >= secpos && newfirst < newsec) count++;
			else if (firstpos <= secpos && newfirst > newsec) count++;
			
			firstpos = newfirst;
			secpos = newsec;
			
			first[firstpointer].time -= curtime;
			second[secpointer].time -= curtime;
			if (first[firstpointer].time == 0) firstpointer++;
			if (second[secpointer].time == 0) secpointer++;
		}
		
		System.out.println(Math.max(count-1,0));
		out.println(Math.max(count-1,0));
		out.close();

	}
	
	static class A {
		long time;
		long speed;
		A (long a, long b) {
			speed = a; time = b;
		}
	}
}