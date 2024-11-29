import java.util.HashMap;
import java.util.Map;

class LRUCache {

    // https://leetcode.com/problems/lru-cache/

    int capacity;
    Node sentinel, lastNode;
    Map<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        sentinel = new Node(0, 0);
        lastNode = sentinel;
        map = new HashMap<>();
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            // update LRU linked list
            Node oldNode = map.get(key);
            oldNode.skip = true;
            Node newNode = new Node(key, oldNode.value);
            map.put(key, newNode);
            lastNode.next = newNode;
            lastNode = newNode;

            return newNode.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        Node newNode = new Node(key, value);
        if (map.containsKey(key)) {
            // 'remove' last value
            Node prev = map.get(key);
            prev.skip = true;
            
            // add new value, and add it to the end of the linkedlist
            map.put(key, newNode);
            lastNode.next = newNode;
            lastNode = newNode;
        } else {
            if (map.size() == capacity) {
                // remove the Least Recently Used Key
                
                // first remove all nodes that are designated as 'removed' already
                while (sentinel.next.skip) {
                    sentinel.next = sentinel.next.next;
                }

                // remove an actual node to stay under capacity
                Node toRemove = sentinel.next;
                map.remove(toRemove.key);
                
                // add new value, and add it to the end of the linkedlist
                map.put(key, newNode);
                lastNode.next = newNode;
                lastNode = newNode;
                sentinel.next = sentinel.next.next;
            } else {
                map.put(key, newNode);
                lastNode.next = newNode;
                lastNode = newNode;
            }
        }
    }

    private class Node {
        int key, value;
        boolean skip;
        Node next;
        Node (int key, int value) {
            this.key = key;
            this.value = value;
            skip = false;
            next = null;
        }
    }
}

/*
    Another solution, using doubly linked list:

    class LRUCache {

        HashMap<Integer, LNode> m;
        // sentinel nodes
        LNode head, tail;
        int capacity;
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            m = new HashMap<>();
            head = new LNode(0, 0);
            tail = new LNode(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        public int get(int key) {
            if (m.containsKey(key)) {
                
                // move this key node to the front
                LNode current = m.get(key);
                LNode prev = current.prev;
                LNode next = current.next;
                prev.next = next;
                next.prev = prev;
                
                current.next = head.next;
                current.prev = head;
                head.next.prev = current;
                head.next = current;
                
                return current.val;
            }
            return -1;
        }
        
        public void put(int key, int value) {
            if (m.containsKey(key)) {
                // move this node to the front
                LNode current = m.get(key);
                LNode prev = current.prev;
                LNode next = current.next;
                prev.next = next;
                next.prev = prev;
                
                current.next = head.next;
                current.prev = head;
                head.next.prev = current;
                head.next = current;
                
                current.val = value;
            }
            else {
                if (m.size() == capacity) {
                    // remove the last node
                    m.remove(tail.prev.key);
                    tail.prev = tail.prev.prev;
                    tail.prev.next = tail;
                }
                // create a new node at the front
                LNode current = new LNode(key, value);
                current.next = head.next;
                current.prev = head;
                head.next.prev = current;
                head.next = current;
                m.put(key, current);
            }         
        }
        
        class LNode {
            int key, val;
            LNode prev;
            LNode next;
            LNode (int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }


 */

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */