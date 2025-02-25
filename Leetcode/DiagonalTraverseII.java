import java.util.*;

public class DiagonalTraverseII {

	// https://leetcode.com/contest/weekly-contest-186/problems/diagonal-traverse-ii/
	
	public static void main(String[] args) {
		List<List<Integer>> a = new ArrayList<>();
		a.add(new ArrayList<>());
		a.get(0).add(14);
		a.get(0).add(12);
		a.get(0).add(19);
		a.get(0).add(16);
		a.get(0).add(9);
		a.add(new ArrayList<>());
		a.get(1).add(13);
		a.get(1).add(14);
		a.get(1).add(15);
		a.get(1).add(8);
		a.get(1).add(11);

		a.add(new ArrayList<>());
		a.get(2).add(11);
		a.get(2).add(13);
		a.get(2).add(1);
		int[] x = findDiagonalOrder(a);
		for (int i = 0; i < x.length; i++) System.out.print(x[i] + " ");
	}
	
	public static int[] findDiagonalOrder(List<List<Integer>> nums) {
		int row = nums.size();
		
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
	    	// key is the sum
	    	// value will be the value in nums
	    for (int i = 0; i < row; i++ ) {
		   for (int j = 0; j < nums.get(i).size(); j++) {
			   if (!map.containsKey(i+j)) {
				   map.put(i+j, new ArrayList<>());
			   }
			   map.get(i+j).add(nums.get(i).get(j));
		   }
	    }
	    
	    ArrayList<Integer> ans = new ArrayList<>();
	    for (Integer a : map.keySet()) {
	    	ArrayList<Integer> cur = map.get(a);
	    	for (int i = cur.size()-1; i>=0; i--) ans.add(cur.get(i));
	    }
	    
	    int[] f = new int[ans.size()];
	    for (int i = 0; i < ans.size(); i++) f[i] = ans.get(i);
	    return f;   
    }
}

/*
	Intead, idea is to use a map in order to put all elements w/ the same sum 
		into the same set in the map. then loop through the map, smallest going first
		and then add it into an array in the end.
*/