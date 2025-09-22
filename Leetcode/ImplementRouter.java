import java.util.*;

// https://leetcode.com/problems/implement-router/?envType=daily-question&envId=2025-09-20

class Router {

    private int memoryLimit;
    private Set<Packet> allPackets;
    private Queue<Packet> queue;
    
    // destination = [timestamps]
    private Map<Integer, List<Integer>> destinationMap;
    // keep track of index for ^ timestamps based on removed packets
    private Map<Integer, Integer> destinationIndex;

    public Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        allPackets = new HashSet<>();
        queue = new ArrayDeque<>();
        destinationMap = new HashMap<>();
        destinationIndex = new HashMap<>();
    }
    
    public boolean addPacket(int source, int destination, int timestamp) {
        Packet curPacket = new Packet(source, destination, timestamp);
        if (allPackets.contains(curPacket)) return false;

        allPackets.add(curPacket);
        queue.add(curPacket);
        destinationMap.putIfAbsent(destination, new ArrayList<>());
        destinationMap.get(destination).add(timestamp);
        destinationIndex.putIfAbsent(destination, 0);

        if (allPackets.size() > memoryLimit) removeNextPacket();

        return true;
    }
    
    public int[] forwardPacket() {
        Packet nextPacket = removeNextPacket();
        if (nextPacket == null) return new int[]{};
        return new int[]{nextPacket.s, nextPacket.d, nextPacket.t};
    }

    private Packet removeNextPacket() {
        if (queue.isEmpty()) return null;
        Packet nextPacket = queue.poll();
        allPackets.remove(nextPacket);
        destinationIndex.put(nextPacket.d, destinationIndex.get(nextPacket.d) + 1);
        return nextPacket;
    }
    
    public int getCount(int destination, int startTime, int endTime) {
        if (!destinationMap.containsKey(destination)) return 0;

        List<Integer> timestamps = destinationMap.get(destination);
        int startIndex = destinationIndex.get(destination);

        int startTimeIndex = binarySearch(timestamps, startTime, startIndex);
        int endTimeIndex = binarySearch(timestamps, endTime+1, startIndex) - 1;
        return endTimeIndex - startTimeIndex + 1;
    }

        // find first occurrence of # >= v
    private int binarySearch(List<Integer> t, int v, int start) {
        int min = start;
        int max = t.size();
        while (min < max) {
            int mid = (min + max) >> 1;
            if (t.get(mid) >= v) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    private class Packet {
        int s, d, t;
        Packet(int s, int d, int t) {
            this.s = s;
            this.d = d;
            this.t = t;
        }

        public boolean equals(Object o) {
            if (o instanceof Packet) {
                Packet otherPacket = (Packet)(o);
                return s == otherPacket.s && d == otherPacket.d && t == otherPacket.t;
            }
            return false;
        }

        public int hashCode() {
            return s * 97 + d * 113 + t * (int)(1e9+7);
        }
    }
}


/**
 * Your Router object will be instantiated and called as such:
 * Router obj = new Router(memoryLimit);
 * boolean param_1 = obj.addPacket(source,destination,timestamp);
 * int[] param_2 = obj.forwardPacket();
 * int param_3 = obj.getCount(destination,startTime,endTime);
 */