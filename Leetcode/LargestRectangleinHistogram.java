
import java.util.*;

public class LargestRectangleinHistogram {

	// https://leetcode.com/problems/largest-rectangle-in-histogram/
	
	public static void main(String[] args) {
		int[] arr = {2,1,5,6,2,3};
		largestRectangleArea(arr);
	}
	
	public static int largestRectangleArea(int[] heights) {
		int n = heights.length;
        ArrayDeque<Integer> s = new ArrayDeque<>();
        int maxArea = 0;
        int i=0;
        while (i<n) {
            if (s.isEmpty() || heights[i] > heights[s.peek()]) {
                s.push(i++);
            } 
            else {
                int cur = s.pop();
                int left = 0;
                if (!s.isEmpty()) left = s.peek()+1;
                int right = i-1;
                maxArea = Math.max(maxArea, heights[cur] * (right - left + 1));
            }
        }
        while (!s.isEmpty()) {
        	int cur = s.pop();
            int left = 0;
            if (!s.isEmpty()) left = s.peek()+1;
            int right = n-1;
            maxArea = Math.max(maxArea, heights[cur] * (right - left + 1));
        }
        return maxArea;
	}

	public int largestRectangleArea2(int[] heights) {
		int n = heights.length;
        int[] next = new int[n];
        int[] prev = new int[n];
        int[] psum = new int[n];
        A[] arr = new A[n];
        for (int i=0; i<n; i++) {
        	next[i] = i+1;
        	prev[i] = i-1;
        	if (i == 0) psum[i] = heights[0];
        	else psum[i] = psum[i-1] + heights[i];
        	arr[i] = new A(heights[i], i);
        }
        
        Arrays.parallelSort(arr);
        
        int ans=0;
        for (int i=0; i<n; i++) {
        	int left = prev[arr[i].index] + 1;
        	int right = next[arr[i].index] - 1;
        	ans = Math.max(ans, arr[i].val * (right - left + 1));
        	
        	if (next[arr[i].index] < n) prev[next[arr[i].index]] = prev[arr[i].index];
        	if (prev[arr[i].index] >= 0) next[prev[arr[i].index]] = next[arr[i].index];
        }
        
        return ans;
        
    }
	
	class A implements Comparable<A> {
		int val, index;
		A (int a, int b) {
			val = a; index = b;
		}
		public int compareTo(A o) {
			return o.val - val; 		// largest first
		}
	}
}