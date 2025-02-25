public class KthSymbolinGrammar {

	// https://leetcode.com/problems/k-th-symbol-in-grammar/
	
	public static void main(String[] args) {
		kthGrammar(7, 10);
	}
		
	public static int kthGrammar(int N, int K) {
		return rec(N, K-1);
    }
	
	public static int rec(int level, int index) {
		if (level == 0) return 0;
		
		if (index % 2 == 1) {
			int ret = rec(level - 1, (index - 1)/2);
			if (ret == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			int ret = rec(level - 1, (index)/2);
			if (ret == 0) {
				return 0;
			}
			else {
				return 1;
			}
		}
	}
}