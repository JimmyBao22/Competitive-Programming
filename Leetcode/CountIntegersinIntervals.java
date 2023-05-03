
import java.util.*;

public class CountIntegersinIntervals {

	// https://leetcode.com/problems/count-integers-in-intervals/
	
	public static void main(String[] args) {

	}
	
    class CountIntervals {

        TreeMap<Integer, Integer> intervals;        // left = right
        int count = 0;
    
        public CountIntervals() {
            intervals = new TreeMap<>();
        }
        
        // merge intervals
        public void add(int left, int right) {
            int curCount = 0;
            Integer lower = intervals.floorKey(left);
            int next = left;
            if (lower != null && intervals.get(lower) >= left - 1) {
                curCount += Math.min(intervals.get(lower), right) - left + 1;
                next = intervals.get(lower);
                intervals.remove(lower);
            }
            else {
                lower = left;
            }
    
            while (next <= right + 1) {
                Integer higher = intervals.ceilingKey(next);
                if (higher != null && higher <= right + 1) {
                    curCount += Math.min(intervals.get(higher), right) - higher + 1;
                }
                else break;
    
                next = intervals.get(higher);
                intervals.remove(higher);
            }
            next = Math.max(next, right);
    
            count += (right - left + 1) - curCount;
    
            intervals.put(lower, next);
        }
        
        public int count() {
            return count;
        }
    }
    
    /**
     * Your CountIntervals object will be instantiated and called as such:
     * CountIntervals obj = new CountIntervals();
     * obj.add(left,right);
     * int param_2 = obj.count();
     */
}