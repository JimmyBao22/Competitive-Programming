
import java.util.*;
import java.io.*;

public class meetings {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=967
	
	static int n;
	static int l;
	static int weightneeded;
	static Cow[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("meetings.in"));
		PrintWriter out = new PrintWriter(new FileWriter("meetings.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		arr = new Cow[n];
		weightneeded=0;
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b= Integer.parseInt(st.nextToken());
			int c= Integer.parseInt(st.nextToken());
			arr[i] = new Cow(a,b,c);
			weightneeded += a;
		}
		weightneeded = (weightneeded+1)/2; // ceiling
		
		Arrays.sort(arr);
		
		int min = 0;
		int max = l-1;
		
		while (min < max) {
			int middle = min + (max - min)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		int time = min;
		// System.out.println(time);
		int ans=0;
		ArrayDeque<Cow> queue = new ArrayDeque<>();
		for (int i=0; i<n; i++) {
			if (!arr[i].right) {
				while (queue.size() > 0 && queue.peek().pos + 2 * time < arr[i].pos) queue.poll();
				ans += queue.size();
			} 
			else {
				queue.add(arr[i]);
			}
		}
	
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static boolean check(int num) {
		int totalweight=0;
		int count=0;
		for (int i=0; i<n; i++) {
			if (arr[i].pos <= num && !arr[i].right) {
				count++;		// find the number of things going to the left and can reach 0
			}
			else if (arr[i].pos > num) break;
		}
		for (int i=0; i<count; i++) {
			totalweight += arr[i].weight;		// add the first count number of weights. These are the
		}										// ones who will reach 0
		count = 0;
		for (int i=n-1; i>=0; i--) {
			if (arr[i].pos >= l - num && arr[i].right) {
				count++;
			}
			else if (arr[i].pos < l - num) break;
		}
		for (int i=n-1; i>=n-count; i--) {
			totalweight += arr[i].weight;
		}
		return totalweight >= weightneeded;
	}
	
	static class Cow implements Comparable<Cow> {
		int weight;
		int pos;
		boolean right;
		Cow (int a, int b, int c) {
			weight = a;
			pos = b;
			right = c == 1 ? true : false;
		}
		
		public int compareTo(Cow other) {
			return this.pos - other.pos;
		}
	}
}

/*
 	Idea: first find the smallest t that works through binary search. 
 	
 	Then, using t, use a queue and loop through the list. 
 		At an element, if it is going to the right, add it to the queue.
 		Otherwise, it is going left. Therefore, go through queue so that
 			it only contains elements for which this element will pass when it goes to the left
 
 */