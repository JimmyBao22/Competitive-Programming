
import java.util.*;
import java.io.*;

public class IncrementOfCoins {

	// https://atcoder.jp/contests/abc184/tasks/abc184_d
	
	static int a,b,c;
	static double[][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("incrementofcoins"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		memo = new double[100][100][100];
		for (int i=0; i<100; i++) {
			for (int j=0; j<100; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		double ans = dp(a,b,c);
		
		System.out.println(ans);
	}
	
	public static double dp(int cura, int curb, int curc) {
		if (cura >= 100 || curb >= 100 || curc >= 100) {
			return (cura - a) + (curb - b) + (curc - c);		// num moves
		}
		if (memo[cura][curb][curc] != -1) return memo[cura][curb][curc];
		double ans=0;
		
		// go to next cura
		ans += (double)(cura)/(cura + curb + curc) * dp(cura+1, curb, curc);
		ans += (double)(curb)/(cura + curb + curc) * dp(cura, curb+1, curc);
		ans += (double)(curc)/(cura + curb + curc) * dp(cura, curb, curc+1);
		
		return memo[cura][curb][curc] = ans;
	}
}