
import java.util.*;
import java.io.*;

public class swap {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1014
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("swap.in"));
		PrintWriter out = new PrintWriter(new FileWriter("swap.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = i;
		
		int[][] instructions = new int[m][2];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			instructions[i][0] = Integer.parseInt(st.nextToken())-1;
			instructions[i][1] = Integer.parseInt(st.nextToken())-1;
			
			int moved=instructions[i][1]+1;
			for (int j=instructions[i][0]; j <= instructions[i][1]; j++) {
				if (j >= moved) break;
				int temp = arr[j];
				arr[j] = arr[instructions[i][1] - j + instructions[i][0]];
				arr[instructions[i][1] - j + instructions[i][0]] = temp;
				moved = instructions[i][1] - j + instructions[i][0];
			}
			//System.out.println(Arrays.toString(arr));
		}
		
		int[] forwards = new int[n];
		for (int i=0; i<n; i++) {
			forwards[arr[i]] = i;
		}
		
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				ArrayList<Integer> cur = new ArrayList<>();		// a cycle
				while (!visited[i]) {
					visited[i] = true;
					cur.add(i);
					i = forwards[i];
				}
				int shift = (k-1) % cur.size();
				for (int j=0; j<cur.size(); j++) {
					// cur.get(j) shifted to where cur.get((j + shift)%cur.size()) is
					arr[forwards[cur.get((j + shift)%cur.size())]] = cur.get(j);
					//System.out.println(Arrays.toString(arr));
				}
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			s.append(arr[i]+1);
			s.append("\n");
		}
		
		System.out.print(s);
		out.print(s);
		out.close();
	}
}