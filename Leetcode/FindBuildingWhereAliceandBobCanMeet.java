import java.util.List;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class FindBuildingWhereAliceandBobCanMeet {

    // https://leetcode.com/problems/find-building-where-alice-and-bob-can-meet/description/?envType=daily-question&envId=2024-12-22

    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int n = heights.length;
        int q = queries.length;

        @SuppressWarnings("unchecked")
        List<Integer>[] queriesByIndices = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            queriesByIndices[i] = new ArrayList<>();
        }
        
        boolean[] processed = new boolean[q];
        for (int i = 0; i < q; i++) {
            if (queries[i][0] > queries[i][1]) swap(queries[i]);
            queriesByIndices[queries[i][0]].add(i);
            queriesByIndices[queries[i][1]].add(i);
        }

        // sort heights
        Pair[] sortedHeights = new Pair[n];
        for (int i = 0; i < n; i++) {
            sortedHeights[i] = new Pair(heights[i], i);
        }
        Arrays.sort(sortedHeights, new Comparator<Pair>() {
            public int compare(Pair a, Pair b) {
                if (a.getKey() == b.getKey()) return a.getValue() - b.getValue();
                return b.getKey() - a.getKey();  // sort descending
            }
        });

        int[] ans = new int[q];
        // store all indices that get processed. when i'm currently trying to process index i, every index stored in this treeset will have height > height[i]
        // this allows me to quickly find the "next largest index" to move to
        TreeSet<Integer> indicesAvailable = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            Pair curPair = sortedHeights[i];
            int curIndex = curPair.getValue();

            // go through all queries associted with this index
            List<Integer> curQueries = queriesByIndices[curIndex];
            int m = curQueries.size();
            for (int j = 0; j < m; j++) {
                int queryJ = curQueries.get(j);
                if (processed[queryJ]) continue;
                
                // alice < bob
                int alice = queries[queryJ][0];
                int bob = queries[queryJ][1];
                if (alice == bob) {
                    ans[queryJ] = bob;
                } else if (heights[alice] < heights[bob]) {
                    ans[queryJ] = bob;
                } else {
                    // find next available index > bob that has height > both
                    Integer nextAvailableIndex = indicesAvailable.higher(bob);
                    if (nextAvailableIndex == null) ans[queryJ] = -1;
                    else ans[queryJ] = nextAvailableIndex;
                }

                processed[queryJ] = true;
            }

            indicesAvailable.add(curIndex);
        }

        return ans;
    }

    private class Pair {
        private int a, b;
        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
        public int getKey() {
            return a;
        }
        public int getValue() {
            return b;
        }
    }

    private void swap(int[] arr) {
        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;
    }
}