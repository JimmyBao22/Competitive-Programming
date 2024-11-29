import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FindKPairswithSmallestSums {

    // https://leetcode.com/problems/find-k-pairs-with-smallest-sums/description/
    
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        List<List<Integer>> ans = new ArrayList<>();

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>(){
            public int compare(Pair a, Pair b) {
                return nums1[a.nums1Index] + nums2[a.nums2Index] - (nums1[b.nums1Index] + nums2[b.nums2Index]);
            }
        });

        // put every pair in of the form (nums1[i], nums2[0])
        for (int i = 0; i < n; i++) {
            pq.add(new Pair(i, 0));
        }

        while (ans.size() < k) {
            Pair smallest = pq.poll();
            
            // add smallest pair to answer list
            List<Integer> smallestPair = new ArrayList<>();
            smallestPair.add(nums1[smallest.nums1Index]);
            smallestPair.add(nums2[smallest.nums2Index]);
            ans.add(smallestPair);

            // add new pair possibility to pq
            // keep nums1Index constant, only move forward nums2Index
            if (smallest.nums2Index + 1 < m) {
                smallest.nums2Index++;
                pq.add(smallest);
            }
        }

        return ans;
    }

    private class Pair {
        int nums1Index, nums2Index;
        Pair (int nums1Index, int nums2Index) {
            this.nums1Index = nums1Index;
            this.nums2Index = nums2Index;
        }
    }
}

/*
    Another Solution:

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        List<List<Integer>> ans = new ArrayList<>();

        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>(){
            public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
                return nums1[a.getKey()] + nums2[a.getValue()] - (nums1[b.getKey()] + nums2[b.getValue()]);
            }
        });

        pq.add(new Pair<Integer, Integer>(0, 0));
        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        while (ans.size() < k) {
            Pair<Integer, Integer> smallest = pq.poll();
            if (visited.contains(smallest)) continue;
            visited.add(smallest);

            int i = smallest.getKey();
            int j = smallest.getValue();

            // add smallest pair to answer list
            List<Integer> smallestPair = new ArrayList<>();
            smallestPair.add(nums1[i]);
            smallestPair.add(nums2[j]);
            ans.add(smallestPair);

            // add new pair possibility to pq
            if (i+ 1 < n) {
                pq.add(new Pair<Integer, Integer>(i + 1, j));
            }
            if (j + 1 < m) {
                pq.add(new Pair<Integer, Integer>(i, j+1));
            }
        }

        return ans;
    }
*/
