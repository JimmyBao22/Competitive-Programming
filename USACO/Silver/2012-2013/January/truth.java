
import java.util.*;
import java.io.*;

public class truth {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=225
	
	static int n,m;
	static A[] arr;
	static ArrayList<ArrayList<A>> g;
	static int[] type;
		//-1 = nothing yet; 0=false; 1=true;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("truth.in"));
		PrintWriter out = new PrintWriter(new FileWriter("truth.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr=new A[m];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			char three = st.nextToken().charAt(0);
			arr[i] = new A(one,two,three);
		}
		
		int min=0;
		int max = m;
		while (min<max) {
			int middle = (min+max+1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
				
		System.out.println(min);
		out.println(min);
		out.close();
	}
	
	static boolean works;
	
	public static boolean check(int mid) {
		g=new ArrayList<>();
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<mid; i++) {
			g.get(arr[i].from).add(arr[i]);
			g.get(arr[i].to).add(new A(arr[i].to, arr[i].from, arr[i].c));
		}
		type=new int[n];
		Arrays.fill(type, -1);
		
		for (int i=0; i<n; i++) {
			if (type[i] != -1) continue;
			int[] copy = new int[n];
			for (int j=0; j<n; j++) copy[j] = type[j];
			
			works=true;
			type[i] = 0;
			dfs(i);
			if (works) continue;
			else for (int j=0; j<n; j++) type[j] = copy[j];
			
			works=true;
			type[i] = 1;
			dfs(i);
			if (works) continue;
			
			return false;
		}
		return true;
	}
	
	public static void dfs(int curnode) {
		for (int i=0; i<g.get(curnode).size(); i++) {
			A next = g.get(curnode).get(i);
			if (type[next.to] == 0) {
				if (type[curnode] == next.c) {
					works=false;
					return;
				}
				continue;
			}
			else if (type[next.to] == 1) {
				if (type[curnode] != next.c) {
					works=false;
					return;
				}
				continue;
			}
			if (type[curnode] == next.c) {
				type[next.to] = 1;
			}
			else {
				type[next.to] = 0;
			}
			dfs(next.to);
		}
	}
	
	
	static class A {
		int from; int to; int c;
		A(int a, int b, char c) {
			from=a; to=b; 
			if (c == 'L') this.c = 0;
			else this.c = 1;
		}
		A(int a, int b, int c) {
			from=a; to=b; 
			this.c = c;
		}
	}
}