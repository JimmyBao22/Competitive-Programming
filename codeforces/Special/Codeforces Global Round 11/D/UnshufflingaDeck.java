
import java.util.*;
import java.io.*;

public class UnshufflingaDeck {

	// https://codeforces.com/contest/1427/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("UnshufflingaDeck"));
	
		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n+1];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=1; i<=n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
		boolean front = false;
		for (int i=n; i>=1; i--) {
			if (n%2 != 0 && i==1) break;
			ArrayList<Integer> cur = new ArrayList<Integer>();
			int[] temp = new int[n+1];
			if (front) {
				for (int j=n; j>i; j--) {
					cur.add(1);
					temp[j] = j;
				}
				
				int j= (n-i+1);
				for (; j<n; j++) {
					if (arr[j] == i) break;
				}
				
				if (j - (n-i) > 0) cur.add(j - (n-i));
				if (n - j > 0) cur.add(n - j);
	
				int p=1;
				for (int k=j+1; k<= n; k++) {
					temp[p] = arr[k];
					++p;
				}

				for (int k=n-i+1; k<=j; k++) {
					temp[p] = arr[k];
					p++;
				}
			}
			else {
				
				int j=1;
				for (; j<=n; j++ ) {
					if (arr[j] == i) break;
				}
				
				if (j-1 > 0) cur.add(j-1);
				for (int k=j-1; k>=0; k--) {
					temp[n+(k-j + 1)] = arr[k];
				}
				
				int prev = j;
				for (; j<=n; j++) {
					if (arr[j] > i) break;
				}
				j--;
				
				if (j - prev + 1 > 0) cur.add(j - prev+1);
				
				int p=1;
				for (int k=n; k>j; k--) {
					cur.add(1);
					temp[p] = arr[k];
					p++;
				}
				
				for (int k=prev; k<=j; k++) {
					temp[p] = arr[k];
					p++;
				}
			}
			
			arr = temp;
			if (cur.size() != 1) moves.add(cur);
			front = !front;
		}
		
		System.out.println(moves.size());
		for (int i=0; i<moves.size(); i++) {
			System.out.print(moves.get(i).size() + " ");
			for (int j=0; j<moves.get(i).size(); j++) {
				System.out.print(moves.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
}