class Solution:

    # https://leetcode.com/problems/number-of-ways-to-assign-edge-weights-ii/?envType=daily-question&envId=2026-06-12

    MOD = 10**9 + 7
    MAXN = 10**5 + 1
    LOG = 32

    def assignEdgeWeights(self, edges: List[List[int]], queries: List[List[int]]) -> List[int]:
        self.n = len(edges) + 1
        self.g = [[] for _ in range(self.n)]
        for i in range(self.n-1):
            self.g[edges[i][0]-1].append(edges[i][1]-1)
            self.g[edges[i][1]-1].append(edges[i][0]-1)

        self.depth = [0] * self.n
        self.parent = [[-1] * self.LOG for _ in range(self.n)]
        
        self.dfs(0, -1, 0)
        self.precompute()

        answers = []
        for i in range(len(queries)):
            dist = self.get_dist(queries[i][0]-1, queries[i][1]-1)
            if dist == 0:
                answers.append(0)
            else:
                answers.append(self.my_pow(2, dist - 1))

        return answers

    def get_dist(self, u, v):
        return self.depth[u] + self.depth[v] - 2 * self.depth[self.lca(u,v)]

    def lca(self, u, v):
        if self.depth[u] > self.depth[v]:
            temp = u
            u = v
            v = temp

        # depth[u] <= depth[v]
        diff = self.depth[v] - self.depth[u]
        for i in range(self.LOG-1, -1, -1):
            if ((diff >> i) & 1):
                v = self.parent[v][i]

        if u == v:
            return u

        for i in range(self.LOG-1, -1, -1):
            if self.parent[u][i] != self.parent[v][i]:
                u = self.parent[u][i]
                v = self.parent[v][i]
        
        return self.parent[u][0]

    def precompute(self):
        for j in range(1, self.LOG):
            for i in range(self.n):
                if self.parent[i][j-1] != -1:
                    self.parent[i][j] = self.parent[self.parent[i][j-1]][j-1]


    def dfs(self, cur, p, d):
        self.parent[cur][0] = p
        self.depth[cur] = d
        for next in self.g[cur]:
            if p != next:
                self.dfs(next, cur, d + 1)

    def my_pow(self, a, b):
        x = 1
        while b > 0:
            if b & 1 == 1:
                x *= a
                x %= self.MOD
            
            a *= a
            a %= self.MOD
            b >>= 1

        return x