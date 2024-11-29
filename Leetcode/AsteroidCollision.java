import java.util.Arrays;
import java.util.Stack;

public class AsteroidCollision {

    // https://leetcode.com/problems/asteroid-collision/

    public int[] asteroidCollision(int[] asteroids) {
        int n = asteroids.length;

        int countAlive = 0;
        boolean[] alive = new boolean[n];
        Stack<Integer> goingRightVals = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (asteroids[i] > 0) {
                // keep track of rocks going right
                goingRightVals.push(asteroids[i]);
            } else {
                // see if this rock is going to get destroyed
                while (!goingRightVals.isEmpty() && Math.abs(asteroids[i]) > goingRightVals.peek()) {
                    // this rock going left will destroy the smaller one going right
                    goingRightVals.pop();
                }

                if (!goingRightVals.isEmpty() && Math.abs(asteroids[i]) == goingRightVals.peek()) {
                    // both rocks get destroyed
                    goingRightVals.pop();
                } else if (goingRightVals.isEmpty()) {
                    // survives
                    alive[i] = true;
                    countAlive++;
                }
            }
        }

        System.out.println(Arrays.toString(alive));

        Stack<Integer> goingLeftVals = new Stack<>();
        for (int i = n-1; i >= 0; i--) {
            if (asteroids[i] < 0) {
                // keep track of rocks going left
                goingLeftVals.push(Math.abs(asteroids[i]));
            } else {
                // see if this rock is going to get destroyed
                while (!goingLeftVals.isEmpty() && asteroids[i] > goingLeftVals.peek()) {
                    // this rock going right will destroy the smaller one going left
                    goingLeftVals.pop();
                }

                if (!goingLeftVals.isEmpty() && asteroids[i] == goingLeftVals.peek()) {
                    // both rocks get destroyed
                    goingLeftVals.pop();
                } else if (goingLeftVals.isEmpty()) {
                    // survives
                    alive[i] = true;
                    countAlive++;
                }
            }
        }

        System.out.println(Arrays.toString(alive));

        int[] ans = new int[countAlive];
        int p = 0;
        for (int i = 0; i < n; i++) {
            if (alive[i]) ans[p++] = asteroids[i];
        }

        return ans;
    }

}
