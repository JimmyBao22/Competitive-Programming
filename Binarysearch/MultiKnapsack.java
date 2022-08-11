
import java.util.*;
import java.io.*;

public class MultiKnapsack {

    public static void main(String[] args) {

    }

    // https://binarysearch.com/problems/Multi-Knapsack

    int n;
    A[] arr;
    int[][][] memo;

    public int solve(int[] weights, int[] values, int capacity, int count) {
        n = weights.length;
        arr = new A[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new A(weights[i], values[i]);
        }

        memo = new int[n][capacity + 1][count + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < capacity + 1; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        return dp(0, 0, 0, capacity, count);
    }

    public int dp(int i, int c, int num, int capacity, int count) {
        if (i >= n || c > capacity || num >= count)
            return 0;
        if (memo[i][c][num] != -1)
            return memo[i][c][num];
        int ans = 0;

        // take
        if (c + arr[i].w <= capacity) {
            ans = arr[i].v + dp(i + 1, c + arr[i].w, num + 1, capacity, count);
        }

        // don't take
        ans = Math.max(ans, dp(i + 1, c, num, capacity, count));

        return memo[i][c][num] = ans;
    }
}
