import java.util.Arrays;

class FruitsIntoBasketsIII {

    // https://leetcode.com/problems/fruits-into-baskets-iii/

    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int n = fruits.length;
        SegTree segTree = new SegTree(n);
        segTree.build(baskets);

        // for each fruit, find the first basket that has size >= this fruit
        // do this through binary search
        int unplaced = 0;
        for (int i = 0; i < n; i++) {
            int min = 0;
            int max = n;
            while (min < max) {
                int mid = (min + max) >> 1;
                int maxFound = segTree.getMax(0, mid + 1);
                if (maxFound >= fruits[i]) {
                    max = mid;
                } else {
                    min = mid + 1;
                }
            }

            if (min == n) {
                // no basket was found
                unplaced++;
            } else {
                // basket was found. basket is at index min
                segTree.insert(min, 0);
            }
        }

        return unplaced;
    }

    // Segment Tree maintains max value
    class SegTree {
        private int size;
        private int[] tree;
        SegTree(int n) {
            size = 1;
            while (size < n) {
                size <<= 1;
            }
            tree = new int[size << 1];
        }

        // find max value between [l, r)
        int getMax(int l, int r) {
            return getMax(0, size, l, r, 0);
        }

        private int getMax(int lx, int rx, int l, int r, int x) {
            // check if my current segment [lx, rx) is contained by [l, r)
            if (lx >= l && rx <= r) {
                return tree[x];
            }
            // check if [lx, rx) is outside the required segment [l, r)
            if (rx <= l || lx >= r) return 0;

            int mid = (lx + rx) >> 1;
            int ret1 = getMax(lx, mid, l, r, (x << 1) + 1);
            int ret2 = getMax(mid, rx, l, r, (x << 1) + 2);
            return Math.max(ret1, ret2);
        }

        // set arr[i] = v
        void insert(int i, int v) {
            insert(0, size, i, v, 0);
        }

        private void insert(int lx, int rx, int i, int v, int x) {
            if (rx - lx == 1) {
                tree[x] = v;
                return;
            }

            int mid = (lx + rx) >> 1;
            if (i < mid) {
                insert(lx, mid, i, v, (x << 1) + 1);
            } else {
                insert(mid, rx, i, v, (x << 1) + 2);
            }

            tree[x] = Math.max(tree[(x << 1) + 1], tree[(x << 1) + 2]);
        }

        void build(int[] arr) {
            build(arr, 0, size, 0);
        }

        private void build(int[] arr, int lx, int rx, int x) {
            if (rx - lx == 1) {
                if (lx < arr.length) tree[x] = arr[lx];
                return;
            }

            int mid = (lx + rx) >> 1;
            build(arr, lx, mid, (x << 1) + 1);
            build(arr, mid, rx, (x << 1) + 2);

            tree[x] = Math.max(tree[(x << 1) + 1], tree[(x << 1) + 2]);
        }

        public String toString() {
            return Arrays.toString(tree);
        }
    }
}