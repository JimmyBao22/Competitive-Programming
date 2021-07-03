
import java.util.*;
import java.io.*;

public class Calendars {

	// https://mbit.mbhs.edu/archive/2020f/advanced.pdf
	// https://codeforces.com/gym/102824/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Calendars"));

		int n = Integer.parseInt(in.readLine());
		int[] a = new int[n];
		int[] b = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] where = new int[n];		// where[i] is where i is in a
		for (int i=0; i<n; i++) {
			a[i] = Integer.parseInt(st.nextToken())-1;
			where[a[i]] = i;
		}
		long[] moves = new long[n]; 		// moves[i] is how many moves needed for i
		int[] forwardsmoves = new int[n]; 		// forwardsmoves[i] is how many forwards moves only needed for i
		HashSet<Integer> left = new HashSet<>();		// b[i] is to left of where[b[i]]
		HashSet<Integer> right = new HashSet<>();		// to right
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			b[i] = Integer.parseInt(st.nextToken())-1;
			if (where[b[i]] <= i) {
				forwardsmoves[b[i]] = n - i + where[b[i]];
				right.add(b[i]);
			}
			else {
				forwardsmoves[b[i]] = where[b[i]] - i;
				left.add(b[i]);
			}
			moves[b[i]] = Math.abs(where[b[i]] - i);
		}
		
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
			// moves = integers
		
		for (int i=0; i<n; i++) {
			if (!map.containsKey(forwardsmoves[i])) {
				map.put(forwardsmoves[i], new ArrayList<>());
			}
			map.get(forwardsmoves[i]).add(i);
		}
		
		long curmoves = 0;
		for (int i=0; i<n; i++) {
			curmoves += moves[i];
		}
		
		long ans = curmoves;
		for (int i=1; i<n; i++) {
			// rotate to right
			curmoves += (right.size() - 1);		// except last one
			curmoves -= left.size();
			
			// move the thing on the right, to the left side, which is b[n-i]
					// the change here will be subtract n - 1 - where[b[n-i]], then add
					// where[b[n-i]]
			
			curmoves -= (n - 1 - where[b[n-i]]);
			curmoves += where[b[n-i]];
			
			right.remove(b[n-i]); left.add(b[n-i]);
			
			if (map.containsKey(i)) {
				for (Integer j : map.get(i)) {
					left.remove(j);
					right.add(j);
				}
			}
			ans = Math.min(ans, curmoves);
		}
		System.out.println(ans);
	}
}