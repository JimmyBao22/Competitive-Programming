
public class CountingBits {

	// https://leetcode.com/problems/counting-bits/
	
	public static void main(String[] args) {

	}

	public static int[] countBits(int num) {
		int[] b = new int[num+1];
		for (int i = 0; i < num+1; i++) {
			b[i] = b[i>>1] + i%2;
		}
		return b;
	}
}

//good solution: https://leetcode.com/problems/counting-bits/discuss/305448/Very-basic-java-solution.-3-line
	/*
		basically consider each of the binary representations
						
			if u divide by 2, it is the same as shifting right by 1 digit
			
			ex. 1101010101 --> 110101010
			
			if the original number is odd, the rightmost 1 disappears, so u add 1
				if it is even the rightmost 0 disappears so u dont add anything
	 */	