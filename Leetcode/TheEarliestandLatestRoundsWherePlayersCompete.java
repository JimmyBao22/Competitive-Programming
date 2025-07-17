import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheEarliestandLatestRoundsWherePlayersCompete {

    // https://leetcode.com/problems/the-earliest-and-latest-rounds-where-players-compete/
    
    private int minDepth = 10;
    private int maxDepth = 1;

    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        // try all different combinations
        List<Integer> playersLeft = new ArrayList<>();
        for (int j = 1; j <= n; j++) {
            playersLeft.add(j);
        }

        findDepths(playersLeft, firstPlayer, secondPlayer, 0);

        return new int[]{minDepth, maxDepth};
    }

    private void findDepths(List<Integer> playersLeft, int firstPlayer, int secondPlayer, int depth) {
        if (playersLeft == null) {
            minDepth = Math.min(minDepth, depth);
            maxDepth = Math.max(maxDepth, depth);
            return;
        }

        int n = playersLeft.size();
        int combos = (n - 2) / 2;
        for (int combo = 0; combo < (1 << combos); combo++) {
            List<Integer> updatedPlayersLeft = updatedPlayersLeft(playersLeft, combo, firstPlayer, secondPlayer);
            // System.out.println(updatedPlayersLeft);
            findDepths(updatedPlayersLeft, firstPlayer, secondPlayer, depth+1);
        }
    }

    private List<Integer> updatedPlayersLeft(List<Integer> playersLeft, int combo, int firstPlayer, int secondPlayer) {
        List<Integer> updatedPlayersLeft = new ArrayList<>();
        int comboIndex = 0;
        for (int j = 0; j < (playersLeft.size() + 1) / 2; j++) {
            int player1 = playersLeft.get(j);
            int player2 = playersLeft.get(playersLeft.size() - j - 1);
            if (player1 == player2) {
                // happens when theres an odd number of players left
                updatedPlayersLeft.add(player1);
            } else if (player1 == firstPlayer || player1 == secondPlayer) {
                if (player2 == firstPlayer || player2 == secondPlayer) {
                    return null;
                }
                updatedPlayersLeft.add(player1);
            } else if (player2 == firstPlayer || player2 == secondPlayer) {
                updatedPlayersLeft.add(player2);
            } else {
                if (((combo >> comboIndex) % 2) == 0) {
                    // left player wins
                    updatedPlayersLeft.add(player1);
                } else {
                    // right player wins
                    updatedPlayersLeft.add(player2);
                }
                comboIndex++;
            }
        }

        Collections.sort(updatedPlayersLeft);
        return updatedPlayersLeft;
    }
}