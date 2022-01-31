
import java.util.*;
import java.io.*;

public class Expogo {

	// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fef2/00000000002d5b62
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Expogo"));

		int t = Integer.parseInt(in.readLine());
		o: for (int tt = 1; tt < t + 1; tt++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int xneed = Integer.parseInt(st.nextToken());
			int yneed = Integer.parseInt(st.nextToken());
			
			if (m(xneed) == m(yneed)) {
				System.out.println("Case #" + tt + ": IMPOSSIBLE");
				continue o;
			}
			
			// at each step always jump to the one with a bit at this position
			StringBuilder s = new StringBuilder();
			for (int i=0; i<31; i++ ) {
				if (xneed == 0 && yneed == 0) break;
				if (xneed == 0) {
					if (yneed == 1) {
						yneed--;
						s.append('N');
					}
					else if (yneed == -1) {
						yneed++;
						s.append('S');
					}
					else if (m((yneed+1)/2) == 1) {
						yneed++;
						s.append('S');
					}
					else if (m((yneed-1)/2) == 1) {
						yneed--;
						s.append('N');
					}
					else {
						System.out.println("Case #" + tt + ": IMPOSSIBLE");
						continue o;
					}
				}
				else if (yneed == 0) {
					if (xneed == 1) {
						xneed--;
						s.append('E');
					}
					else if (xneed == -1) {
						xneed++;
						s.append('W');
					}
					else if (m((xneed+1)/2) == 1) {
						xneed++;
						s.append('W');
					}
					else if (m((xneed-1)/2) == 1) {
						xneed--;
						s.append('E');
					}
					else {
						System.out.println("Case #" + tt + ": IMPOSSIBLE");
						continue o;
					}
				}
				else if (m(xneed) == 1) {
					if (m((xneed+1)/2) != m(yneed/2)) {
						s.append('W');
						xneed++;
					}
					else if (m((xneed-1)/2) != m(yneed/2)) {
						s.append('E');
						xneed--;
					}
					else {
						System.out.println("Case #" + tt + ": IMPOSSIBLE");
						continue o;
					}
				}
				else {
					if (m((xneed)/2) != m((yneed+1)/2)) {
						s.append('S');
						yneed++;
					}
					else if (m((xneed)/2) != m((yneed-1)/2)) {
						s.append('N');
						yneed--;
					}
					else {
						System.out.println("Case #" + tt + ": IMPOSSIBLE");
						continue o;
					}
				}
				xneed /= 2;
				yneed /= 2;
			}
			
			System.out.println("Case #" + tt + ": " + s);
		}
	}
	
	public static int m(int a) {
		return (a%2+2)%2;
	}
}