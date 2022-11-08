
import java.util.*;
import java.io.*;

public class LCS {

	// https://atcoder.jp/contests/dp/tasks/dp_f
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LCS"));
		
		String a = in.readLine();
		String b = in.readLine();
		int n = a.length();
        int m = b.length();
        A[][] dp = new A[n][m];
        int max=0;
        if (a.charAt(0) == b.charAt(0)) dp[0][0] = new A(1, 0, 0);
        else dp[0][0] = new A(0,0,0);
        
        max = Math.max(max, dp[0][0].val);
        for (int i=1; i<n; i++) {
            if (a.charAt(i) == b.charAt(0)) dp[i][0] = new A(1, i, 0);
            else dp[i][0] = new A(dp[i-1][0].val, i-1, 0);
            max = Math.max(max, dp[i][0].val);
        }
        for (int i=1; i<m; i++) {
            if (a.charAt(0) == b.charAt(i)) dp[0][i] = new A(1, 0, i);
            else dp[0][i] = new A(dp[0][i-1].val, 0, i-1);
            max = Math.max(max, dp[0][i].val);
        }
        
        for (int i=1; i<n; i++) {
        	for (int j=1; j<m; j++) {
        		if (a.charAt(i) == b.charAt(j)) {
        		    dp[i][j] = new A(dp[i-1][j-1].val+1, i-1, j-1);
        		}
                if (dp[i][j] == null || dp[i-1][j].val > dp[i][j].val) {
                	dp[i][j] = new A(dp[i-1][j].val, i-1, j);
                }
                if (dp[i][j-1].val > dp[i][j].val) {
                	dp[i][j] = new A(dp[i][j-1].val, i, j-1);
                }
                max = Math.max(max, dp[i][j].val);
        	}
        }
        
        if (max == 0) {
        	System.out.println("");
        	return;
        }
        
       A cur = new A(0,0,0);
       o: for (int i=0; i<n; i++) {
    	   for (int j=0; j<m; j++) {
    		   if (dp[i][j].val == max) {
    			   cur = new A(dp[i][j].val, dp[i][j].x, dp[i][j].y, i, j);
    			   break o;
    		   }
    	   }
       }

       ArrayList<Character> f = new ArrayList<>();
       while (true) {
    	   
    	   if (cur.x+1 == cur.curx && cur.y + 1 == cur.cury) {
    		   // came from a diagonal
    		   f.add(a.charAt(cur.curx));
    	   }
    	   
    	   if (dp[cur.curx][cur.cury].x == cur.curx && dp[cur.curx][cur.cury].y == cur.cury) {
    		   break;
    	   }
    	   cur = new A(dp[cur.x][cur.y].val, dp[cur.x][cur.y].x, dp[cur.x][cur.y].y, cur.x, cur.y);
       }
       
       if (cur.val == 1) {
    	   f.add(a.charAt(cur.curx));
       }
       StringBuilder s = new StringBuilder();
       for (int i=f.size()-1; i>=0; i--) {
    	   s.append(f.get(i));
       }
       System.out.println(s);
	}
	
	static class A {
		int val;
		int x; int y;		// where you are coming from
		int curx; int cury;
		A (int a, int b, int c) {
			val = a; x = b; y = c;
		}
		A (int a, int b, int c, int d, int e) {
			val = a; x = b; y = c; curx = d; cury = e;
		}
		void print() {
			System.out.println(val + " " + x + " " + y);
		}
	}
}