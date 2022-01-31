
import java.util.*;
import java.io.*;

public class herding {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=918
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("herding.in"));
		PrintWriter out = new PrintWriter(new FileWriter("herding.out"));

		int n = Integer.parseInt(in.readLine());

		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(in.readLine());
		
		Arrays.parallelSort(arr);

		int[] lengthsstart = new int[2*n-2];
		for (int i=0; i<n-1; i++) {
			// start at arr[i]. cannot start at arr[n-1]
			int end = arr[i]+n-1;
			
			int min = i;
			int max = n-1;
			while (min < max) {
				int middle = (min + max+1)/2;
				if (arr[middle] == end) {
					min = middle;
					max = middle;
				}
				else if (arr[middle] < end) {
					min = middle;
				}
				else {
					max = middle-1;
				}
			}
			if (max == n-1 && arr[max] < end) {
				lengthsstart[2*i] = -1;
			}
			else if (n - (max - i + 1) == 1 && arr[max] != end) {
				lengthsstart[2*i] = -1;
			}
			else lengthsstart[2*i] = n - (max - i + 1);
			
			// start from arr[i]+1
			end = arr[i]+n;
			min = i+1;
			max = n-1;
			while (min < max) {
				int middle = (min + max+1)/2;
				if (arr[middle] == end) {
					min = middle;
					max = middle;
				}
				else if (arr[middle] < end) {
					min = middle;
				}
				else {
					max = middle-1;
				}
			}
			if (max == n-1 && arr[max] < end) {
				lengthsstart[2*i+1] = -1;
			}
			else if (n - (max - i) == 1) {
				lengthsstart[2*i+1] = -1;
			}
			else if (n - (max - i) == 2 && arr[max] != end) {
				lengthsstart[2*i+1] = -1;
			}
			else lengthsstart[2*i+1] = n - (max - i);
		}
		
		int min=n;
		for (int i=0; i<lengthsstart.length; i++) {
			if (lengthsstart[i]!=-1) min = Math.min(min, lengthsstart[i]);
		}
		
		int max = findmax(arr);
		
		System.out.println(min + "\n" + max);
		out.println(min + "\n" + max);
		out.close();

	}
	
	public static int findmax(int[] arr) {
		int n = arr.length;
		int left = arr[1];
		int right = arr[n-1];
		int dist1 = right - left + 1;
		dist1 -= n;
		
		left = arr[0];
		right = arr[n-2];
		int dist2 = right - left + 1;
		dist2 -= n;
		
		return Math.max(dist1,dist2) + 1;
	}
}