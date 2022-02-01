
import java.util.*;
import java.io.*;

public class GuessTheMaximums {

	// https://codeforces.com/contest/1363/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GuessTheMaximums"));

		int t = Integer.parseInt(in.readLine());  
		for (int j=0; j<t; j++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());  
			int k = Integer.parseInt(st.nextToken());  
			
			// input subsets
			ArrayList<int[]> subsets = new ArrayList<>();
			for (int i=0; i<k; i++) {
				st = new StringTokenizer(in.readLine());
				int c = Integer.parseInt(st.nextToken());
				int[] cur = new int[c];
				for (int x=0; x<c; ++x) {
					cur[x] = Integer.parseInt(st.nextToken());
				}
				subsets.add(cur);
			}	
			
			// find the max value over all of the elements --> 1 query
			System.out.print("? " + n + " ");
			for (int i=1; i<=n; i++) System.out.print(i + " ");
			System.out.println();
			System.out.flush();
			int maxval = Integer.parseInt(in.readLine());
			if (maxval == -1) System.exit(0);
			
			//bin search for max --> log_2(1000) ≈ 10 queries
			int min = 1;
			int max = n;
			while (min < max) {
				int middle = (max + min+1)/2;
				// query the middle to the end
				System.out.print("? " + (n - middle+1) + " ");
				for (int i=middle; i<=n; i++) System.out.print(i + " ");
				System.out.println();
				System.out.flush();
				// cur max value given from query of middle to end
				int cur_max = Integer.parseInt(in.readLine());
				if (cur_max == -1) System.exit(0);
				// if the cur max is the max value, the max value exists somewhere from
					// the middle to the end
				if (cur_max == maxval) {
					min = middle;
				}
				// otherwise it is at the beginning
				else if (cur_max < maxval) {
					max = middle-1;
				}
			}
			
			// loop over all of the subsets, and see which one contains the index for the 
				// max value (which is given by min or max from the binary search). 
				//this means it cannot be max value. All other can be this max value
			int subset_index=0;
			outerloop: for (int i=0; i<subsets.size(); i++) {
				for (int r=0; r<subsets.get(i).length; r++) {
					if (subsets.get(i)[r] == min) {
						subset_index = i;
						break outerloop;
					}
				}
			}
			
			int secmaxval = Integer.MIN_VALUE;
			ArrayList<Integer> nonsubset = new ArrayList<>();
				// the last subset that is not the max value, we need to 
				// find the max of all the indices which are not in this 
				// last subset. nonsubset will keep every index of all the terms which
				// are not in the last subset
			for (int i=1; i<=n; i++) {
				boolean contained = false;
				for (int r=0; r<subsets.get(subset_index).length; r++) {
					if (subsets.get(subset_index)[r] == i) {
						contained = true;
						break;
					}
				}
				if (!contained) nonsubset.add(i);
			}

			// now query over all elements of nonsubset --> 1 query
			System.out.print("? " + nonsubset.size() + " ");
			for (int i=0; i< nonsubset.size(); i++) {
				System.out.println(nonsubset.get(i) + " ");
			}
			System.out.println();
			System.out.flush();
			int ret = Integer.parseInt(in.readLine());
			if (ret == -1) System.exit(0);
			secmaxval = ret;
			
			// output
			System.out.print("! ");
			for (int i=0; i<k; i++) {
				if (i == subset_index) System.out.print(secmaxval + " ");
				else System.out.print(maxval + " ");
			}
			String s = in.readLine();
			if (s.equals("Incorrect")) System.exit(0);
		}
	}
}

	// total queries will be about 1 + 10 + 1 = 12

/*

	Query all of the number to get the max element. Then use binary search 
		to find the position of the max element 
			(note: there could be multiple max elements, but we only need to find 
				one because that means all the other positions can have value of this max
	
	all the subsets are disjoint, so all of the arrays are this max value, except
		the subset that contains this position of max.
	 	
	 	you have to look at all other values to see the 
	 	max value this subset specifically can take.
		
		
*/