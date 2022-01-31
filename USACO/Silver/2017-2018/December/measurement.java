
import java.util.*;
import java.io.*;

public class measurement {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=763
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("measurement.in"));
		PrintWriter out = new PrintWriter(new FileWriter("measurement.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long g = Integer.parseInt(st.nextToken());
		ArrayList<int[]> arr = new ArrayList<>();

		HashMap<Integer, Long> curvals = new HashMap<>();
		HashSet<Integer> curmaxcows = new HashSet<>();
		HashSet<Integer> prevday = new HashSet<>();
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int day = Integer.parseInt(st.nextToken());
			int cow = Integer.parseInt(st.nextToken());
			String val = st.nextToken();
			int value=0;
			if (val.charAt(0) == '0') {
				
			}
			else if (val.charAt(0) == '+') {
				value = Integer.parseInt(val.substring(1));
			}
			else {
				value = -1 * Integer.parseInt(val.substring(1));
			}
			arr.add(new int[] {day, cow, value});
			curvals.put(cow, g);
			curmaxcows.add(cow);
			prevday.add(cow);
		}
		
		Collections.sort(arr, new Comparator<int[]>() {
			public int compare (int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		int ans=0;
		long curmaxval = g;	
		int p=0;
		while (curmaxcows.contains(p)) p++;
		curvals.put(p, g);
		curmaxcows.add(p);
		prevday.add(p);
		
		for (int i=0; i<n; i++) {
			int[] cur = arr.get(i);
			boolean nextonesameday=false;
			if (i != n-1 && arr.get(i+1)[0] == cur[0]) {
				nextonesameday=true;
			}
			if (cur[2] < 0) {
				curvals.put(cur[1], curvals.getOrDefault(cur[1], g)+cur[2]);
				if (curmaxcows.contains(cur[1])) {
					curmaxcows.remove(cur[1]);
					if (!nextonesameday) ans++;
					if (curmaxcows.size() == 0) {
						curmaxval = g;
						for (Integer a : curvals.keySet()) {
							curmaxval = Math.max(curmaxval, curvals.get(a));
						}
						for (Integer a : curvals.keySet()) {
							if (curvals.get(a) == curmaxval) curmaxcows.add(a);
						}
					}
					if (!nextonesameday && curmaxcows.size() == prevday.size()) {
						boolean works=true;
						for (Integer a : curmaxcows) {
							if (!prevday.contains(a)) {
								works=false;
								break;
							}
						}
						if (works) ans--;
					}
				}
			}
			else if (cur[2] == 0) {
				// no change...
			}
			else {
				// cur[2] > 0
				curvals.put(cur[1], curvals.getOrDefault(cur[1], g)+cur[2]);
				if (curmaxcows.size() == 1 && curmaxcows.contains(cur[1])) {
					// it already is, by itself, the max!
					// therefore, if u increase, the display doesnt change
					curmaxval = curvals.get(cur[1]);
				}
				else if (curvals.get(cur[1]) == curmaxval) {
					curmaxcows.add(cur[1]);
					if (!nextonesameday) ans++;
				}
				else if (curvals.get(cur[1]) > curmaxval) {
					curmaxval = curvals.get(cur[1]);
					curmaxcows = new HashSet<>();
					curmaxcows.add(cur[1]);
					if (!nextonesameday) ans++;
				}
			}
			if (!nextonesameday) {
				prevday = new HashSet<>();
				for (Integer a : curmaxcows) {
					prevday.add(a);
				}
			}
		}

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}