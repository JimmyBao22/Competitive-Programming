import java.util.*;
import java.io.*;

public class lineup {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=965

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File("lineup.in"));
		int n = in.nextInt();
		String[][] order = new String[n][2];
		for (int i = 0; i < n; i++) {
			order[i][0] = in.next();
			in.next();
			in.next();
			in.next();
			in.next();
			order[i][1] = in.next();
			if (order[i][0].compareTo(order[i][1])>0) 
		    {
		        String temp = order[i][0];
		        order[i][0] = order[i][1];
		        order[i][1] = temp;
		    }
		}
		in.close();

		String[] cows = {"Beatrice", "Belinda", "Bella", "Bessie", "Betsy", "Blue", "Buttercup", "Sue"};
		
		String[] g = new String[8];
		
		outerloop:
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == i) continue;
				for (int a = 0; a < 8; a++) {
					if (a == i || a == j) continue;
					for (int b = 0; b < 8; b++) {
						if (b == i || b == j || b == a) continue;
						for (int c = 0; c < 8; c++) {
							if (c == i || c == j || c == a || c == b) continue;
							for (int d = 0; d < 8; d++) {
								if (d == i || d == j || d == a || d == b || d==c) continue;
								for (int e = 0; e < 8; e++) {
									if (e == i || e == j || e == a || e == b || e==c || e==d) continue;
									for (int f = 0; f < 8; f++) {
										if (f == i || f == j || f == a || f == b || f==c || f==d || f==e) continue;
										g[0] = cows[i];
										g[1] = cows[j];
										g[2] = cows[a];
										g[3] = cows[b];
										g[4] = cows[c];
										g[5] = cows[d];
										g[6] = cows[e];
										g[7] = cows[f];
										if (check(g, order)) {
											break outerloop;
										}
									}
								}
							}
						}
					}
				}
			}
		}

		PrintWriter out = new PrintWriter(new File("lineup.out"));
		for (int i = 0; i < g.length; i++) {
			System.out.println(g[i]);
			out.println(g[i]);
		}
		out.close();
	}
	
	public static boolean check(String[] g, String[][] order) {
		boolean[] k = new boolean[order.length];
		for (int i = 0; i < order.length; i++) {
			for (int a = 0; a < g.length-1; a++) {
				if (g[a].equals(order[i][0]) && g[a+1].equals(order[i][1])) {
					k[i] = true;
				}
				if (g[a].equals(order[i][1]) && g[a+1].equals(order[i][0])) {
					k[i] = true;
				} 
			}
		}
		for (int i = 0; i < k.length; i++) {
			if (!k[i]) return false;
		}
		return true;
	}
}