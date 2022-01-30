import java.util.*;
import java.io.*;

public class sleepy {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=892

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("sleepy.in"));
		
		int n = in.nextInt();
		int[] c = new int[n];
		
		for (int i = 0; i < n; i++) {
			c[i] = in.nextInt();
		}
		in.close();
		
		int count = 0;
		
		while (!ordered(c)) {
			int s = 0;
			for (int i = n-1; i > 0; i--) {
				//this will find the index of when it stops going down
				// ex. 1423 it will go 3 then 2 then 4 and realize
				// 4 > 2 so it will return index 2
				
				if (c[i-1] > c[i]) {
					s = i;
					break;
				}
			}
			
			int k = 0;
			for (int i = s; i < n; i++) {
				if (c[0] > c[i]) {
					k = i;
				}
			}
			if (k == 0) k = s-1;
			if (c[0] == n) k = n-1;
			c = shift(c, k);
			count++;
		}
		
		PrintWriter out = new PrintWriter(new File("sleepy.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}
	
	public static boolean ordered(int[] array) {
		boolean x = true;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != i+1) {
				x = false;
				break;
			}
		}
		return x;
	}
	
	public static int[] shift(int[] array, int a) {
		//shift first letter to position a.
		int[] array2 = new int[array.length];
		for (int i = 0; i < a; i++) {
			array2[i] = array[i+1];
		}
		
		array2[a] = array[0];
		
		for (int i = a+1; i < array.length; i++) {
			array2[i] = array[i];
		}
		return array2;
	}
}