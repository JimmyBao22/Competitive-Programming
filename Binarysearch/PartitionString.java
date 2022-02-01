
import java.util.*;
import java.io.*;

public class PartitionString {

	public static void main(String[] args) {
		int[] arr = solve("cocoplaydae");
		for (int i=0; i<arr.length; i++) 
		{
			System.out.println(arr[i]);
		}
	}
	
	// https://binarysearch.com/problems/Partition-String
	
	public static int[] solve(String s) {
		HashMap<Character, Integer> map = new HashMap<>();
			// character, last pos
		int n = s.length();
		for (int i=0; i<n; i++) {
			map.put(s.charAt(i), i);
		}
		
		ArrayList<Integer> arr = new ArrayList<>();
		int lastpos=0;
		for (int i=0; i<n; i++) {
			int count=0;
			lastpos = map.get(s.charAt(i));
			for (int j=i; j<=lastpos; j++) {
				lastpos = Math.max(lastpos, map.get(s.charAt(j)));
				count++;
				i++;
			}
			i--;
			arr.add(count);
		}
		
		int[] arr2 = new int[arr.size()];
		for (int i=0; i<arr.size(); i++) {
			arr2[i] = arr.get(i);
		}
		return arr2;
		
    }
}