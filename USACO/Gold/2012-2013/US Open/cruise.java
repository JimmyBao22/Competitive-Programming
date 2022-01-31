
import java.util.*;
import java.io.*;

public class cruise {

	// http://usaco.org/index.php?page=viewproblem2&cpid=284
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cruise.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cruise.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		Node[] arr = new Node[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new Node(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		}
		st = new StringTokenizer(in.readLine());
		char[] moves = new char[m];
		for (int i=0; i<m; i++) {
			moves[i] = st.nextToken().charAt(0);
		}
		
		HashMap<Integer, Integer> visited = new HashMap<>();
			// start num, move
		
		visited.put(0, 0);
		
		int curnode=0;
		for (int i=1; i<=k; i++) {
			for (int j=0; j<m; j++) {
				if (moves[j] == 'L') curnode = arr[curnode].left;
				else curnode = arr[curnode].right;
			}
			if (visited.containsKey(curnode)) {
				int lastseen = visited.get(curnode);
				k = (k-i)%(i-lastseen);
				i = 0;
				visited.clear();
			}
			visited.put(curnode, i);				
		}
		
		System.out.println(curnode+1);
		out.println(curnode+1);
		out.close();
	}
	
	static class Node {
		int left; int right;
		Node (int a, int b) {left = a; right = b;}
	}
}