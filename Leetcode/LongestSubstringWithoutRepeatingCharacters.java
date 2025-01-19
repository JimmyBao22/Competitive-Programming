import java.util.*;

public class LongestSubstringWithoutRepeatingCharacters {

	// https://leetcode.com/problems/longest-substring-without-repeating-characters/
	
	public static void main(String[] args) {
		lengthOfLongestSubstring("ynyo");
	}
	
	public static int lengthOfLongestSubstring(String s) {
        // use a queue and a hashset
		Deque<Character> seq = new ArrayDeque<>();
		HashSet<Character> curcontained = new HashSet<>();
		int max = 0;
		
		for (int i = 0; i < s.length(); i++) {
			seq.add(s.charAt(i));
			if (curcontained.contains(s.charAt(i))) {
				max = Math.max(max, curcontained.size());
				while (seq.peek() != s.charAt(i)) {
					Character cur = seq.poll();
					curcontained.remove(cur);
				}
				seq.poll();
				curcontained.remove(s.charAt(i));
			}
			curcontained.add(s.charAt(i));
		}
		max = Math.max(max, curcontained.size());
		return max;	
    }
}