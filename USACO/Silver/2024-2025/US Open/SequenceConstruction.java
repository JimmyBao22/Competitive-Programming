
import java.util.*;
import java.io.*;

public class SequenceConstruction {
    
    // https://usaco.org/index.php?page=viewproblem2&cpid=1518

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-->0) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // find smallest sequence of numbers s.t. it has xor of popcounts = K
            long sum = 0;
            List<Integer> vals = new ArrayList<>();
            for (int i = 31; i >= 0; i--) {
                if (((k >> i) & 1) == 1) {
                    // the smallest number that has popcount = (1 << i) is (1 << (1 << i)) - 1
                    int v = (1 << (1 << i)) - 1;
                    sum += v;
                    vals.add(v);
                }
            }

            if (sum > m) {
                sb.append(-1);
                sb.append("\n");
                continue;
            }

            m -= sum;
            if ((m & 1) == 1) {
                if (m == 1) {
                    // only possible if vals contains a 1
                    if (vals.get(vals.size() - 1) == 1) {
                        vals.remove(vals.size() - 1);
                        vals.add(2);
                        m = 0;
                    } else {
                        sb.append(-1);
                        sb.append("\n");
                        continue;
                    }
                } else {
                    m -= 3;
                    vals.add(1);
                    vals.add(2);
                }
            }

            // once m is even, just utilize m/2 twice. the xor will cancel out
            assert (m & 1) == 0;
            m >>= 1;
            if (m > 0) {
                vals.add(m);
                vals.add(m);
            }

            sb.append(vals.size());
            sb.append("\n");
            for (int i = 0; i < vals.size(); i++) {
                sb.append(vals.get(i) + " ");
            }
            sb.append("\n");
        }

		System.out.print(sb);
	}
}