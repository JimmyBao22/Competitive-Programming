import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.HashMap;

class TaskManager {

    // https://leetcode.com/problems/design-task-manager/?envType=daily-question&envId=2025-09-18

    Map<Integer, Task> taskIdMap;
    PriorityQueue<Task> topTasks;

    public TaskManager(List<List<Integer>> tasks) {
        taskIdMap = new HashMap<>();
        topTasks = new PriorityQueue<>();

        int n = tasks.size();
        for (int i = 0; i < n; i++) {
            List<Integer> curInput = tasks.get(i);
            add(curInput.get(0), curInput.get(1), curInput.get(2));
        }
    }
    
    public void add(int userId, int taskId, int priority) {
        Task curTask = new Task(userId, taskId, priority);
        taskIdMap.put(taskId, curTask);
        topTasks.add(curTask);
    }
    
    public void edit(int taskId, int newPriority) {
        if (!taskIdMap.containsKey(taskId)) return;
        Task oldTask = taskIdMap.get(taskId);
        rmv(taskId);
        add(oldTask.userId, oldTask.taskId, newPriority);
    }
    
    public void rmv(int taskId) {
        Task oldTask = taskIdMap.get(taskId);
        oldTask.valid = false;
        taskIdMap.remove(taskId);
    }
    
    public int execTop() {
        while (!topTasks.isEmpty() && !topTasks.peek().valid) {
            topTasks.poll();
        }
        if (topTasks.isEmpty()) return -1;
        Task topTask = topTasks.poll();
        rmv(topTask.taskId);
        return topTask.userId;
    }

    private class Task implements Comparable<Task> {
        int userId, taskId, priority;
        boolean valid;
        
        Task(int uid, int tid, int prio) {
            userId = uid;
            taskId = tid;
            priority = prio;
            valid = true;
        }

        public int compareTo(Task o) {
            if (priority == o.priority) {
                return o.taskId - taskId;
            }
            return o.priority - priority;
        }

        public String toString() {
            return userId + " " + taskId + " " + priority;
        }
    }
}

/**
 * Your TaskManager object will be instantiated and called as such:
 * TaskManager obj = new TaskManager(tasks);
 * obj.add(userId,taskId,priority);
 * obj.edit(taskId,newPriority);
 * obj.rmv(taskId);
 * int param_4 = obj.execTop();
 */