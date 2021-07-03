
import java.util.*;
import java.io.*;

public class AnotherProblemonStrings {

	// https://codeforces.com/contest/165/problem/C
	
	// explanation at bottom
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AnotherProblemonStrings"));

		int k= Integer.parseInt(in.readLine());
		char[] s = in.readLine().toCharArray();
		int n = s.length;
		int[] forward = new int[n];
		int[] backwards = new int[n];
		
		ArrayList<Integer> ones = new ArrayList<>();
		for (int i=0; i<n; i++) {
			if (s[i] == '1') {
				ones.add(i);
			}
		}
		
		if (ones.size() < k) {
			System.out.println(0);
			return;
		}
		
		if (k == 0) {
			ArrayList<Integer> lengths = new ArrayList<>(); // sections of 0's
			int curlength = 0;
			for (int i=0; i<n; i++) {
				if (s[i] == '1') {
					if (curlength != 0) {
						lengths.add(curlength);
						curlength = 0;
					}
				}
				else {
					curlength++;
				}
				//lastchar = s[i];
			}
			if (curlength != 0) lengths.add(curlength);
			long total=0;
			for (int i=0; i<lengths.size(); i++) {
				if (lengths.get(i) >= 0) {
					total += (long)lengths.get(i)*(lengths.get(i)+1)/2;
				}
			}
			System.out.println(total);
			return;
		}
		
		int lastzero = 0;
		boolean one = false;
		for (int i=0; i<n; i++) {
			if (one && s[i] == '0') {
				one = false;
				lastzero = i;
				forward[i] = i;
			}
			else if (!one && s[i] == '0') {
				forward[i] = lastzero;
			}
			else if (!one && s[i] == '1'){
				// s[i] == '1'
				one = true;
				forward[i] = lastzero;
			}
			else if (one && s[i] == '1') {
				forward[i] = i;
			}
		}
		
		lastzero = n-1;
		one = false;
		for (int i=n-1; i>=0; i--) {
			if (one && s[i] == '0') {
				one = false;
				lastzero = i;
				backwards[i] = i;
			}
			else if (!one && s[i] == '0') {
				backwards[i] = lastzero;
			}
			else if (!one && s[i] == '1'){
				// s[i] == '1'
				one = true;
				backwards[i] = lastzero;
			}
			else if (one && s[i] == '1') {
				backwards[i] = i;
			}
		}
		
		int onespointerstart= 0;
		int onespointerend = k-1;
			// pointer for arraylist
		long total =0;
		
		while (onespointerend < ones.size()) {
			// ones.get(onespointerstart) -- index of start '1'
			// ones.get(onespointerend) -- index of end '1'
			
			total += (long)(ones.get(onespointerstart) - forward[ones.get(onespointerstart)] + 1) * (long)(-ones.get(onespointerend) + backwards[ones.get(onespointerend)] + 1);
			onespointerstart++;
			onespointerend++;
		}
		
		System.out.println(total);
	}
}

/*

	my solution:
	
	separate k = 0 separately
	
	if (k ==0) 
		then look at each section filled with only consecutive 0's
		ex. 0000
			notice that if u want 1 '0', then there are 4, if you want 2 '0's (consecutively),
				then there are 3, if u want 3 '0's (consecutively), then there are 2, etc
			
			Therefore, given n consective 0's, in total there will be n+(n-1) + (n-2) + ... + 1 = 
				n(n+1)/2 total substrings
				
	if (k != 0) 
	
		then look at each sequence of k '1's
		keep track of the indices of the '1's so you can find their positions in O(1)
		
		for each '1', you have to keep 2 things. the left most position it can go to and the rightmost (if 
			this '1' was the end of a substring)
			
			for example, if the sequence was 1001100, then the 
				3rd 1 can go left up to 2 spaces, and right up to 0 spaces 
					(001, 01, 1)
			
			By keeping the leftmost and the rightmost parts, we can compute
				for each k 1's, how much u can extend the string on both sides in O(1)
				
				
				
		For example:
					100110101
		indices:	012345678
		forward:	011145577
		backwards:	222355778
		
		k = 3
		Now look at 3 1's that are next to each other.
			first: indices 0, 3, 4 (10011)
			
			notice that the subsequences here include (10011, 100110)
			now, this is because on the left position, there is only 1 possibility, while on the right position there
				are 2 possibilities
				
			Looking at forward (index 0), (0-0+1) = 1, while looking at backwards (index 4) (5-4+1) = 2
				1*2 = 2
				
			
			
			Second: indices 3, 4, 6 (1101)
			
			notice that the subsequences here include (1101, 01101, 001101, 11010, 011010, 0011010)
			now, this is because on the left position, there are 3 possibility, while on the right position there
				are 2 possibilities (3*2 = 6)
				
			Looking at forward (index 3), (3-1+1) = 3, while looking at backwards (index 6) (7-6+1) = 2
				3*2 = 6
				
				
				
			Third: indices 4, 6, 8
			
			notice that the subsequence here is (10101)
			now, this is because on the left position, there is 1 possibility, while on the right position there
				is 1 possibility
				
			Looking at forward (index 4), (4-4+1) = 1, while looking at backwards (index 8) (8-8+1) = 1
				1*1 = 1
				
			Sum all of the products --> 2+6+1 = 9
*/
