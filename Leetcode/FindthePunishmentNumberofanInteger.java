public class FindthePunishmentNumberofanInteger {
    
    // https://leetcode.com/problems/find-the-punishment-number-of-an-integer/submissions/1547949653/?envType=daily-question&envId=2025-02-15
    
    public int punishmentNumber(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            int prod = i * i;
            String s = prod + "";
            if (works(0, s, i, -1)) {
                System.out.println(prod);
                ans += prod;
            }
        }
        return ans;
    }

    private boolean works(int i, String s, int goal, int lastTook) {
        if (goal < 0) return false;
        if (i == s.length()) return goal == 0 && lastTook == s.length()-1;

        // take here
        int curNum = Integer.parseInt(s.substring(lastTook+1, i+1));
        if (works(i+1, s, goal - curNum, i)) return true;

        // don't take here
        return works(i+1, s, goal, lastTook);
    }
}
