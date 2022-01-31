import java.util.*;
import java.io.*;

public class cbarn {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=618
	// http://usaco.org/index.php?page=viewproblem2&cpid=621
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cbarn.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cbarn.out"));

		int n = Integer.parseInt(in.readLine());
		Cell[] arr = new Cell[n];
		for (int i=0; i<n; i++) {
			Cell cur = new Cell(i, Integer.parseInt(in.readLine()));
			arr[i] = cur;
		}
		
		int lastzero=-1;
		
		boolean didsomething=true;
		while (didsomething) {
			didsomething=false;
			lastzero=-1;
			for (int k=2*n-1; k>=0; k--) {
				int i = k%n;
				if (i < 0) i += n;
				if (arr[i].val == 0) {
					// found a 0
					if (lastzero == -1) lastzero = i;
				}
				else if (lastzero != -1 && arr[i].val != 0) {
					int numleft = arr[i].val;
					if (lastzero < i) lastzero += n;
					for (int j=lastzero; j>i; j--) {
						Cell cur = new Cell(j%n, 1);
						cur.movesmade = arr[i].movesmade + (j - i);
						
						//cur.print();
						
						arr[j%n] = cur;
						numleft--;
						lastzero=j%n-1;
						if (numleft <= 0) {
							break;
						}
					}
					if (numleft > 0) {
						Cell cur = new Cell(i, numleft);
						cur.movesmade = arr[i].movesmade;
						
						//cur.print();

						arr[i] = cur;
						lastzero=-1;
					}
					else {
						Cell cur = new Cell(i, 0);
						arr[i] = cur;
					}
					didsomething=true;
				}
			}
		}
		
		long result = 0;
		
		for (int i=0; i<n; i++) {
			result += (long)arr[i].movesmade * (long)arr[i].movesmade;
		}
		
		System.out.println(result);
		out.println(result);
		out.close();

	}
	
	static class Cell {
		int pos;
		int movesmade=0;
		int val;
		Cell (int pos, int val) {
			this.pos = pos;
			this.val = val;
		}
		
		void print() {
			System.out.println(pos + " " + movesmade + " " + val);
		}
	}
}

// simulate the whole thing works