
import java.util.*;

public class ContainerWithMostWater {

	// https://leetcode.com/problems/container-with-most-water/
	
	public static void main(String[] args) {

	}
	
	public int maxArea(int[] height) {
		int n = height.length;
		HashMap<Integer,Integer> goingright = new HashMap<>();
			// index, value
		HashMap<Integer,Integer> goingleft = new HashMap<>();
		int prevval = height[0];
		goingright.put(0, height[0]);
		for (int i=1; i<n; i++) {
			if (height[i] > prevval) {
				prevval = height[i];
				goingright.put(i, height[i]);
			}
		}
		prevval = height[n-1];
		goingleft.put(n-1, height[n-1]);
		for (int i=n-2; i>=0; i--) {
			if (height[i] > prevval) {
				prevval = height[i];
				goingleft.put(i, height[i]);
			}
		}
		
		int max = 0;
		for (Integer left : goingright.keySet()) {
			for (Integer right : goingleft.keySet()) {
				if (left < right) {
					max = Math.max(max, (right - left) * Math.min(goingright.get(left), goingleft.get(right)));
				}
			}
		}
		return max;
    }
}
