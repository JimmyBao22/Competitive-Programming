#include <string>
#include <vector>
using namespace std;

class LargestColorValueinaDirectedGraph {
public:
    
    int n, m;
    static const int MAXN = 1e5+1;

    int largestPathValue(string colors, vector<vector<int>>& edges) {
        n = colors.length();
        m = edges.size();
        vector<vector<int>> g(n), memo(n, vector<int>(26));
        vector<int> visited(n), open(n);
        for (int i = 0; i < m; i++) {
            g[edges[i][0]].push_back(edges[i][1]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (dfs(i, colors, g, memo, visited, open)) return -1;
            for (int j = 0; j < 26; j++) {
                ans = max(ans, memo[i][j]);
            }
        }

        return ans;
    }

    bool dfs(int i, string& colors, vector<vector<int>>& g, vector<vector<int>>& memo, vector<int>& visited, vector<int>& open) {
        // for each color, only need to take the max over each path
        if (open[i]) return true;
        if (visited[i]) return false;
        open[i] = true;
        visited[i] = true;

        for (auto to : g[i]) {
            if (dfs(to, colors, g, memo, visited, open)) return true;
            for (int j = 0; j < 26; j++) {
                memo[i][j] = max(memo[i][j], memo[to][j]);
            }
        }

        memo[i][colors[i] - 'a']++;
        open[i] = false;
        return false;
    }
};