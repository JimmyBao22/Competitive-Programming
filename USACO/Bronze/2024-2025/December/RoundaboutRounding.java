import java.util.*;
import java.io.*;

public class RoundaboutRounding {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1443

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-->0) {
            int n = Integer.parseInt(in.readLine());
            int p = 0;
            int temp = n;
            while (temp > 0) {
                temp /= 10;
                p++;
            }

            long numValues = 0;
            long sum = 0;
            long lowerBound = 4;
            long upperBound = 4;
            for (int i = 1; i < p; i++) {
                sum += numValues;
                numValues = numValues * 10 + 5;
                lowerBound = lowerBound * 10 + 4;
                upperBound = upperBound * 10 + 9;
            }
            lowerBound++;

            if (n >= lowerBound) {
                if (n >= upperBound) {
                    sum += (upperBound - lowerBound + 1);
                } else {
                    sum += (n - lowerBound + 1);
                }
            }

            sb.append(sum);
            sb.append("\n");
        }

		System.out.print(sb);
	}
}

/*
    values of x:
    45-49
    445-499
    4445-4999
    ...
*/