public class UniquePaths {

	// https://leetcode.com/problems/unique-paths/
	
	public int uniquePaths(int m, int n) {
		int[][] arr = new int[m][n];
        for (int i=0; i<n; i++) arr[0][i] = 1;
        for (int j=0; j<m; j++) arr[j][0] = 1;
        for (int i=1; i<n; i++) {
            for (int j=1; j<m; j++) {
                arr[j][i] = arr[j-1][i] + arr[j][i-1];
            }
        }
        return arr[m-1][n-1];
        // m--;
        // n--;
        // int t = m+n;
        // long ans = 1;
        // if (m > n) {
        //     // t!/m!n! = (m+n)(m+n-1)...(m+1)/n!
        //     for (int i=m+n; i>= m+1; i--) {
        //     	ans*=i;
        //     }
        //     for (int i=n; i>= 2; i--) {
        //     	ans /= i;
        //     }
        // }
        // else {
        // 	// t!/m!n! = (m+n)(m+n-1)...(n+1)/m!
        //     for (int i=m+n; i>= n+1; i--) {
        //     	ans*=i;
        //     }
        //     for (int i=m; i>= 2; i--) {
        //     	ans /= i;
        //     }
        // }
        // return (int)ans;
    }
	
	public static void main(String[] args) {

	}

}
