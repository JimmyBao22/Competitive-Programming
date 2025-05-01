class Solution {

    // https://leetcode.com/problems/count-good-triplets-in-an-array/?envType=daily-question&envId=2025-04-15
    
    public long goodTriplets(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] indicesOfNum2 = new int[n];
        for (int i = 0; i < n; i++) {
            indicesOfNum2[nums2[i]] = i;
        }

        // First find the number of good doubles. Do this by summing what indices have already been passsed in num2 with the BIT
        // goodDoubles[i] = # of good doubles ending at pos i
        BIT bitDoubles = new BIT(n);
        long[] goodDoubles = new long[n];
        for (int i = 0; i < n; i++) {
            int val = nums1[i];
            int index = indicesOfNum2[val];
            goodDoubles[index] = bitDoubles.sum(index);
            bitDoubles.add(index, 1);
        }

        // To find the number of good triples, use the same as above, except instead of directly summing number of indices passed, sum the number of ways those indices could've become good doubles
        BIT bitTriples = new BIT(n);
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int val = nums1[i];
            int index = indicesOfNum2[val];
            ans += bitTriples.sum(index);
            bitTriples.add(index, goodDoubles[index]); 
        }

        return ans;
    }

    private class BIT {
        int n;
        long[] f;
        BIT(int n) {
            this.n = n+1;
            f = new long[this.n];
        }

        void add(int i, long x) {
            i++;
            while (i < n) {
                f[i] += x;
                i += i&-i;
            }
        }

        long sum(int i) {
            i++;
            long s = 0;
            while (i > 0) {
                s += f[i];
                i -= i&-i;
            }
            return s;
        }
    }
}