import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.TreeMap;

class ApplyOperationstoMaximizeScore {

    // https://leetcode.com/problems/apply-operations-to-maximize-score/?envType=daily-question&envId=2025-03-29

    private int MOD = (int)(1e9+7);

    public int maximumScore(List<Integer> nums, int k) {
        int n = nums.size();
        int[] primeScore = new int[n];
        for (int i = 0; i < n; i++) {
            primeScore[i] = getPrimeScore(nums.get(i));
        }

        int[] left = new int[n];    // left[i] = min index l s.t. for [l,i], element i has the highest prime score
        int[] right = new int[n];   // right[i] = max index r s.t. for [i, r], element i has the highest prime score
        
        Arrays.fill(right, n-1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()) {
                int before = stack.peek();
                if (primeScore[before] >= primeScore[i]) {
                    left[i] = before + 1;
                    break;
                } else {
                    right[before] = i - 1;
                    stack.pop();
                }
            }
            stack.push(i);
        }

        TreeMap<Long, Long> primeScoreCounts = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            long count = (long)(i - left[i] + 1) * (right[i] - i + 1);
            primeScoreCounts.put((long)nums.get(i), primeScoreCounts.getOrDefault((long)nums.get(i), 0l) + count);
        }

        // System.out.println(Arrays.toString(primeScore));
        // System.out.println(Arrays.toString(left));
        // System.out.println(Arrays.toString(right));
        // System.out.println(primeScoreCounts);

        long maxScore = 1;
        for (Long val : primeScoreCounts.descendingKeySet()) {
            long exponent = Math.min(k, primeScoreCounts.get(val));
            maxScore *= pow(val, exponent);
            maxScore %= MOD;
            k -= Math.min(k, primeScoreCounts.get(val));
            if (k == 0) break;
        }

        return (int)maxScore;
    }

    private long pow(long a, long b) {
        long x = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                x *= a;
                x %= MOD;
            }
            a *= a;
            a %= MOD;
            b >>= 1;
        }
        return x;
    }

    private int getPrimeScore(int x) {
        int score = 0;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                score++;
                while (x % i == 0) x /= i;
            }
        }
        if (x != 1) score++;
        return score;
    }
}