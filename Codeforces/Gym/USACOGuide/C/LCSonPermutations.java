
import java.util.*;
import java.io.*;

public class LCSonPermutations {

	// https://codeforces.com/gym/102951/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LCSonPermutations"));

		int t = 1;
		while (t-- > 0) {
			int n = Integer.parseInt(in.readLine());
			int[] arr1 = new int[n], arr2 = new int[n];
			int[] pos = new int[n+1];		// position of arr[i]
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr1[i] = Integer.parseInt(st.nextToken());
				pos[arr1[i]] = i;
			}
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr2[i] = Integer.parseInt(st.nextToken());
			}
			
			// LIS
			TreeSet<Integer> set = new TreeSet<>();
			for (int i=0; i<n; i++) {
				Integer higher = set.ceiling(pos[arr2[i]]);
				if (higher != null) set.remove(higher);
				set.add(pos[arr2[i]]);
			}
			
			System.out.println(set.size());
		}
	}
}