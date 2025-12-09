import java.util.*;
import java.io.*;

public class MoreCowPhotos {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1516

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-->0) {
            int n = Integer.parseInt(in.readLine());
            StringTokenizer st = new StringTokenizer(in.readLine());
            Map<Integer, Integer> map = new HashMap<>();
            int maxVal = 0;
            for (int i = 0; i < n; i++) {
                int v = Integer.parseInt(st.nextToken());
                map.put(v, map.getOrDefault(v, 0) + 1);
                maxVal = Math.max(maxVal, v);
            }

            // max val can only be placed in the middle
            map.remove(maxVal);

            int doubleCount = 0;
            for (Integer key : map.keySet()) {
                if (map.get(key) >= 2) doubleCount++;
            }

            sb.append(2 * doubleCount + 1);
            sb.append("\n");
        }

		System.out.print(sb);
	}
}