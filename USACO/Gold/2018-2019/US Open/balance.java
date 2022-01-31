
import java.util.*;
import java.io.*;

public class balance {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=947
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("balance.in"));
		PrintWriter out = new PrintWriter(new FileWriter("balance.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[2*n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<2*n; ++i) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int firsthalf=0;
		int firstones=0;
		int firstzeroes=0;
		for (int i=0; i<n; i++) {
			if (arr[i] == 1) firstones++;
			else {
				firsthalf += firstones;
				firstzeroes++;
			}
		}
		int secondhalf=0;
		int secondones=0;
		int secondzeroes = 0;
		for (int i=n; i<2*n; i++) {
			if (arr[i] == 1) secondones++;
			else {
				secondhalf += secondones;
				secondzeroes++;
			}
		}
		
			// no changes in middle
		int ans=Math.abs(firsthalf-secondhalf);

		// 1 0  swap
		int moves=0;
		int fhalf = firsthalf;
		int fones = firstones;
		int fzeroes = firstzeroes;
		int shalf = secondhalf;
		int sones = secondones;
		int szeroes = secondzeroes;
		int[] arrc = new int[2*n];
		for (int i=0; i<2*n; i++) arrc[i] = arr[i];
		for (int i=0; i<n; i++) {
			if (fones == 0 || szeroes == 0) break;
			if (fhalf == shalf) {
				ans = Math.min(ans, moves);
				break;
			}
			if (moves >= ans) break;
			
			for (int j=n-1; j>=0; j--) {
				if (arrc[j] == 1) {
					if (n-1 != j) {
						arrc[j] = 0;
						arrc[n-1] = 1;
					}
					break;
				}
				fhalf--;
				moves++;
			}
			
			for (int j=n; j<2*n; j++) {
				if (arrc[j] == 0) {
					if (n != j) {
						arrc[n] = 0;
						arrc[j] = 1;
					}
					break;
				}
				shalf--;
				moves++;
			}
			
			fhalf += (fones-1);
			shalf += (szeroes - 1);
			fones--;
			fzeroes++;
			szeroes--;
			sones++;
			arrc[n-1] = 0;
			arrc[n] = 1;
			moves++;
			ans = Math.min(ans, Math.abs(fhalf - shalf) + moves);
		}
		
		// 0 1  swap
		moves=0;
		fhalf = firsthalf;
		fones = firstones;
		fzeroes = firstzeroes;
		shalf = secondhalf;
		sones = secondones;
		szeroes = secondzeroes;
		arrc = new int[2*n];
		for (int i=0; i<2*n; i++) arrc[i] = arr[i];
		for (int i=0; i<n; i++) {
			if (fzeroes == 0 || sones == 0) break;
			if (fhalf == shalf) {
				ans = Math.min(ans, moves);
				break;
			}
			if (moves >= ans) break;
			
			for (int j=n-1; j>=0; j--) {
				if (arrc[j] == 0) {
					if (n-1 != j) {
						arrc[j] = 1;
						arrc[n-1] = 0;
					}
					break;
				}
				fhalf++;
				moves++;
			}
			
			for (int j=n; j<2*n; j++) {
				if (arrc[j] == 1) {
					if (n != j) {
						arrc[n] = 1;
						arrc[j] = 0;
					}
					break;
				}
				shalf++;
				moves++;
			}
			
			fhalf -= (fones);
			shalf -= (szeroes);
			fzeroes--;
			fones++;
			sones--;
			szeroes++;
			arrc[n-1] = 1;
			arrc[n] = 0;
			moves++;
			ans = Math.min(ans, Math.abs(fhalf - shalf) + moves);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}