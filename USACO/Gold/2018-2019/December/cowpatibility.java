
import java.util.*;
import java.io.*;

public class cowpatibility {

	// http://usaco.org/index.php?page=viewproblem2&cpid=862
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowpatibility.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowpatibility.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][5];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j=0; j<5; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());				
			}
			Arrays.parallelSort(arr[i]);
		}

		HashMap<A, Long> map = new HashMap<>();
		for (int i=0; i<n; i++) {
			// each combo represented by binary string 1 to 31
			for (int b = 1; b<=31; b++) {
				char[] c = Integer.toBinaryString(b).toCharArray();
				int s = Integer.bitCount(b);
				A cur = new A(s);
				int pointer=0;
				for (int j=0; j<c.length; j++) {
					if (c[c.length-1-j] == '1') {
						cur.arr[pointer] = arr[i][j];
						pointer++;
					}
				}
				map.put(cur, map.getOrDefault(cur, 0l)+1);
			}
		}
		
		long ans=0;
		for (A c : map.keySet()) {
			if (c.num%2 == 1) {
				ans += map.get(c) * (map.get(c)-1)/2;
			}
			else {
				ans -= map.get(c) * (map.get(c)-1)/2;
			}
		}
		
		ans = (long)(n)*((long)(n)-1)/2 - ans;

		System.out.println(ans);
		out.println(ans);
        out.close();

	}
	
	static class A {
		int num;
		int[] arr;
		A (int a) {
			num = a;
			arr = new int[num];
		}
		
		@Override
		public boolean equals (Object obj) {
			if (this == obj) return true;
			if(obj == null || obj.getClass()!= this.getClass()) return false; 
			A o = (A)(obj);
			if (o.num == num) {
				for (int i=0; i<num; i++) {
					if (arr[i] != o.arr[i]) return false;
				}
				return true;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			int ret=0;
			for (int i=0; i<num; i++) {
				ret += ret*100+arr[i]*10;
			}
			return ret;
		}
	}
}