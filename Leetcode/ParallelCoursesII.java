import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ParallelCoursesII {

    // https://leetcode.com/problems/parallel-courses-ii/

    private Map<Integer, Integer> memo;
    private List<Integer>[] g;
    private int[] indegree;
    private static final int INF = (int)(1e9);

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        g = new ArrayList[n];
        indegree = new int[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < relations.length; i++) {
            int prev = relations[i][0] - 1;
            int next = relations[i][1] - 1;
            g[prev].add(next);
            indegree[next]++;
        }

        // [courses taken (as binary), # of semesters]
        memo = new HashMap<>();

        return dp(0, n, k);
    }

    private int dp(int coursesTaken, int n, int k) {
        if (memo.containsKey(coursesTaken)) {
            return memo.get(coursesTaken);
        }

        // Preprocess some information for this iteration
        int[] curIndegree = new int[n];
        List<Integer> needToTake = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            curIndegree[i] += indegree[i];

            if (((coursesTaken >> i) & 1) == 0) {
                needToTake.add(i);
            } else {
                // Already taken this course
                for (Integer to : g[i]) {
                    curIndegree[to]--;
                }
            }
        }

        // Already taken all courses
        int m = needToTake.size();
        if (m == 0) {
            return 0;
        }

        // Try all possibilities of new courses that can be taken this semester
        int ans = INF;
        outer: for (int i = 1; i < (1 << m); i++) {
            int count = 0;
            int newCoursesTaken = coursesTaken;
            for (int j = 0; j < m; j++) {
                if (((i >> j) & 1) == 1) {
                    int actualIndex = needToTake.get(j);
                    count++;
                    if (count > k || curIndegree[actualIndex] != 0) continue outer;
                    newCoursesTaken += (1 << actualIndex);
                }
            }

            ans = Math.min(ans, 1 + dp(newCoursesTaken, n, k));
        }

        memo.put(coursesTaken, ans);
        return ans;
    }
}