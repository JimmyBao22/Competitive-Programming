public class SubarraySumsDivisiblebyK {

    // https://leetcode.com/problems/subarray-sums-divisible-by-k/

    public int subarraysDivByK(int[] nums, int k) {
        int n = nums.length;
        int[] pref = new int[n];
        pref[0] = (nums[0] % k + k) % k;
        for (int i = 1; i < n; i++) {
            pref[i] = pref[i-1] + nums[i];
            pref[i] = (pref[i] % k + k) % k;
        }

        int[] modulos = new int[k];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += modulos[pref[i]];
            if (pref[i] == 0) ans++;
            modulos[pref[i]]++;
        }


        return ans;
    }
    
}
