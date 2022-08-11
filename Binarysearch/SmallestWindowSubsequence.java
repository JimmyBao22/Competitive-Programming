
import java.util.*;
import java.io.*;

public class SmallestWindowSubsequence {

	public static void main(String[] args) {

	}
	
	// https://binarysearch.com/problems/Smallest-Window-Subsequence

    public String solve(String s, String t) {
        int n = s.length();
        int m = t.length();
        TreeSet<Integer>[] a = new TreeSet[26];
            //  for each letter, create a treeset of positions in string s.
        for (int i=0; i<26; i++) {
            a[i] = new TreeSet<>();
        }
        for (int i=0; i<n; i++) {
            a[s.charAt(i) - 'a'].add(i);
        }

        int start = 0;
        int end = 0;
        int size = (int)(1e9);
        o: for (Integer x : a[t.charAt(0) - 'a']) {
            int curpos = x;
            for (int i=1; i<m; i++) {
                Integer next = a[t.charAt(i) - 'a'].higher(curpos);
                if (next == null) break o;
                curpos = next;
            }
            if (curpos - x + 1 < size) {
                size = curpos - x + 1;
                start = x;
                end = curpos;
            }
        }

        if (size == (int)(1e9)) return "";
        return s.substring(start, end+1);
    }
}