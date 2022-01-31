
import java.util.*;
import java.io.*;

public class greetings {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=205
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("greetings.in"));
		PrintWriter out = new PrintWriter(new FileWriter("greetings.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int b = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());

		A[] barr = new A[b];
		A[] earr = new A[e];
		for (int i=0; i<b; i++ ) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			char two = st.nextToken().charAt(0);
			if (two=='R') {
				barr[i] = new A(one, 1);
			} else {
				barr[i] = new A(one, -1);
			}
		}
		for (int i=0; i<e; i++ ) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			char two = st.nextToken().charAt(0);
			if (two=='R') {
				earr[i] = new A(one, 1);
			} else {
				earr[i] = new A(one, -1);
			}
		}
		
		int firstp=0;
		int secondp=0;
		int firstpos=0;
		int secondpos=0;
		int count=0;
		while (firstp<b && secondp<e) {
			A first = barr[firstp];
			A second = earr[secondp];
			if (intersect(firstpos, secondpos, first, second)) count++;
			firstpos += (Math.min(first.time, second.time))*first.dir;
			secondpos += (Math.min(first.time, second.time))*second.dir;
			if (first.time < second.time) {
				firstp++;
				second.time -= first.time;
			} 
			else {
				secondp++;
				first.time -= second.time;
			}
		}
		
		while (firstp < b) {
			int newf = firstpos + barr[firstp].time * barr[firstp].dir;
			if (firstpos<secondpos && newf >= secondpos) count++;
			else if (secondpos < firstpos && secondpos >= newf) count++;
			firstpos = newf;
			firstp++;
		}
		
		while (secondp < e) {
			int news = secondpos + earr[secondp].time * earr[secondp].dir;
			if (firstpos<secondpos && firstpos >= news) count++;
			else if (secondpos < firstpos && news >= firstpos) count++;
			secondpos = news;
			secondp++;
		}

		System.out.println(count);
		out.println(count);
		out.close();

	}
	
	public static boolean intersect(int firstpos, int secondpos, A first, A second) {
		int fchange = firstpos + (Math.min(first.time, second.time))*first.dir;
		int schange = secondpos + (Math.min(first.time, second.time))*second.dir;
		if (firstpos<secondpos && fchange >= schange) return true;
		if (secondpos < firstpos && schange>= fchange) return true;
		return false;
	}
	
	static class A {
		int time;
		int dir;
		A (int a, int b) {
			time = a; dir = b;
		}
	}
}