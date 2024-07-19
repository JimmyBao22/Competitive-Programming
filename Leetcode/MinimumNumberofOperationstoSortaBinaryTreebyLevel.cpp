#include <queue>
#include <vector>
#include <iostream>
using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class Solution {
public:

    // https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/

    int minimumOperations(TreeNode* root) {
        queue<pair<TreeNode*, int>> q;
        q.push({root, 0});
        int ans = 0;
        vector<int> level_values;
        int current_level = 0;
        while (!q.empty()) {
            auto cur = q.front();
            q.pop();
            if (current_level != cur.second) {
                // need to process the previous level stuff
                ans += processLevel(level_values);
                level_values.clear();
                current_level++;
            }
            level_values.push_back(cur.first->val);
            if (cur.first->left) q.push({cur.first->left, cur.second+1});
            if (cur.first->right) q.push({cur.first->right, cur.second+1});
        }
        ans += processLevel(level_values);
        return ans;
    }
    
    int processLevel(vector<int> level_values) {
        vector<int> sorted;
        unordered_map<int, int> indices;
        int n = level_values.size();
        for (int i = 0; i < n; i++) {
            indices[level_values[i]] = i;
            sorted.push_back(level_values[i]);
        }
        
        sort(sorted.begin(), sorted.end());
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (level_values[i] != sorted[i]) {
                int actual_index = indices[sorted[i]];
                swap(level_values, indices, i, actual_index);
                count++;
            }
        }

        return count;
    }
    
    void swap(vector<int> &level_values, unordered_map<int, int> &indices, int a, int b) {
        int val1 = level_values[a];
        int val2 = level_values[b];
        level_values[a] = val2;
        level_values[b] = val1;
        indices[val1] = b;
        indices[val2] = a;
    }
    
    void print(vector<int> v) {
        for (auto i : v) cout << i << " ";
        cout << "\n";
    }
};