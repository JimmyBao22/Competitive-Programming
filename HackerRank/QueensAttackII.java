
import java.util.*;
import java.io.*;

public class QueensAttackII {

	// https://www.hackerrank.com/challenges/queens-attack-2/problem
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("QueensAttackII"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] queen = new int[2];
		st = new StringTokenizer(in.readLine());
		queen[0] = Integer.parseInt(st.nextToken());
		queen[1] = Integer.parseInt(st.nextToken());
		int[][] obstacles = new int[k][2];
		HashMap<Integer, HashSet<Integer>> ob = new HashMap<>();
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			obstacles[i][0] = Integer.parseInt(st.nextToken());
			obstacles[i][1] = Integer.parseInt(st.nextToken());
			if (ob.containsKey(obstacles[i][0])) {
				ob.get(obstacles[i][0]).add(obstacles[i][1]);
			}
			else {
				HashSet<Integer> c = new HashSet<>();
				c.add(obstacles[i][1]);
				ob.put(obstacles[i][0], c);
			}
		}
		
		int num=0;
		// going left
		for (int i=queen[1]-1; i>=1; i--) {
			if (ob.containsKey(queen[0])) {
				if (ob.get(queen[0]).contains(i)) {
					break;
				}
			}
			num++;
		}
		
		// going right
		for (int i=queen[1]+1; i<=n; i++) {
			if (ob.containsKey(queen[0])) {
				if (ob.get(queen[0]).contains(i)) {
					break;
				}
			}
			num++;
		}
		
		// going down
		for (int i=queen[0]-1; i>=1; i--) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(queen[1])) {
					break;
				}
			}
			num++;
		}
		
		// going up
		for (int i=queen[0]+1; i<=n; i++) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(queen[1])) {
					break;
				}
			}
			num++;
		}
		
		// diagonal left
		for (int i=queen[0]+1, j=queen[1]-1; i<=n && j>=1; i++, j--) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(j)) {
					break;
				}
			}
			num++;
		}
		
		// diagonal right
		for (int i=queen[0]+1, j=queen[1]+1; i<=n && j<=n; i++, ++j) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(j)) {
					break;
				}
			}
			num++;
		}
		
		// diagonal down left
		for (int i=queen[0]-1, j=queen[1]-1; i>=1 && j>=1; i--, j--) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(j)) {
					break;
				}
			}
			num++;
		}
		
		// diagonal down right
		for (int i=queen[0]-1, j=queen[1]+1; i>=1 && j<=n; i--, ++j) {
			if (ob.containsKey(i)) {
				if (ob.get(i).contains(j)) {
					break;
				}
			}
			num++;
		}
		
		System.out.println(num);
		
	}
}