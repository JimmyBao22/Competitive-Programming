
import java.util.*;
import java.io.*;

public class EhabtheXorcist {
	
	// https://codeforces.com/contest/1325/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("EhabtheXorcist.in"));
		// INPUT //

		StringTokenizer st = new StringTokenizer(in.readLine());
		long u = Long.parseLong(st.nextToken());  
		long v = Long.parseLong(st.nextToken());  
		
		// u is the xor, v is the add
		if (u > v) System.out.println(-1);
		else if (u == v) {
			if (u != 0) System.out.println(1);
			System.out.println(u);
		}
		else if (u % 2 != v % 2) System.out.println(-1);
		else {
			// u < v
			
			ArrayList<Long> works = new ArrayList<>();
			works.add(u);
			
			v = v-u;
			while (v > 0) {
				long largest = (long) Math.pow(2, log(v));
				works.add((largest/2));
				works.add((largest/2));
				v -= largest;
			}
			
			ArrayList<Long> better = new ArrayList<>();
			for (int i = 0; i < works.size(); i++) {
				long cur = works.get(i);
				for (int j = i+1; j< works.size(); j++) {
					if ((cur^works.get(j)) > cur) {		
							// need shortest array. works.get(j), j>0 will always containt a single bit
							// so, if this bit is greater, than that means cur does not contain this bit
							// so this is okay becaue both adding and xor will be fine.
						cur = cur^works.get(j);
						// j is used
						works.remove(j);
						j--;
					}
				}
				better.add(cur);

			}
			
			System.out.println(better.size());
			for (int i = 0; i < better.size(); i++) {
				System.out.print(better.get(i) + " ");
			}
		}
	}
	
	public static long log(long n) {
		long x = 1;
		//long count = 2;
		while ((1l<<(x+1)) <= n) {
			x++;
		//	count *= 2;
		}
		return x;
	}

}
