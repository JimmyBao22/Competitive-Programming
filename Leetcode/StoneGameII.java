public class StoneGameII {
    
    // https://leetcode.com/problems/stone-game-ii/description/

    public static final int INF = (int)(1e9);
    private int n;
    private int[] psum;
    private int[][][] memo;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        if (n == 1) return piles[0];

        psum = new int[n];
        psum[0] = piles[0];
        for (int i = 1; i < n; i++) {
            psum[i] = psum[i-1] + piles[i];
        }

        // memo[i][j][0] = max(Alice stones - Bob stones) -> max bc Alice try to maximize her stones
        // memo[i][j][1] = min(Alice stones - Bob stones) -> min bc Bob try to minimize # stones Alice gets
        memo = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j][0] = -INF;
                memo[i][j][1] = -INF;
            }
        }
        
        int v = dp(0, 1, 0);
        return (psum[n-1] + v) / 2;
    }

    private int dp(int i, int M, int turn) {
        if (i >= n) return 0;
        if (memo[i][M][turn] != -INF) return memo[i][M][turn];

        // at this point, can take any value X where 1 <= X <= 2M
        int ans = INF;
        if (turn == 0) ans = -INF; 

        int upperBound = Math.min(n-1, 2 * M);
        for (int X = 1; X <= upperBound; X++) {
            int rangeEnd = Math.min(i + X - 1, n-1);
            int stones = psum[rangeEnd];
            if (i-1 >= 0) stones -= psum[i-1];

            if (turn == 0) {
                // want to maximize
                ans = Math.max(ans, stones + dp(i + X, Math.max(M, X), 1));
            } else {
                // want to minimize
                ans = Math.min(ans, -stones + dp(i + X, Math.max(M, X), 0));
            }
        }

        return memo[i][M][turn] = ans;
    }

    private void print(int[][][] dp) {
        int n = dp.length;
        System.out.println("Alice");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dp[i][j][0] + " ");
            }
            System.out.println();
        }
        System.out.println("Bob");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dp[i][j][1] + " ");
            }
            System.out.println();
        }
    }
}
