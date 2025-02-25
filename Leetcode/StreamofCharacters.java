class StreamChecker {

	// https://leetcode.com/problems/stream-of-characters/ 
	
	trie root = new trie();
	StringBuilder sb = new StringBuilder();
	
    public StreamChecker(String[] words) {
        for (String s : words) root.insert(s);
    }
    
    public boolean query(char letter) {
        sb.append(letter);
		
        trie curr = root;
		for(int i=sb.length()-1; i>=0 && i>=sb.length()-2000; i--) {
			int at = sb.charAt(i) - 'a';
			if (curr != null && curr.wordCount > 0) return true;
			if(curr.children[at] == null) return false;
			curr = curr.children[at];
		}
		return curr.wordCount > 0;
    }
    
    class trie {
		
		trie[] children = new trie[26];
		int wordCount = 0;					// number of words ending here
		
		void insert(String s) {
			trie curr = this;
			for(int i=s.length()-1; i>=0; i--) {		// backwards
				int at = s.charAt(i) - 'a';
				if(curr.children[at] == null) curr.children[at] = new trie();
				curr = curr.children[at];
			}
			curr.wordCount++;
		}
    }
}

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker obj = new StreamChecker(words);
 * boolean param_1 = obj.query(letter);
 */