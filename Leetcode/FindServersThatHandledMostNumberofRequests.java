import java.util.*;

public class FindServersThatHandledMostNumberofRequests {

    // https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests/

    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int n = arrival.length;
        Server[] servers = new Server[k];
        TreeSet<Integer> availableServers = new TreeSet<>();
        PriorityQueue<Server> usedServers = new PriorityQueue<>(new Comparator<Server>() {
            public int compare(Server a, Server b) {
                return a.time - b.time;
            }
        });
            // map req to list of indices
        Map<Integer, Set<Integer>> countReqs = new HashMap<>();
        countReqs.put(0, new HashSet<>());
        int maxCountReq = 0;
        for (int i = 0; i < k; i++) {
            servers[i] = new Server(i, -1);
            availableServers.add(i);
            countReqs.get(0).add(i);
        }

        for (int i = 0; i < n; i++) {
            while (!usedServers.isEmpty() && usedServers.peek().time <= arrival[i]) {
                Server freed = usedServers.poll();
                availableServers.add(freed.index);
            }

            // add this current one in
            Integer serverIndex = availableServers.ceiling(i % k);
            if (serverIndex == null) {
                serverIndex = availableServers.ceiling(0);
                if (serverIndex == null) {
                    // dropped, nothing can handle this req
                    continue;
                }
            }

            availableServers.remove(serverIndex);
            Server curServer = servers[serverIndex];
            countReqs.get(curServer.count).remove(serverIndex);
            curServer.count++;
            if (!countReqs.containsKey(curServer.count)) {
                countReqs.put(curServer.count, new HashSet<>());
            }
            countReqs.get(curServer.count).add(serverIndex);
            maxCountReq = Math.max(maxCountReq, curServer.count);

            curServer.time = arrival[i] + load[i];
            usedServers.add(curServer);
        }

        List<Integer> ans = new ArrayList<>();
        for (Integer x : countReqs.get(maxCountReq)) {
            ans.add(x);
        }
        return ans;
    }

    public class Server {
        int index, time, count;
        Server (int index, int time) {
            this.index = index;
            this.time = time;
            count = 0;
        }
    }
}

/*
 
    Failed C++ Implementation:

    using namespace std;

    class Solution {
    public:
        struct Server {
            int index, time, count;
            Server() {}
            Server (int index, int time) {
                this -> index = index;
                this -> time = time;
                count = 0;
            }
        };

        struct ServerCompare {
            bool operator()(Server& a, Server& b) const {
                return a.time > b.time;
            }
        };

        set<int> availableServers;
        priority_queue<Server, vector<Server>, ServerCompare> usedServers;
        map<int, set<int>> countReqs;

        vector<int> busiestServers(int k, vector<int>& arrival, vector<int>& load) {
            int n = arrival.size();
            Server servers[k];
            countReqs[0] = set<int>();
            int maxReqCount = 0;
            for (int i = 0; i < k; i++) {
                servers[i] = Server(i, -1);
                availableServers.insert(i);
                countReqs[0].insert(i);
            }

            for (int i = 0; i < n; i++) {
                while (!usedServers.empty() && usedServers.top().time <= arrival[i]) {
                    Server freed = usedServers.top();
                    usedServers.pop();
                    availableServers.insert(freed.index);
                }

                // add this current one in
                auto serverIndex = availableServers.upper_bound(i % k);
                if (serverIndex == availableServers.end()) {
                    serverIndex = availableServers.upper_bound(0);
                    if (serverIndex == availableServers.end()) {
                        // dropped, nothing can handle this req
                        continue;
                    }
                }

                availableServers.erase(serverIndex);
                Server curServer = servers[*serverIndex];
                countReqs[curServer.count].erase(*serverIndex);
                curServer.count++;
                if (countReqs.find(curServer.count) == countReqs.end()) {
                    countReqs[curServer.count] = set<int>();
                }
                countReqs[curServer.count].insert(*serverIndex);
                maxReqCount = max(maxReqCount, curServer.count);

                curServer.time = arrival[i] + load[i];
                usedServers.push(curServer);
            }

            vector<int> ans;
            for (auto x : countReqs[maxReqCount]) {
                ans.push_back(x);
            }
            return ans;
        }
    };
 */