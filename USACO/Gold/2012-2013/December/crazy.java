
import java.util.*;
import java.io.*;

public class crazy {

	// http://usaco.org/index.php?page=viewproblem2&cpid=208

	static int n,c;
	static ArrayList<ArrayList<Integer>> regions = new ArrayList<>();
	static int[][] fences;
	static Cow[] cows;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("crazy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crazy.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		fences = new int[n][4];
		for (int i=0; i<n; i++) {
			int[] cur = new int[4];
			st = new StringTokenizer(in.readLine());
			cur[0] = Integer.parseInt(st.nextToken());
			cur[1] = Integer.parseInt(st.nextToken());
			cur[2] = Integer.parseInt(st.nextToken());
			cur[3] = Integer.parseInt(st.nextToken());
			fences[i] = cur;
		}
		cows = new Cow[c];
		for (int i=0; i<c; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			cows[i] = new Cow (one, two);
		}
		
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) findregions(i, visited);
		}
	
		for (int i=0; i<c; i++) {
			int x = cows[i].x; int y = cows[i].y;
				// vertical line
			for (int j=0; j<regions.size(); j++) {
				int count=0;
				for (int k=0; k<regions.get(j).size(); k++) {
					int index = regions.get(j).get(k);
					
					// current line
					double m2 = (double)(fences[index][3] - fences[index][1])/ 
							(double)(fences[index][2] - fences[index][0]);
					double b2 = fences[index][1] - m2 * fences[index][0];
					
					double yf = m2*x+b2;
					if (yf<y) continue;
					double xf = x;
					
					if ((fences[index][0] <= xf) != (fences[index][2]<=xf)) {
						count++;
					}
				}
				if (count%2==1) {
					cows[i].r.add(j);
				}
			}
			Collections.sort(cows[i].r);
		}
		
		Arrays.parallelSort(cows);
		
		int max=1;
		int curcount=1;
		ArrayList<Integer> cur = cows[0].r;
		for (int i=1; i<cows.length; i++ ) {
			boolean works=true;
			if (cows[i].r.size() != cur.size()) {
				works=false;
			}
			else {
				for (int j=0; j<cows[i].r.size(); j++) {
					if (cows[i].r.get(j) != cur.get(j)) {
						works=false;
						break;
					}
				}
			}
			if (works) {
				curcount++;
			}
			else {
				max = Math.max(max, curcount);
				//System.out.println(curcount);
				cur = cows[i].r;
				curcount=1;
			}
		}
		max = Math.max(max, curcount);

		System.out.println(max);
		out.println(max);
		out.close();
	}
	
	public static void findregions(int cur, boolean[] visited) {
		ArrayDeque<Integer> d = new ArrayDeque<>();
		d.add(cur);
		ArrayList<Integer> included = new ArrayList<>();
		while (!d.isEmpty()) {
			cur = d.poll();
			if (visited[cur]) continue;
			visited[cur] = true;
			included.add(cur);
			for (int i=0; i<n; i++) {
				if (visited[i]) continue;
				if (fences[i][0] == fences[cur][0] && fences[i][1] == fences[cur][1]) {
					d.add(i);
				}
				if (fences[i][2] == fences[cur][0] && fences[i][3] == fences[cur][1]) {
					d.add(i);
				}
				if (fences[i][0] == fences[cur][2] && fences[i][1] == fences[cur][3]) {
					d.add(i);
				}
				if (fences[i][2] == fences[cur][2] && fences[i][3] == fences[cur][3]) {
					d.add(i);
				}
			}
		}
		regions.add(included);
	}
	
	static class Cow implements Comparable<Cow> {
		int x; int y; 
		ArrayList<Integer> r;
		Cow(int a, int b) {
			x = a; y = b;
			r = new ArrayList<>();  // indices of all regions it is inside
		}
		public int compareTo(Cow o) {
			if (r.size() == o.r.size()) {
				for (int i=0; i<r.size(); i++) {
					if (r.get(i) != o.r.get(i)) return r.get(i) - o.r.get(i);
				}
				return 0;
			}
			else return r.size() - o.r.size();
		}
	}
}

/*
 		Crazy fences
do n^2 to look at which lines intersect, and dfs out from each line to find each other line it is touching. 
Then can create regions.
draw a line in any direction. For a region, odd number of lines it intersect = inside, even = outside
For each cow you loop through all regions to find if it is inside it. Keep list of all regions it is inside of.
	sort list, then sort the whole list of cows based on their lists
then loop through and see which cows have all things the same in their list (all things have to be the same!)

 */