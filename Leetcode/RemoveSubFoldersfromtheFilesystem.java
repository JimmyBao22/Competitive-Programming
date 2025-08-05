import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoveSubFoldersfromtheFilesystem {

    // https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/description/
    
    private List<String> ans;

    public List<String> removeSubfolders(String[] folder) {
        int n = folder.length;
        Trie root = new Trie();
        for (int i = 0; i < n; i++) {
            insert(folder[i], root);
        }

        ans = new ArrayList<>();
        getTopFolders(ans, root, "");
        return ans;
    }

    private void getTopFolders(List<String> ans, Trie cur, String s) {
        if (cur.isWord) {
            ans.add(s);
            return;
        }

        for (String key : cur.children.keySet()) {
            getTopFolders(ans, cur.children.get(key), s + "/" + key);
        }
    }

    private void insert(String s, Trie root) {
        Trie tempTrie = root;

        int m = s.length();
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) == '/') continue;

            if (tempTrie.isWord) {
                // this word is a sub-folder
                return;
            }

            int nextIndex = s.indexOf('/', i);
            if (nextIndex == -1) nextIndex = m;
            String cur = s.substring(i, nextIndex);
            i = nextIndex;

            if (!tempTrie.children.containsKey(cur)) {
                tempTrie.children.put(cur, new Trie());
            }
            tempTrie = tempTrie.children.get(cur);
        }

        tempTrie.isWord = true;
    }

    private class Trie {
        Map<String, Trie> children;
        boolean isWord;

        Trie() {
            children = new HashMap<>();
            isWord = false;
        }
    }    
}

/*

    A non-trie solution

    public List<String> removeSubfolders(String[] folder) {
        int n = folder.length;
        Arrays.sort(folder);
        List<String> topFolders = new ArrayList<>();
        topFolders.add(folder[0]);
        for (int i = 1; i < n; i++) {
            String prev = topFolders.get(topFolders.size() - 1);
            int m = prev.length();
            if (folder[i].length() == m && folder[i].equals(prev)) continue;
            if (folder[i].length() > m) {
                prev += "/";
                m++;
                if (folder[i].substring(0, m).equals(prev)) {
                    // this folder is a subfolder
                    continue;
                }
            }

            topFolders.add(folder[i]);
        }

        return topFolders;
    }

 */