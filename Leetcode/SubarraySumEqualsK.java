import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
    
    // https://leetcode.com/problems/subarray-sum-equals-k/

    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] pref = new int[n];

        pref[0] = nums[0];
        for (int i = 1; i < n; i++) {
            pref[i] = pref[i-1] + nums[i];
        }

        Map<Integer, Integer> prefMap = new HashMap<>();
        prefMap.put(0, 1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // pref[i] - pref[x] = k
            int needed = pref[i] - k;
            ans += prefMap.getOrDefault(needed, 0);
            prefMap.put(pref[i], prefMap.getOrDefault(pref[i], 0) + 1);
        }

        return ans;
    }
}
