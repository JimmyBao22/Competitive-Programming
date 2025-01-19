
import java.util.*;

public class NumberofVisiblePeopleinaQueue {

	public static void main(String[] args) {

	}

	public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        
        // ans[i] = increase sequence of increasing heights
        Stack<Integer> s = new Stack<>();
        s.push(heights[n-1]);
        for (int i=n-2; i>=0; i--) {
            int count = 0;
            
            while (!s.empty() && s.peek() < heights[i]) {
                count++;
                s.pop();
            }
            
            ans[i] = count + (s.empty() ? 0 : 1);

            s.push(heights[i]);
        }

        return ans;
    }
}

        // for (int i=0; i<n-1; i++) {
        //     int count=0;
        //     int prev = -1;
        //     for (int j=i+1; j<n; j++) {
        //         if (heights[j] > prev) {
        //             prev = heights[j];
        //             count++;
        //         }
        //         if (heights[j] > heights[i]) break;
        //     }
        //     ans[i] = count;
        // }