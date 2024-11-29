import java.util.*;

class SlidingWindowMaximum {

    // https://leetcode.com/problems/sliding-window-maximum/description/

    Stack<Integer> maxLeft;
    Stack<Integer> maxRight;
    Stack<Integer> curLeft;
    Stack<Integer> curRight;

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int INF = (int)(1e9);

        maxLeft = new Stack<>();
        maxRight = new Stack<>();
        curLeft = new Stack<>();
        curRight = new Stack<>();

        for (int i = 0; i < k; i++) {
            if (maxRight.isEmpty()) {
                maxRight.push(nums[i]);
            } else {
                maxRight.push(Math.max(nums[i], maxRight.peek()));
            }
            curRight.push(nums[i]);
        }

        int[] ans = new int[n - k + 1];
        ans[0] = maxRight.peek();

        for (int i = k; i < n; i++) {
            // remove nums[i - k]
            if (maxLeft.isEmpty()) {
                switchStacks();
            }
            maxLeft.pop();
            curLeft.pop();

            // add nums[i]
            if (maxRight.isEmpty()) {
                maxRight.push(nums[i]);
            } else {
                maxRight.push(Math.max(nums[i], maxRight.peek()));
            }
            curRight.push(nums[i]);

            int leftMax = maxLeft.isEmpty() ? -INF : maxLeft.peek();
            int rightMax = maxRight.isEmpty() ? -INF : maxRight.peek();
            ans[i - k + 1] = Math.max(leftMax, rightMax);
        }

        return ans;
    }

    public void switchStacks() {
        while (!curRight.isEmpty()) {
            maxRight.pop();
            int val = curRight.pop();
            if (maxLeft.isEmpty()) {
                maxLeft.push(val);
            } else {
                maxLeft.push(Math.max(val, maxLeft.peek()));
            }
            curLeft.push(val);
        }
    }
}

/*

// another solution that's O(nlogn) instead of O(n)

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        TreeMap<Integer, Integer> count = new TreeMap<>();
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < n; i++) {
            if (i >= k) {
                // remove element nums[i - k]
                count.put(nums[i - k], count.get(nums[i - k]) - 1);
                if (count.get(nums[i - k]) == 0) {
                    count.remove(nums[i - k]);
                }
            }

            // add element nums[i]
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);

            if (i >= k-1) {
                ans[i - k + 1] = count.lastKey();
            }
        }

        return ans;
    }
}

 */