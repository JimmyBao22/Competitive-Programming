
import java.util.*;
import java.io.*;

public class invite {

	// http://usaco.org/index.php?page=viewproblem2&cpid=228
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("invite.in"));
		PrintWriter out = new PrintWriter(new FileWriter("invite.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int g = Integer.parseInt(st.nextToken());
		ArrayList<HashSet<Integer>> list = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		HashSet<Integer> use = new HashSet<>();
			// keeps track of lists that have not been used yet
		
		for (int i=0; i<g; i++) {
			st = new StringTokenizer(in.readLine());
			int c = Integer.parseInt(st.nextToken());
			HashSet<Integer> cur = new HashSet<>();
			for (int j=0; j<c; j++) {
				int cc = Integer.parseInt(st.nextToken());
				if (cc != 1) cur.add(cc);
			}
			list.add(cur);
			indices.add(i);
			use.add(i);
		}
		
		Collections.sort(indices, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return  list.get(b).size() - list.get(a).size();
					// smaller last
			}
		});
		
		int cows=1;
		
		while (indices.size()>0) {
			if (list.get(indices.get(indices.size()-1)).size() >= 2) break;
				// last element size >= 2
			HashSet<Integer> rem = new HashSet<>();
			
				// go over all lists that have size 1, and add those cows to rem
			for (int i=indices.size()-1; i>=0; i--) {
				if (indices.size()==0) break;
				if (list.get(indices.get(indices.size()-1)).size() >= 2) break;
				else {
					for (Integer a : list.get(indices.get(indices.size()-1))) {
						if (!rem.contains(a)) {
							rem.add(a);
							cows++;
						}
					}					
					use.remove(indices.get(indices.size()-1));
					indices.remove(indices.size()-1);
				}
			}
			
			for (Integer a : use) {
				for (Integer b : rem) {
					list.get(a).remove(b);
				}
			}
			
			Collections.sort(indices, new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					return  list.get(b).size() - list.get(a).size();
						// smaller last
				}
			});
		}

		System.out.println(cows);
		out.println(cows);
		out.close();
	}
}