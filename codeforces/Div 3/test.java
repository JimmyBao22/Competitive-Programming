
import java.util.*;
import java.io.*;

public class test {

	// https://codeforces.com/contest/1492/problem/E
	
	// WA
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("five"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());

			int[] s = new int[m];
			int[][] arr = new int[n][m];
			for (int i=0; i<n; i++) {
				st = new StringTokenizer(in.readLine());
				for (int j=0; j<m; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
					if (i == 0) s[j] = arr[0][j];
				}
			}
			
			boolean works=false;
			for (int k=0; k<150; k++) {
				System.out.println(Arrays.toString(s));
				HashMap<Integer, Integer>[] maps = new HashMap[m];
				TreeMap<Integer, HashSet<Integer>>[] reverse = new TreeMap[m];
				for (int i=0; i<m; i++) {
					maps[i] = new HashMap<>();
					reverse[i] = new TreeMap<>();
				}
				int maxindex=0;
				int maxdiff=0;
				for (int i=0; i<n; i++) {
					int diff=0;
					for (int j=0; j<m; j++) {
						if (arr[i][j] != s[j]) {
							diff++;
							if (maps[j].containsKey(arr[i][j])) {
								reverse[j].get(maps[j].get(arr[i][j])).remove(arr[i][j]);		
								if (reverse[j].get(maps[j].get(arr[i][j])).size() == 0) reverse[j].remove(maps[j].get(arr[i][j]));
							}
							maps[j].put(arr[i][j], maps[j].getOrDefault(arr[i][j], 0)+1);
							if (!reverse[j].containsKey(maps[j].get(arr[i][j]))) {
								reverse[j].put(maps[j].get(arr[i][j]), new HashSet<>());
							}
							reverse[j].get(maps[j].get(arr[i][j])).add(arr[i][j]);
						}
					}
					if (diff > maxdiff) {
						maxdiff = diff;
						maxindex = i;
					}
				}
				
				if (maxdiff <= 2) {
					works=true;
					break;
				}
				
				ArrayList<int[]> two = new ArrayList<>(); //<Integer, int[]> map2 = new TreeMap<>();
				for (int j=0; j<m && maxdiff > 0; j++) {
					if (arr[maxindex][j] != s[j]) {
						if (arr[maxindex][j] == reverse[j].lastKey()) {
							s[j] = arr[maxindex][j];
							maxdiff--;
						}
						else {
							two.add(new int[] {j, arr[maxindex][j], maps[j].get(arr[maxindex][j])});
						}
					}
				}
				
				if (maxdiff <= 2) continue;
				
				Collections.sort(two, new Comparator<int[]>() {
					public int compare(int[] a, int[] b) {
						return a[2] - b[2];
					}
				});
				while (maxdiff > 2) {
					int[] cur = two.get(two.size()-1);
					two.remove(two.size()-1);
					s[cur[0]] = cur[1];
					maxdiff--;
				}				
			}
			
			if (!works) {
				System.out.println("No");
				continue;
			}
			StringBuilder sb =  new StringBuilder();
			sb.append("Yes");
			sb.append("\n");
			for (int i=0; i<m; i++) {
				sb.append(s[i] + " ");
			}
			System.out.println(sb);
		}
	}
}