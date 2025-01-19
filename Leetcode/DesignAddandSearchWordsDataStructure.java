class WordDictionary {
    
    // https://leetcode.com/problems/design-add-and-search-words-data-structure/

    Trie t;

    public WordDictionary() {
        t = new Trie();
    }
    
    public void addWord(String word) {
        t.insert(word);
    }
    
    public boolean search(String word) {
        return t.contains(t, word, 0);
    }

    class Trie {
		
		Trie[] children = new Trie[26];
		int wordCount = 0;					// number of words ending here
		
		void insert(String s) {
			Trie curr = this;
			for(int i = 0; i < s.length(); i++) {
				int at = s.charAt(i) - 'a';
				if (curr.children[at] == null) curr.children[at] = new Trie();
				curr = curr.children[at];
			}
			curr.wordCount++;
		}
		
		boolean contains(Trie cur, String s, int pos) {
            if (pos == s.length()) return cur.wordCount > 0;

            if (s.charAt(pos) == '.') {
                for (int i = 0; i < 26; i++) {
                    if (cur.children[i] != null) {
                        if (contains(cur.children[i], s, pos + 1)) return true;
                    }
                }
                return false;
            }
            else {
                if (cur.children[s.charAt(pos) - 'a'] == null) return false;
                return contains(cur.children[s.charAt(pos) - 'a'], s, pos + 1);
            }
		}
	}
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */