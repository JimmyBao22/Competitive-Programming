import java.util.ArrayList;
import java.util.List;

public class LongestPathWithDifferentAdjacentCharacters {
    
    // https://leetcode.com/problems/longest-path-with-different-adjacent-characters/

    private List<Integer>[] g;

    @SuppressWarnings("unchecked")
    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
            g[i].add(parent[i]);
        }

        return dfs(0, -1, s)[0];
    }

    // return [longest length that can't be extended, longest length that can]
    private int[] dfs(int node, int parent, String s) {
        int longestLength = 0;
        int longestExtendable = 0;
        int secondLongestExtendable = 0;

        for (Integer to : g[node]) {
            if (to != parent) {
                int[] ret = dfs(to, node, s);
                
                longestLength = Math.max(longestLength, ret[0]);

                // see if I can extend current length to child
                if (s.charAt(node) != s.charAt(to)) {
                    if (ret[1] > longestExtendable) {
                        secondLongestExtendable = longestExtendable;
                        longestExtendable = ret[1];
                    } else {
                        secondLongestExtendable = Math.max(secondLongestExtendable, ret[1]);
                    }
                }
            }
        }

        longestLength = Math.max(longestLength, longestExtendable + 1 + secondLongestExtendable);

        return new int[]{longestLength, 1 + longestExtendable};
    }
}
