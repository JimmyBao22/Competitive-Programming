import java.util.*;
import java.io.*;

public class CakeGame {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1446

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-->0) {
            int n = Integer.parseInt(in.readLine());
            long[] arr = new long[n];
            long totalSum = 0;
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                totalSum += arr[i];
            }

            // try every combo of what elsie can take
            int elsieCount = (n / 2) - 1;
            long elsieMaxSum = 0;
            long curSum = 0;

            // elsie takes all left
            for (int i = 0; i < elsieCount; i++) {
                curSum += arr[i];
            }
            elsieMaxSum = curSum;

            // try all other combos
            for (int i = n-1; i >= n-elsieCount; i--) {
                curSum += arr[i];
                int rightCount = n - i;
                curSum -= arr[elsieCount - rightCount];
                elsieMaxSum = Math.max(elsieMaxSum, curSum);
            }

            sb.append((totalSum - elsieMaxSum) + " " + elsieMaxSum);
            sb.append("\n");
        }

		System.out.print(sb);
	}
}