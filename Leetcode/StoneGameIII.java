public class StoneGameIII {

    // https://leetcode.com/problems/stone-game-iii/

    public static final int INF = (int)(1e9);
    private int n;
    private int[][] memo;

    public String stoneGameIII(int[] piles) {
        n = piles.length;
        
        // stores alice score - bob score
        memo = new int[n][2];
        for (int i = 0; i < n; i++) {
            memo[i][0] = -INF;
            memo[i][1] = -INF;
        }
        
        int v = dp(0, 0, piles);
        // print(memo);
        
        if (v > 0) {
            return "Alice";
        } else if (v < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    private int dp(int i, int turn, int[] piles) {
        if (i >= n) return 0;
        if (memo[i][turn] != -INF) return memo[i][turn];


        int ans = -INF;
        if (turn == 1) ans = INF;

        int addVal = 0;
        for (int j = 0; j < 3; j++) {
            if (i + j < n) addVal += piles[i+j];
            if (turn == 0) {
                // Alice turn, want to maximize
                ans = Math.max(ans, dp(i+j+1, 1, piles) + addVal);
            } else {
                // Bob turn, want to minimize
                ans = Math.min(ans, dp(i+j+1, 0, piles) - addVal);
            }
        }

        return memo[i][turn] = ans;
    }

    private void print(int[][] dp) {
        int n = dp.length;
        System.out.println("Alice");
        for (int i = 0; i < n; i++) {
            System.out.print(dp[i][0] + " ");
        }
        System.out.println();
        System.out.println("Bob");
        for (int i = 0; i < n; i++) {
            System.out.print(dp[i][1] + " ");
        }
        System.out.println();
    }
}