public class PalindromicSubstrings {

	// https://leetcode.com/problems/palindromic-substrings/
	
	public static void main(String[] args) {
		String s = "zhzhz";
		s = "aaa";
		System.out.println(countSubstrings(s));
	}
	
	public static int countSubstrings(String s) {
        int count=0;
        int n = s.length();
        boolean[][] palindrome = new boolean[n][n];
        for (int i=0; i<n; i++) {
            palindrome[i][i] = true;
            count++;
            if (i < n-1 && s.charAt(i) == s.charAt(i+1)) {
                palindrome[i][i+1] = true;
                count++;
            }
        }

        for (int l=2; l<n; l++) {
            for (int i=0; i+l<n; i++) {
                int j = i+l;
                if (s.charAt(i) == s.charAt(j) && palindrome[i+1][j-1]) {
                    palindrome[i][j] = true;
                    count++;
                }
            }
        }
		return count;
    }

	public static boolean palindrome (String s) {
		int n = s.length();
		for (int i=0; i<n; i++) {
			if (s.charAt(i) != s.charAt(n-i-1)) {
				return false;
			}
		}
		return true;
    }
}

/*

		works as well:

 		 int n = s.length();
         int[][] dp = new int[n][n];
         for (int i=0; i<n; i++) dp[i][i] = 1;
         for (int add = 1; add<n; add++) {
         	for (int i=0; i+add<n; i++) {
         		int j = i+add;
         		dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
         		if (palindrome(s.substring(i, j+1))) dp[i][j]++;
         	}
         }
         return dp[0][n-1];

		Ex. zhzhz
		
		dp:
		
		dp[i][j] means the number of palindromes in interval [i,j] inclusive
			this is the same as the number of palindromes from interval 
				[i+1, j] + [i,j-1] - [i+1, j-1]
				as well as add in additional 1 if [i,j] is a palindrome itself
		
		   0 1 2 3 4
		
		0  1 2 4 6 9
		  
		1    1 2 4 6
		
		2      1 2 4
		
		3        1 2
		
		4          1	
*/