
import java.util.*;
import java.io.*;

public class carnival {

	// https://oj.uz/problem/view/CEOI14_carnival
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(in.readLine());

		ArrayList<Integer> parents = new ArrayList<>();
		int[] par = new int[n+1];
		parents.add(1);
		par[1] = 1;
		int counter=2;
		for (int i=2; i<=n; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(parents.size()+1 + " ");
			for (int j=0; j<parents.size(); j++) {
				sb.append(parents.get(j) + " ");
			}
			sb.append(i + " ");
			System.out.println(sb);
			System.out.flush();
			int curvalue = Integer.parseInt(in.readLine());
			if (curvalue == parents.size()+1) {
				parents.add(i);
				par[i] = counter;
				counter++;
			}
			else {
				// binary search where it needs to go
				int min=0;
				int max = parents.size()-1;
				while (min < max) {
					int middle = (min + max)/2;
					sb = new StringBuilder();
					sb.append(middle - min + 2 + " ");
					// search first half
					for (int j=min; j<=middle; j++) {
						sb.append(parents.get(j) +  " ");
					}
					sb.append(i + " ");
					System.out.println(sb);
					System.out.flush();
					int curvalue2 = Integer.parseInt(in.readLine());
					if (curvalue2 == middle-min+1) {
						// inside lower half
						max = middle;
					}
					else min = middle+1;
				}
				par[i] = par[parents.get(min)];
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(0 + " ");
		for (int i=1; i<=n; i++) {
			sb.append(par[i] + " ");
		}
		System.out.println(sb);
		System.exit(0);
	}
}