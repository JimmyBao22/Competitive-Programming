import java.util.Arrays;

public class CanIWin {
    
    // https://leetcode.com/problems/can-i-win/description/

    private int[] memo;

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) return true;
        if (maxChoosableInteger * (maxChoosableInteger + 1) / 2 < desiredTotal) return false;
        memo = new int[1 << (maxChoosableInteger+1)];
        Arrays.fill(memo, -1);
        dp(0, maxChoosableInteger, desiredTotal);
        return memo[0] == 1;
    }

    private int dp(int usedNumbers, int maxChoosableInteger, int desiredTotal) {
        if (memo[usedNumbers] != -1) return memo[usedNumbers];
        int sum = 0;
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (((usedNumbers >> i) & 1) == 1) sum += i;
        }
        if (sum >= desiredTotal) return memo[usedNumbers] = 0;

        // try to take any of the available numbers and see if its possible to force a win
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (((usedNumbers >> i) & 1) == 0) {
                if (dp(usedNumbers | (1 << i), maxChoosableInteger, desiredTotal) == 0) {
                    // you can force a win by choosing integer i
                    return memo[usedNumbers] = 1;
                }
            }
        }

        // you cannot force a win by choosing any available integer
        return memo[usedNumbers] = 0;
    }
}
