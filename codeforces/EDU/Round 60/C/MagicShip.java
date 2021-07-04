
import java.util.*;
import java.io.*;

public class MagicShip {

	// https://codeforces.com/problemset/problem/1117/C
	
	static long x1, y1, x2, y2;
	static int n;
	static String s;
	static HashMap<Integer, long[]> map;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MagicShip"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		x1 = Integer.parseInt(st.nextToken());
		y1 = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		x2 = Integer.parseInt(st.nextToken());
		y2 = Integer.parseInt(st.nextToken());

		n = Integer.parseInt(in.readLine());
		s = in.readLine();
		
		map = new HashMap<>();
		long x=0;
		long y=0;
		for (int i=0; i<n; i++) {
			if (s.charAt(i) == 'U') y++;
			else if (s.charAt(i) == 'R') x++;
			else if (s.charAt(i) == 'L') x--;
			else y--;
			map.put(i, new long[] {x,y});
		}
		
		long min=0;
		long max=(long)(1e18);
		while (min<max) {
			long middle = min + (max-min)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		if (max >= (long)(1e18)) {
			System.out.println(-1);
			return;
		}
		System.out.println(min);
	}
	
	public static boolean check(long mid) {
		long x=x1; long y=y1;
		long times = mid/n;
		long mod = mid%n;
		x += map.get(n-1)[0]*times;
		y += map.get(n-1)[1]*times;
		if (mod != 0) {
			x += map.get((int)mod-1)[0];
			y += map.get((int)mod-1)[1];
		}
		
		long diff = Math.abs(x - x2) + Math.abs(y - y2);
		return diff <= mid;
	}
}