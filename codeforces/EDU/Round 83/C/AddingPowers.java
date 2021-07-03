
import java.util.*;
import java.io.*;

public class AddingPowers {

	// https://codeforces.com/problemset/problem/1312/C
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AddingPowers"));

		int t = Integer.parseInt(in.readLine());  
		for (int j = 0; j < t; j++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken()); 
			int k = Integer.parseInt(st.nextToken()); 
			HashSet<Long> a = new HashSet<>();
			long[] arr = new long[n];
			st = new StringTokenizer(in.readLine());
			boolean works = true;
			outerloop: for (int i = 0 ; i < n; i++) {
				arr[i] = Long.parseLong(st.nextToken());
				// for each one check
				if (arr[i] == 0) continue;
				else {
					ArrayList<Long> cur = new ArrayList<>();
					// put it into base k
					while (arr[i] > 0) {
						// you can't have more than 1, because if you have like 2
							// for example, then you need to add that twice but that's
								// impossible
						if (arr[i] % k > 1) {
							works = false;
							break;
						}
						else {
							cur.add(arr[i] % k);
							arr[i] /= k;
						}
					}
					long p = 0;
					for (int m = 0; m < cur.size(); ++m) {
						if (cur.get(m) != 0) {
								// no other term has to have that position occupied, 
									// because if it does then you have two different
									// places that you have to add that value 		
									// at that specific time, which isn't possible
							if (a.contains((long) (cur.get(m) * Math.pow(k, p)))) {
								works = false;
								break outerloop;
							}
							else {
								a.add((long) (cur.get(m) * Math.pow(k, p)));
							}
						}
						p++;
					}
				}
			}
			
			if (works) System.out.println("YES");
			else System.out.println("NO");
			
		}
	}
}