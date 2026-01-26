import java.io.*;
import java.util.*;

public class FarmerJohnCheeseBlock2 {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1444

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // keep track of how many blocks have been removed for each xy pair, xz pair, and yz pair
        int[][] xy = new int[n][n];
        int[][] xz = new int[n][n];
        int[][] yz = new int[n][n];

        StringBuilder sb = new StringBuilder();
        
        int configs = 0;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            xy[x][y]++;
            xz[x][z]++;
            yz[y][z]++;

            // check if n blocks have been removed for a key
            if (xy[x][y] == n) {
                configs++;
            }
            if (xz[x][z] == n) {
                configs++;
            }
            if (yz[y][z] == n) {
                configs++;
            }

            sb.append(configs);
            sb.append("\n");
        }

		System.out.print(sb);
	}
}