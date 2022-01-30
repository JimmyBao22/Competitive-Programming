
import java.util.*;
import java.io.*;

public class WeightsDivisionEasy {

	// https://codeforces.com/contest/1399/problem/E1
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("WeightsDivisionEasy"));

		int t = Integer.parseInt(in.readLine());
		while (t -->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			long S = Long.parseLong(st.nextToken());
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			HashMap<Integer, HashMap<Integer, Long>> cost = new HashMap<>();
			HashMap<Integer, HashMap<Integer, Long>> times = new HashMap<>();
			
			for (int i=0; i<n; i++) {
				g.add(new ArrayList<>());
				cost.put(i, new HashMap<>());
				times.put(i, new HashMap<>());
			}
			
			for (int i=0; i<n-1; i++) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken())-1;
				int b = Integer.parseInt(st.nextToken())-1;
				long w = Long.parseLong(st.nextToken());
				g.get(a).add(b);
				g.get(b).add(a);
				if (a<b) {
					cost.get(a).put(b, w);
					times.get(a).put(b, 0l);
				}
				else {
					cost.get(b).put(a,w);
					times.get(b).put(a, 0l);	
				}
			}
			
			dfs(0, -1, times, g);
			
			ArrayList<Long> allcost = new ArrayList<>();
			ArrayList<int[]> allcostindex = new ArrayList<>();
			long curmon = 0;
			for (Integer a : cost.keySet()) {
				for (Integer b : cost.get(a).keySet()) {
					long curam = cost.get(a).get(b) * times.get(a).get(b);
					allcost.add(curam);
					allcostindex.add(new int[] {a,b});
					curmon += curam;
				}
			}
			
			int count=0;
			int m = allcost.size();
			
			TreeMap<Long, ArrayList<Integer>> diff = new TreeMap<>();
			
			for (int i=0; i<m; i++) {
				long cur2 = allcost.get(i);
				int[] c = allcostindex.get(i);
				
				long ogcost = cost.get(c[0]).get(c[1]);
				ogcost /= 2;
				ogcost *= (times.get(c[0]).get(c[1]));
								
				if (diff.containsKey(cur2 - ogcost) ) {
					diff.get(cur2 - ogcost).add(i);
				}
				else {
					ArrayList<Integer> cc = new ArrayList<>();
					cc.add(i);
					diff.put(cur2 - ogcost, cc);
				}
			}
			
			while (curmon > S) {
				
				if (diff.get(diff.lastKey()).size() == 0) {
					diff.remove(diff.lastKey());
					continue;
				}
				int index = diff.get(diff.lastKey()).get(diff.get(diff.lastKey()).size()-1);
				diff.get(diff.lastKey()).remove(diff.get(diff.lastKey()).size()-1);
				if (diff.get(diff.lastKey()).size() == 0) diff.remove(diff.lastKey());
				
				long cur = allcost.get(index);
				curmon -= cur;
								
				int[] c = allcostindex.get(index);
				long ogcost = cost.get(c[0]).get(c[1]);
				ogcost /= 2;
				cost.get(c[0]).put(c[1], ogcost);
				ogcost *= (times.get(c[0]).get(c[1]));
				curmon += ogcost;
				
				allcost.set(index, ogcost);
				
				long cur2 = allcost.get(index);
				ogcost = cost.get(c[0]).get(c[1]);
				ogcost /= 2;
				ogcost *= (times.get(c[0]).get(c[1]));
				
				if (diff.containsKey(cur2 - ogcost) ) {
					diff.get(cur2 - ogcost).add(index);
				}
				else {
					ArrayList<Integer> cc = new ArrayList<>();
					cc.add(index);
					diff.put(cur2 - ogcost, cc);
				}
				count++;
			}
			
			System.out.println(count);
			
		}
	}
	
	public static void dfs(int cur, int parent, HashMap<Integer, HashMap<Integer, Long>> times, ArrayList<ArrayList<Integer>> g) {
		if (g.get(cur).size() == 1 && cur!=0) {
			// leaf
			if (cur < parent) {
				times.get(cur).put(parent, times.get(cur).getOrDefault(parent, 0l)+1);
			}
			else {
				times.get(parent).put(cur, times.get(parent).getOrDefault(cur, 0l)+1);
			}
			return;
		}
		for (int i=0; i<g.get(cur).size(); i++) {
			if (g.get(cur).get(i) == parent) continue;
			dfs(g.get(cur).get(i), cur, times, g);
			if (parent != -1) {
				if (parent < cur) {
					times.get(parent).put(cur, times.get(parent).getOrDefault(cur, 0l)
							+ times.get(Math.min(cur, g.get(cur).get(i))).get(Math.max(cur, g.get(cur).get(i))));
				}
				else {
					times.get(cur).put(parent, times.get(cur).getOrDefault(parent, 0l)
							+ times.get(Math.min(cur, g.get(cur).get(i))).get(Math.max(cur, g.get(cur).get(i))));
				}
			}
		}
	}
}