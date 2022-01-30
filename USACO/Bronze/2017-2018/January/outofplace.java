
import java.util.*;
import java.io.*;

public class outofplace {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=785

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("outofplace.in"));
		
		int n = in.nextInt();
		int[] line = new int[n];
		
		for (int i = 0; i < n; i++) {
			line[i] = in.nextInt();
		}
		in.close();

		int b = 0;
		int count = 0;
		int[] check = new int[n-1];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				// jth term is skipped
				if (i == j) continue;
				if (i > j) {
					check[i-1] = line[i];
				}
				else {
					check[i] = line[i];
				}
				// this creates a new boolean called check, with one less element every time
			}
			if (sorted(check)) { 
				b = j; // this term must be the one wrong
				break;
			}
		}
		
		if (line[b] < line[b-1]) {
			count = smaller(line, b);
		}
		else if (line[b] > line[b+1]) {
			count = larger(line, b);
		}
		
		PrintWriter out = new PrintWriter(new File("outofplace.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}
	
	public static boolean sorted(int[] array) {
		for (int i = 0; i < array.length-1; i++) {
			if (array[i] > array[i+1]) {
				return false;
			}
		}
		return true;
	}
	
	public static int smaller(int[] line, int b) {
		int count = 0;
		while (!sorted(line)) {
			count++;
			
			int before = line[b-1];
			// this is the person before
			
			int m = b-1;
			for (int i = m; i >0; i--) {
				if (before != line[i-1]) {
					//m = b-(before-i);
					m = i;
					break;
				}
				// look before. are there any that are the same? 
				// for example 77 would swap with first 7 instead 
				// of second 7
			}
			
			int temp = line[b];
			line[b] = line[m];
			line[m] = temp;
			b = m;
			//swaps em
		}
		return count;
	}
	
	public static int larger(int[] line, int b) {
		int count = 0;
		while (!sorted(line)) {
			count++;
			
			int after = line[b+1];
			// this is the person before
			
			int m = b+1;
			for (int i = m; i < line.length-1; i++) {
				if (after != line[i+1]) {
					//m = b-(before-i);
					m = i;
					break;
				}
				// look before. are there any that are the same? 
				// for example 77 would swap with first 7 instead 
				// of second 7
			}
			
			int temp = line[b];
			line[b] = line[m];
			line[m] = temp;
			b = m;
			//swaps em
		}
		return count;
	}
}