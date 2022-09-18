import java.util.*;

public class PalindromePairs {
    
    public static void main(String[] args) {
        String[] cur = new String[] {"bb","bababab","baab","abaabaa","aaba","","bbaa","aba","baa","b"};

        List<List<Integer>> a = palindromePairs(cur);
        for (int i=0; i<a.size(); i++) {
        	System.out.println(a.get(i).toString());
        }
    }
	
	// https://leetcode.com/problems/palindrome-pairs/

    static long mod = (long)(1e9+7);
    static int n, MaxM;
    static long[] power;
    static long p = 911382323;
    static long[][] forwardHashes, backwardHashes;
    public static List<List<Integer>> palindromePairs(String[] words) {
        n = words.length;
        forwardHashes = new long[n][];
        backwardHashes = new long[n][];
        MaxM = 301;
        power = new long[MaxM];
        
        calc_power1();

        for (int i=0; i<n; i++) {

            forwardHashes[i] = new long[words[i].length()];
            backwardHashes[i] = new long[words[i].length()];

            forwardHashes[i] = prefHash1(words[i], forwardHashes[i]);

            StringBuilder cur = new StringBuilder(words[i]);
            cur.reverse();
            backwardHashes[i] = prefHash1(cur.toString(), backwardHashes[i]);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i==j) continue;

                String one = words[i];
                String two = words[j];

                int m = one.length();
                int m2 = two.length();
                
                if (m > m2) {
                    if (m2 == 0 || forwardHashes[i][m2-1] == backwardHashes[j][m2-1]) {
                        int left = m - m2;
                        
                        if (SubstringHash1(m2, m2 - 1 + left / 2, forwardHashes[i]) 
                        		== SubstringHash1(0, left / 2 - 1, backwardHashes[i])) {
                            List<Integer> cur = new ArrayList<>();
                            cur.add(i);
                            cur.add(j);
                            ans.add(cur);
                        }
                    }
                }
                else {
                    if (m == 0 || forwardHashes[i][m-1] == backwardHashes[j][m-1]) {
                        int left = m2 - m;
                        
                        if (SubstringHash1(m, m - 1 + left / 2, backwardHashes[j])
                        		== SubstringHash1(0, left / 2 - 1, forwardHashes[j])) {
                            List<Integer> cur = new ArrayList<>();
                            cur.add(i);
                            cur.add(j);
                            ans.add(cur);
                        }
                    }
                }
            }
        }

        return ans;
    }

    public static long SubstringHash1(int i, int j, long[] pref) {
    	if (i > j) return 0;
    	if (j < 0) return 0;
	    if (i != 0) {
	        return (((pref[j] - (pref[i-1] * power[j - i + 1])%mod)%mod+mod)%mod);
	    }
	    else {
	        return pref[j];
	    }
	}
	
	public static long[] prefHash1(String s, long[] pref) {
	    if (s.length() > 0) pref[0] = s.charAt(0) - 'a';
	    for (int i=1; i<s.length(); i++) {
	        pref[i] = (pref[i-1]*p + (s.charAt(i) - 'a'))%mod;
	    }
	    return pref;
	}
	
	public static void calc_power1() {
	    power[0] = 1;
	    for (int i=1; i<MaxM; i++) {
	        power[i] = power[i-1] * p;
	        power[i] %= mod;
	    }
	}
}
