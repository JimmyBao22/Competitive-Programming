
import java.util.*;
import java.io.*;

public class GuessYourWayOut {

	// https://codeforces.com/contest/507/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GuessYourWayOut"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		long h = Long.parseLong(st.nextToken());
		long n = Long.parseLong(st.nextToken());
		long total = (long) Math.pow(2, h);
		long steps =0;
		boolean right=false;
		while (total >= 2) {
			steps++;
			if (!right) {
				if (n > total/2) {
					// we are not in right tree
					steps += total-1;
					right = !right;
					n = n-total/2;
				}
			}
			else {
				if (n <= total/2) {
					steps += total-1;
					right = !right;
				}
				else {
					n = n-total/2;
				}
			}
			total /=2;
			right = !right;
		}
		System.out.println(steps);
	}
}