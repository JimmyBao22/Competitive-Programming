import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MinimumIntervaltoIncludeEachQuery {

    // https://leetcode.com/problems/minimum-interval-to-include-each-query/
    
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int q = queries.length;

        TreeMap<Integer, List<Integer>> add = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            int size = right - left + 1;
            
            if (!add.containsKey(left)) {
                add.put(left, new ArrayList<>());
            }
            add.get(left).add(size);

            // Use negative to denote removal
            if (!add.containsKey(right + 1)) {
                add.put(right + 1, new ArrayList<>());
            }
            add.get(right + 1).add(-size);
        }

        // For each value, find the smallest interval size that contains it
        // [Index, size]
        TreeMap<Integer, Integer> smallestSize = new TreeMap<>();
        smallestSize.put(0, -1);
        // Map of [size, count] --> used as a sort of priorityqueue
        TreeMap<Integer, Integer> currentSizes = new TreeMap<>();

        // Only need to consider the indices when you add/remove an interval, because that's when something changes
        for (Integer index : add.keySet()) {
            for (Integer size : add.get(index)) {
                if (size < 0) {
                    currentSizes.put(-size, currentSizes.get(-size) - 1);
                    if (currentSizes.get(-size) == 0) currentSizes.remove(-size);
                } else {
                    currentSizes.put(size, currentSizes.getOrDefault(size, 0) + 1);
                }
            }

            smallestSize.put(index, currentSizes.isEmpty() ? -1 : currentSizes.firstKey());
        }

        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int index = smallestSize.floorKey(queries[i]);
            ans[i] = smallestSize.get(index);
        }

        return ans;
    }
}
