
import java.util.*;
import java.io.*;

public class ALotofGames {

	// https://codeforces.com/contest/455/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ALotofGames"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			trie p = new trie();
			String[] arr = new String[n];
			for (int i=0; i<n; i++) {
				arr[i] = in.readLine();
				p.insert(arr[i]);
			}
			
			dfs(p);
			boolean losing = false;
			boolean winning = false;
			for (int i=0; i<26; i++) {
				if (p.children[i] != null) {
					if (p.children[i].winningStrat) winning = true;
					if (p.children[i].losingStrat) losing = true;				
				}
			}
			
			if (winning && losing) {
				System.out.println("First");
			}
			else if (winning && !losing) {
				if (k%2 == 1) System.out.println("First");
				else System.out.println("Second");
			}
			else if (!winning) {
				 System.out.println("Second");
			}
		}
	}
	
	public static void dfs(trie cur) {
		boolean w = false;
		boolean l = false;
		boolean child = false;
		for (int i=0; i<26; i++) {
			if (cur.children[i] != null) {
				child = true;
				dfs(cur.children[i]);
				if (cur.children[i].winningStrat) {
					w = true;
				}
				if (cur.children[i].losingStrat) {
					l = true;
				}
			}
		}
		if (!w) cur.winningStrat = true;
		if (!l && child) cur.losingStrat = true;
	}
	
	static class trie {
		
		trie[] children = new trie[26];
		int wordCount = 0;					// number of words ending here
		boolean winningStrat = false;
		boolean losingStrat = false;

		void insert(String s) {
			trie curr = this;
			for(int i=0; i<s.length(); i++) {
				int at = s.charAt(i) - 'a';
				if(curr.children[at] == null) curr.children[at] = new trie();
				curr = curr.children[at];
			}
			curr.wordCount++;
		}
	}
}