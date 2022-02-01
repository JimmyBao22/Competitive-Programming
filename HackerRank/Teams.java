
import java.util.*;
import java.io.*;

public class Teams {

	// https://www.hackerrank.com/contests/coding-hub-labor-day-2020/challenges/teams-1-1
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Teams"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		HashSet<Integer> allteams =new HashSet<>();
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			allteams.add(two);
			arr[i] = new A(one, two);
		}
		Arrays.parallelSort(arr);
		
		int ans=0;
		
		HashMap<Integer, Integer> cur = new HashMap<>();
		// i = 0
		if (n>=1) cur.put(arr[0].team,1);
		int pointer=1;
		for (; pointer < n; pointer++) {
			cur.put(arr[pointer].team, cur.getOrDefault(arr[pointer].team, 0)+1);
			if (cur.size() == allteams.size()) break;
		}
		if (n==1) {
			System.out.println(0);
			return;
		}
		ans = arr[pointer].pos - arr[0].pos;
		o: for (int i=1; i<n; i++) {
			cur.put(arr[i-1].team, cur.get(arr[i-1].team) - 1);
			if (cur.get(arr[i-1].team) == 0) cur.remove(arr[i-1].team);
			while (cur.size() < allteams.size()) {
				pointer++;
				if (pointer>=n) break o;
				cur.put(arr[pointer].team, cur.getOrDefault(arr[pointer].team, 0)+1);
			}
			ans = Math.min(ans, arr[pointer].pos - arr[i].pos);
		}
		System.out.println(ans);
	}
	
	static class A implements Comparable<A> {
		int pos;
		int team;
		A (int a, int b) {
			pos = a; team = b;
		}
		public int compareTo(A o ) {
			return pos-o.pos;
		}
	}
}