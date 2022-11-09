
import java.util.*;
import java.io.*;

public class Stylishclothes {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Stylishclothes"));

		int c = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] caps = new int[c];
		for (int i=0; i<c; i++) caps[i] = Integer.parseInt(st.nextToken());
		Arrays.parallelSort(caps);
		
		int s = Integer.parseInt(in.readLine());
		st = new StringTokenizer(in.readLine());
		int[] shirts = new int[s];
		for (int i=0; i<s; i++) shirts[i] = Integer.parseInt(st.nextToken());
		Arrays.parallelSort(shirts);
		
		int p = Integer.parseInt(in.readLine());
		st = new StringTokenizer(in.readLine());
		int[] pants = new int[p];
		for (int i=0; i<p; i++) pants[i] = Integer.parseInt(st.nextToken());
		Arrays.parallelSort(pants);
		
		int n = Integer.parseInt(in.readLine());
		st = new StringTokenizer(in.readLine());
		int[] shoes = new int[n];
		for (int i=0; i<n; i++) shoes[i] = Integer.parseInt(st.nextToken());
		Arrays.parallelSort(shoes);
		
		int p1=0, p2=0, p3=0, p4=0;
		int maxval = (int)(1e5);
		int ans=4*maxval;
		int ans1 = 0, ans2 = 0, ans3 = 0, ans4 = 0;
		for (int i=0; i<maxval; i++) {			
			// set i to be the minimum
				// in reality only need to loop through all values that are included in
				// caps, shirts, pants, and shoes,
				// but this way works too and less code
			
			while ((p1+1<c && Math.abs(caps[p1+1] - i) < Math.abs(caps[p1] - i)) || (p1 < c && caps[p1] < i)) {
				p1++;
			}
			while ((p2+1<s && Math.abs(shirts[p2+1] - i) < Math.abs(shirts[p2] - i)) || (p2 < s && shirts[p2] < i)) {
				p2++;
			}
			while ((p3+1<p && Math.abs(pants[p3+1] - i) < Math.abs(pants[p3] - i)) || (p3 < p && pants[p3] < i)) {
				p3++;
			}
			while ((p4+1<n && Math.abs(shoes[p4+1] - i) < Math.abs(shoes[p4] - i)) || (p4 < n && shoes[p4] < i)) {
				p4++;
			}
			
			if (p1 >= c || p2 >= s || p3 >= p || p4 >= n) break;
			
			int curdiff = Math.max(caps[p1], Math.max(shirts[p2], Math.max(pants[p3], shoes[p4])))
					- Math.min(caps[p1], Math.min(shirts[p2], Math.min(pants[p3], shoes[p4])));
			if (curdiff < ans) {
				ans = curdiff;
				ans1 = caps[p1]; ans2 = shirts[p2]; ans3 = pants[p3]; ans4 = shoes[p4];
			}
		}
		
		System.out.println(ans1 + " " + ans2 + " " + ans3 + " " + ans4);
	}
}
