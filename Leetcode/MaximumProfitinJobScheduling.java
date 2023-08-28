import java.util.*;

public class MaximumProfitinJobScheduling {

	// https://leetcode.com/problems/maximum-profit-in-job-scheduling/description/
	
	public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        Arrays.sort(jobs);      // sort by endtime

        int[] dp = new int[n];              // max profit ending at jobs[i]
        for (int i = 0; i < n; i++) {

            // search for index s.t. endtime of index <= this job's start time
            int min = -1;
            int max = i - 1;
            while (min < max) {
                int middle = (min + max + 1) / 2;
                if (jobs[middle].endTime > jobs[i].startTime) {
                    max = middle - 1;
                }
                else {
                    min = middle;
                }
            }

            if (min == -1) {
                dp[i] = jobs[i].profit;
            }
            else {
                dp[i] = jobs[i].profit + dp[min];
            }

            if (i != 0) dp[i] = Math.max(dp[i], dp[i-1]);
        }

        return dp[n-1];
    }

    class Job implements Comparable<Job> {
        int startTime, endTime, profit;
        Job (int a, int b, int c) {
            startTime = a;
            endTime = b;
            profit = c;
        }

        public int compareTo(Job o) {
            return endTime - o.endTime;
        }
    }
}
