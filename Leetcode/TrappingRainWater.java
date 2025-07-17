public class TrappingRainWater {

    // https://leetcode.com/problems/trapping-rain-water/

	public static void main(String[] args) {
		int[] arr = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(trap(arr));
	}
	
    public static int trap(int[] height) {
        int n = height.length;
        int l = 0;
        int r = n-1;
        int maxL = 0;
        int maxR = 0;
        int trappedRainwater = 0;
        while (l <= r) {
            maxL = Math.max(maxL, height[l]);
            maxR = Math.max(maxR, height[r]);
            int minBoth = Math.min(maxL, maxR);
            if (height[l] <= height[r]) {
                trappedRainwater += Math.max(0, minBoth - height[l]);
                l++;
            }
            else {
                trappedRainwater += Math.max(0, minBoth - height[r]);
                r--;
            }
        }
        
        return trappedRainwater;
    }
}
