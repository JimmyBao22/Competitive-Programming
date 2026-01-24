import java.util.*;
import java.io.*;

public class FarmerJohnCheeseBlock {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1444

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // keep track of how many blocks have been removed for each xy pair, xz pair, and yz pair
        Map<Integer, Integer> xy = new HashMap<>();
        Map<Integer, Integer> xz = new HashMap<>();
        Map<Integer, Integer> yz = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        
        int configs = 0;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            int xyKey = x*2000+y;
            int xzKey = x*2000+z;
            int yzKey = y*2000+z;
            xy.put(xyKey, xy.getOrDefault(xyKey, 0) + 1);
            xz.put(xzKey, xz.getOrDefault(xzKey, 0) + 1);
            yz.put(yzKey, yz.getOrDefault(yzKey, 0) + 1);

            // check if n blocks have been removed for a key
            if (xy.get(xyKey) == n) {
                configs++;
            }
            if (xz.get(xzKey) == n) {
                configs++;
            }
            if (yz.get(yzKey) == n) {
                configs++;
            }

            sb.append(configs);
            sb.append("\n");
        }

		System.out.print(sb);
	}
}