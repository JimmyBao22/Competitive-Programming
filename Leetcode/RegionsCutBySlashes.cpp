#include <set>
#include <vector>
#include <string>
using namespace std;

class Solution {
public:

    // https://leetcode.com/problems/regions-cut-by-slashes/description/

    static const int MAXN = 31;

    bool outOfBounds(int i, int j, int n) {
        return i < 0 || j < 0 || i >= n || j >= n;
    }

    int regionsBySlashes(vector<string>& grid) {
        int n = grid.size();
        DSU dsu(n);
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid[i].length(); j++) {
                // always union_sets to outside blocks
                if (!outOfBounds(i-1, j, n)) dsu.union_sets(i, j, 0, i-1, j, 2);
                if (!outOfBounds(i+1, j, n)) dsu.union_sets(i, j, 2, i+1, j, 0);
                if (!outOfBounds(i, j-1, n)) dsu.union_sets(i, j, 3, i, j-1, 1);
                if (!outOfBounds(i, j+1, n)) dsu.union_sets(i, j, 1, i, j+1, 3);

                // determine which internal blocks to union_sets
                if (grid[i][j] == ' ') {
                    dsu.union_sets(i, j, 0, i, j, 1);
                    dsu.union_sets(i, j, 1, i, j, 2);
                    dsu.union_sets(i, j, 2, i, j, 3);
                } else if (grid[i][j] == '/') {
                    dsu.union_sets(i, j, 1, i, j, 2);
                    dsu.union_sets(i, j, 0, i, j, 3);
                } else if (grid[i][j] == '\\') {
                    dsu.union_sets(i, j, 0, i, j, 1);
                    dsu.union_sets(i, j, 2, i, j, 3);
                }
            }
        }

        set<pair<pair<int, int>, int>> s;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 4; k++) {
                    s.insert(dsu.find_parent(i, j, k));
                }
            }
        }

        return s.size();
    }

    struct DSU {
        int n;
        // in the 3rd dim, 0 = top, 1 = right, 2 = bottom, 3 = left
        pair<pair<int, int>, int> parent[MAXN][MAXN][4];
        int size[MAXN][MAXN][4];
        DSU(int n) {
            this->n = n;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < 4; k++) {
                        parent[i][j][k] = {{i, j}, k};
                        size[i][j][k] = 1;
                    }
                }
            }
        }

        pair<pair<int, int>,int> find_parent(int i, int j, int k) {
            auto p = parent[i][j][k];
            pair<pair<int, int>,int> required_p = {{i, j}, k};
            if (p == required_p) return required_p;
            return parent[i][j][k] = find_parent(p.first.first, p.first.second, p.second);
        }

        void union_sets(int i1, int j1, int k1, int i2, int j2, int k2) {
            auto root1 = find_parent(i1, j1, k1);
            auto root2 = find_parent(i2, j2, k2);
            
            if (root1 == root2) return;
            
            int r1i = root1.first.first, r1j = root1.first.second, r1k = root1.second;
            int r2i = root2.first.first, r2j = root2.first.second, r2k = root2.second;
            
            if (size[r1i][r1j][r1k] < size[r2i][r2j][r2k]) {
                parent[r1i][r1j][r1k] = root2;
                size[r2i][r2j][r2k] += size[r1i][r1j][r1k];
            } else {
                parent[r2i][r2j][r2k] = root1;
                size[r1i][r1j][r1k] += size[r2i][r2j][r2k];
            }
        }
    };
};