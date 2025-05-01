import java.util.Arrays;
import java.util.TreeMap;

public class MaximumNumberofTasksYouCanAssign {
    
    // https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/description/?envType=daily-question&envId=2025-05-01

    private int n, m;

    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        n = tasks.length;
        m = workers.length;
        Arrays.sort(tasks);
        Arrays.sort(workers);
        int min = 0;
        int max = n;
        while (min < max) {
            int mid = (min + max + 1) / 2;
            if (check(mid, tasks, workers, pills, strength)) {
                min = mid;
            } else {
                max = mid-1;
            }
        }
        return min;
    }

    private boolean check(int index, int[] tasks, int[] workers, int pills, int strength) {
        if (m - index < 0) {
            // there aren't enough workers for this number of tasks
            return false;
        }

        TreeMap<Integer, Integer> workerVals = new TreeMap<>();
        for (int i = m-index; i < m; i++) {
            workerVals.put(workers[i], workerVals.getOrDefault(workers[i], 0) + 1);
        }

        for (int i = index-1; i >= 0; i--) {
            Integer lastKey = workerVals.lastKey();
            if (lastKey >= tasks[i]) {
                // this works, use this worker
                workerVals.put(lastKey, workerVals.get(lastKey) - 1);
                if (workerVals.get(lastKey) == 0) 
                    workerVals.remove(lastKey);
            } else {
                // need to use a pill
                if (pills == 0) 
                    return false;
                Integer workerWithPill = workerVals.ceilingKey(tasks[i] - strength);
                if (workerWithPill == null) 
                    return false;
                pills--;
                workerVals.put(workerWithPill, workerVals.get(workerWithPill) - 1);
                if (workerVals.get(workerWithPill) == 0) 
                    workerVals.remove(workerWithPill);
            }
        }

        return true;
    }
}

/*
    Lets say I have a set of task and workers and pills.
    Is it possible to complete all these tasks with the given workers and pills?
    Then, we can use binary search to maximize this set of tasks.

    To find if its possible to complete these tasks given workers and pills

    t = [5,10]
    w = [4,9]
    pills = 1, strength = 10
    ans = 2

    t = [5,10]
    w = [4,9]
    pills = 2, strength = 1
    ans = 2
*/