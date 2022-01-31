
import java.util.*;
import java.io.*;

public class crossings {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=242
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("crossings.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crossings.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arrx = new A[n];
		A[] arry = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arrx[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i);
			arry[i] = arrx[i];
		}
		
		Arrays.parallelSort(arrx, new Comparator<A> () {
			public int compare(A a, A b) {
				return a.x - b.x;
			}
		});
		Arrays.parallelSort(arry, new Comparator<A> () {
			public int compare(A a, A b) {
				return a.y - b.y;
			}
		});
		
		int count=0;
		int top=0;
		boolean foundothers=false;
		int lastindex=0;
		HashSet<Integer> bad = new HashSet<>();
		for (int i=0; i<n; i++) {
			while (top<n && bad.contains(arry[top].index)) top++;
			if (top>=n) break;
			if (arrx[i].index == arry[top].index) {
					// found point
				if (!foundothers) count++;
				else i = lastindex-1;
					// backtrack to last point that the top did not visit
				top++;
				foundothers=false;
			}
			else if ((arrx[i].x <= arry[top].x && arrx[i].y >= arry[top].y) ||
						(arrx[i].x >= arry[top].x && arrx[i].y <= arry[top].y)) {
					// did not find good point.
				if (!foundothers) {
					foundothers=true;
					lastindex = i;	
				}
				bad.add(arrx[i].index);
			}
		}

		System.out.println(count);
		out.println(count);
		out.close();
	}
	
	public static int brute(int n, A[] arrx) {
		int count=0;
		for (int i=0; i<n; i++) {
			boolean works=true;
			for (int j=0; j<n; j++) {
				if (i==j) continue;
				if ((arrx[i].x <= arrx[j].x && arrx[i].y >= arrx[j].y) ||
						(arrx[i].x >= arrx[j].x && arrx[i].y <= arrx[j].y)) {
					works=false;
					break;
				}
			}
			if (works) count++;
		}
		return count;
	}
	
	static class A {
		int x; int y; int index;
		A (int a, int b, int c) {
			x=a; y=b; index=c;
		}
		void print() {
			System.out.println(x+ " " + y + " " + index);
		}
	}
}