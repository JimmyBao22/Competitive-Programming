import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ParallelCoursesIII {
    
    // https://leetcode.com/problems/parallel-courses-iii/description/

    List<Integer>[] g;
    int[] indegree;
    int[] minTime;

    public int minimumTime(int n, int[][] relations, int[] time) {
        g = new ArrayList[n];
        indegree = new int[n];
        minTime = new int[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < relations.length; i++) {
            int prev = relations[i][0] - 1;
            int next = relations[i][1] - 1;
            g[prev].add(next);
            indegree[next]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.add(i);
                minTime[i] = time[i];
            }
        }

        while (!q.isEmpty()) {
            int prev = q.poll();
            for (Integer next : g[prev]) {
                indegree[next]--;
                minTime[next] = Math.max(minTime[next], minTime[prev] + time[next]);
                if (indegree[next] == 0) {
                    q.add(next);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, minTime[i]);
        }

        return ans;
    }
}
