import java.util.Arrays;

public class ShortestCommonSupersequence {
    
    // https://leetcode.com/problems/shortest-common-supersequence/description/?envType=daily-question&envId=2025-02-28

    private int n, m;
    private final int MAXN = 10000;
    private int[][] memo, next;

    public String shortestCommonSupersequence(String str1, String str2) {
        n = str1.length();
        m = str2.length();
        memo = new int[n][m];
        next = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        dp(0, 0, str1, str2);

        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        while (i < n && j < m) {
            int nextI = next[i][j] / MAXN;
            int nextJ = next[i][j] % MAXN;
            if (nextI == i+1) {
                sb.append(str1.charAt(i));
            } else {
                sb.append(str2.charAt(j));
            }
            i = nextI;
            j = nextJ;
        }
        while (i < n) sb.append(str1.charAt(i++));
        while (j < m) sb.append(str2.charAt(j++));

        return sb.toString();
    }

    private int dp(int i, int j, String str1, String str2) {
        if (i == n && j == m) return 0;
        if (i == n) return m - j;
        if (j == m) return n - i;
        if (memo[i][j] != -1) return memo[i][j];

        int minVal = Integer.MAX_VALUE;
        if (str1.charAt(i) == str2.charAt(j)) {
            // can move in both direction
            minVal = dp(i+1, j+1, str1, str2);
            next[i][j] = (i+1) * MAXN + (j+1);
        }

        // move in direction of i
        int ret = dp(i+1, j, str1, str2);
        if (ret < minVal) {
            minVal = ret;
            next[i][j] = (i+1) * MAXN + j;
        }

        // move in direction of j
        ret = dp(i, j+1, str1, str2);
        if (ret < minVal) {
            minVal = ret;
            next[i][j] = i * MAXN + (j+1);
        }

        return memo[i][j] = minVal + 1;
    }

}
