#include <vector>
#include <queue>
using namespace std;

class Solution {
public:

    // https://leetcode.com/problems/task-scheduler/

    int leastInterval(vector<char>& tasks, int n) {
        int m = tasks.size();
        using T = pair<int, int>;
        priority_queue<int> current_tasks;
        priority_queue<T, vector<T>, greater<T>> future_tasks;
        
        vector<int> letters_count(26);
        for (int i = 0; i < m; i++) {
            letters_count[tasks[i] - 'A']++;
        }
        
        for (int i = 0; i < 26; i++) {
            if (letters_count[i])
                current_tasks.push(letters_count[i]);
        }
        
        int interval = 0;
        while (!current_tasks.empty() || ! future_tasks.empty()) {
            interval++;
            
            while (!future_tasks.empty()) {
                if (future_tasks.top().first == interval) {
                    current_tasks.push(future_tasks.top().second);
                    future_tasks.pop();
                }
                else {
                    break;
                }
            }
            
            if (!current_tasks.empty()) {
                auto t = current_tasks.top();
                current_tasks.pop();
                if (--t)
                    future_tasks.push({interval + n + 1, t});
            }
        }
        
        return interval;
    }
};