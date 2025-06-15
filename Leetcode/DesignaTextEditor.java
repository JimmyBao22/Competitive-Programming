class TextEditor {

    // https://leetcode.com/problems/design-a-text-editor/

    private Node sentinel;
    private Node curNode;

    public TextEditor() {
        sentinel = new Node(' ', null, null);
        curNode = sentinel; // position = right of this node
    }
    
    public void addText(String text) {
        for (char c : text.toCharArray()) {
            Node cur = new Node(c, curNode, curNode.next);
            if (curNode.next != null) curNode.next.prev = cur;
            curNode.next = cur;
            curNode = cur;
        }
    }
    
    public int deleteText(int k) {
        int numDeleted = 0;
        for (int i = 0; i < k; i++) {
            if (curNode.equals(sentinel)) break;
            curNode.prev.next = curNode.next;
            if (curNode.next != null) curNode.next.prev = curNode.prev;
            curNode = curNode.prev;
            numDeleted++;
        }
        return numDeleted;
    }
    
    public String cursorLeft(int k) {
        for (int i = 0; i < k; i++) {
            if (curNode.equals(sentinel)) break;
            curNode = curNode.prev;
        }
        return getLast10Chars();
    }
    
    public String cursorRight(int k) {
        for (int i = 0; i < k; i++) {
            if (curNode.next == null) break;
            curNode = curNode.next;
        }
        return getLast10Chars();
    }

    private String getLast10Chars() {
        String last10 = "";
        Node temp = curNode;
        for (int i = 0; i < 10; i++) {
            if (temp.equals(sentinel)) break;
            last10 = temp.c + last10;
            temp = temp.prev;
        }
        return last10;
    }

    private class Node {
        char c;
        Node prev, next;
        Node(char c, Node prev, Node next) {
            this.c = c;
            this.prev = prev;
            this.next = next;
        }
    }
}

/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */