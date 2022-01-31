
import java.util.*;
import java.io.*;

public class where {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=740

	static int n; // grid size
	static char[][] colors;
	
	// confirmed PCL's 
	static List<PCL> results = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("where.in"));

		n = Integer.parseInt(in.readLine());  		
		colors = new char[n][];
		
		for (int r = 0; r < n; r++) {
			colors[r] = in.readLine().toCharArray();
		}
		in.close();

		// try PCL's "largest to smallest"
			// 20x20, 20x19, ... 20x1
			// 19x20, 19x19, ... 19x1
				// Note: 19x20 is larger than 20x1 BUT
					// 19x20 cannot contain 20x1
			// ...
			// 1x20, 1x19, ... 1x1
		
		for (int w = n; w >= 1; w--) {
			for (int h = n; h >= 1; h--) {
				// try each possible PCL of given size
				for (int sr = 0; sr <= n-h; sr++) {
					// start row
					for (int sc = 0; sc <= n-w; sc++) {
						// start column
						
						//represent, check, and maybe add this PCL
						PCL pol = new PCL(sr, sc, w, h);
						//pol.printPCL();
						//System.out.println();
						
						if (pol.verifyColors() && !pol.inOtherPCL()) {
							// if this PCL has 2 colors exactly, works, and is not in other PCLs, then add
							results.add(pol);
						}
					}
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("where.out"));
		System.out.println(results.size());
		out.println(results.size());
		out.close();
	}
	
	// class to represent rectangle area, which may be a PCL
	// Note: these are possible PCLs
	static class PCL {
		int rTop, cLeft, rBot, cRight;
		int w, h;
		
		PCL(int startrow, int startcolumn, int w, int h) {
			rTop = startrow;
			cLeft = startcolumn;
			this.w = w;
			this.h = h;
			rBot = rTop + h - 1;
			cRight = cLeft + w - 1;
		}
		
		// checks if this rectangle matches the color rules of a PCL
		// multiple floodfills until all cells have been identified as part
			// of a color region that has been filled
		boolean verifyColors() {
			
			// single visited array, never reset until all regions 
				// in this PCL are filled
			boolean[][] v = new boolean[n][n];
			
			// 1 way to do it vvv
			// keep track of all colors seen, and which color
				// occurred multiple times
				// note: keeping track of exact amount is irrelevant
					// only need to know how many times each region of that color occurred
			Set<Character> regColors = new HashSet<>();
			char multi = 0;
			
				// another way vvv
				//Map<Character, Integer> multiple = new HashMap<>();
					// key: the character
					// value: how many times that characer appeared
				// loop through, then check at the end after storing everything
			
			// look at every cell in region
			for (int r = rTop; r <= rBot; r++) {
				for (int c = cLeft; c <= cRight; c++) {
					// any unchecked cell is part of a new uncounted region
					if (!v[r][c]) {
						char color = colors[r][c];
						
						// do color-counting
						if (regColors.contains(color)) {
							// this color already appeared before. is appearing again
							
							if (multi == 0 || multi == color) {
								multi = color;
							}
							else {
								// a diff color already appeared multiple times AND this one appearing multiple times
								return false;
							}
						}
						else {
							// this color appear for first time
							regColors.add(color);
							
							// more than 2 colors = bad
							if (regColors.size() > 2) return false;
						}
						
						// mark off the rest of this colored regoin so we don't count
							// this region color more than we are supposed to
						flood(v, r, c, color);
					}
				}
			}
			
			// there MUST be a multiple region color
			return multi != 0;
		}
		
		// marks all cells matching a color in the same color-region as starting cell
		void flood(boolean[][] v, int r, int c, char match) {
		
			/*if (r < rTop || r > rBot || c < cLeft || c > cRight) {
				return;
			}
			if (v[r][c]) return;
			if (colors[r][c] != match) return;
			
			v[r][c] = true;
			
			flood(v, r+1, c, match);
			flood(v, r-1, c, match);
			flood(v, r, c+1, match);
			flood(v, r, c-1, match);
			*/

			
			Deque<int[]> a = new ArrayDeque<>();
			a.push(new int[] {r, c});
			
			while (a.size() > 0) {
				int[] k = a.pop();
				int rr = k[0];
				int cc = k[1];
				
				if (rr < rTop || rr > rBot || cc < cLeft || cc > cRight) {
					continue;
				}
				if (v[rr][cc]) continue;
				if (colors[rr][cc] != match) continue;
				
				v[rr][cc] = true;
				
				a.push(new int[] {rr+1, cc});
				a.push(new int[] {rr-1, cc});
				a.push(new int[] {rr, cc+1});
				a.push(new int[] {rr, cc-1});
			}
		}
		
		// check all PCLs already in results list to see if any 
			// contains this one
		boolean inOtherPCL() {
			for (PCL other : results) {
				if (this.cLeft >= other.cLeft &&
					this.cRight <= other.cRight &&
					this.rTop >= other.rTop &&
					this.rBot <= other.rBot) return true;
			}
			return false;
		}
		
		void printPCL() {
			for (int i = rTop; i <= rBot; i++) {
				for (int j = cLeft; j <= cRight; j++) {
					System.out.print(colors[i][j]);
				}
				System.out.println();
			}
		}
	}
}