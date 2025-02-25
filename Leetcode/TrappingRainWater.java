public class TrappingRainWater {

	public static void main(String[] args) {
		int[] arr = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(trap(arr));
	}
	
	// https://leetcode.com/problems/trapping-rain-water/

	public static int trap(int[] height) {
        int[] arr = new int[height.length+2];
        // add '0' to both sides
        for (int i=0; i<height.length; i++) {
            arr[i+1] = height[i];
        }
        int n = arr.length;
        
        int[] psum = new int[n];
        for (int i=1; i<n; i++) psum[i] = psum[i-1] + arr[i];
        
        int[] maxgoingright = new int[n];
        int[] maxgoingleft = new int[n];
        for (int i=1; i<n; i++) {
        	maxgoingleft[i] = Math.max(maxgoingleft[i-1], arr[i]);
        }
        for (int i=n-2; i>=0; i--) {
        	maxgoingright[i] = Math.max(maxgoingright[i+1], arr[i]);
        }
 
        int ans = 0;
        
        for (int i=1; i<n-1; i++) {
        	int boundaries = Math.min(maxgoingleft[i], maxgoingright[i]);
        	int diff = boundaries - arr[i];
        	ans += diff;
        }
        
        return ans;
    }
}
