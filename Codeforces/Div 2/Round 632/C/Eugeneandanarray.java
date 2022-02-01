
import java.util.*;
import java.io.*;

public class Eugeneandanarray {

	// https://codeforces.com/problemset/problem/1333/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Eugeneandanarray.in"));

		int n = Integer.parseInt(in.readLine());  
		long[] arr = new long[n+1];
		long[] psum = new long[n+1];
		Map<Long, Integer> ind = new HashMap<>();
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		arr[0] = 0;
		psum[0] = arr[0];
		ind.put(psum[0], 0);
		for (int i = 1; i <= n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
			psum[i] = psum[i-1] + arr[i];
			if (!ind.containsKey(psum[i])) {
				ind.put(psum[i], i);
			}
		}
		in.close();

		int maxleft = 0; // CANNoT go up to this value
		long result = 0;
		for (int i = 1; i <= n; i++) {
			if (ind.get(psum[i]) != i) {

				maxleft = Math.max(maxleft, ind.get(psum[i]) + 1);
				// ex. arr  5 6 -5 -1
				// ex. psum 5 11 6 5
					//        ^ this index, which is 1, is now maxleft
					//      6 -5 -1 = 0, therefore, this index (1) cannot be included
						// anymore
				
				ind.put(psum[i], i);
					// update the map now, we do not care about the left anymore
			}
			result = result + (i - maxleft);
		}
		
		System.out.println(result);

	}

}

/*

	Consider an input, let's say 
	0 3 3 -3 0 4
	
	Then, create an array (arr), and use prefix sum (psum) array
		Though, shift them all and use 1-base indexing. Put the 0th index
			As 0 so that if the first element is actually 0, you won't count it
	arr = {0, 0, 3, 3, -3, 0, 4}
	pusm = {0, 0, 3, 6, 3, 3, 7}
	
		Note that the sum of a section of the array, from elements i -> j
			is the same as the psum j - psum i-1;
				Ex. from index 2 to 4
					3+3-3 = 3;
					3 - 0 = 3;
			Further, notice that if we want a section to have sum 0, then
				psum j - psum i-1 = 0, meaning psum j = psum i-1

		Therefore, the goal is to find sections where the psum are the same.
			This can be done through a map;
			
		Here, we put the starting values in the map (map to indices)
			{0 = 0, 3 = 2, 6 = 3, 7 = 6}
			
		Maxleft is the max left (index). Note: we cannot go to this element, and we cannot go
			to the left of this element
		To start off, maxleft is 0, because the 0th element is 0, which we cannot use
		
		As we loop over, we see if we can use it or not.
		to count to result, we see how much to the left we can go. 
			this is done in i - maxleft, because we can include the : ith index,
				i and (i-1)st index, ... , i up to (maxleft+1)st index
					this gives i - (maxleft + 1) +1 = i - maxleft
						total amount 
		
	Simulation:
		max_left = 0
		result = 0
		i = 1 --> ind.get(psum[1]) = ind.get(0) = 0 ≠ 1
			maxleft = max(0, 1) = 1;
				Now, we know we cannot go to index 1 or anything to the left
			map {0 = 1};
			result = result + (1-1) = 0
			
		i = 2 --> ind.get(psum[2]) = ind.get(3) = 2 = 2
		result = result + (2 - 1) = 1
			(only one that works rn is {3})
		
		i = 3 --> ind.get(psum[3]) = ind.get(6) = 3 = 3
		result = result + (3 - 1) = 3
			(ones that work: {3}, {3,3}, {3})
			
		i = 4 --> ind.get(psum[4]) = ind.get(3) = 2 ≠ 4
			maxleft = max(1, 3) = 3
				(future subsets can only be indices 4 and later)
			map {3 = 4}
			result = result + (4 - 3) = 4
				(ones that work: {3}, {3,3}, {3}, {-3})
		
		i = 5 --> ind.get(psum[5]) = ind.get(3) = 4 ≠ 5
			maxleft = max(3, 5) = 5
			map {3 = 5}
			result = result + (5 - 5) = 4
				(ones that work: {3}, {3,3}, {3}, {-3})
				
		i = 6 --> ind.get(psum[6]) = ind.get(7) = 6 = 6
			result = result + (6 - 5) = 5
				
		Final:ones that work: {3}, {3,3}, {3}, {-3}, {4}
*/