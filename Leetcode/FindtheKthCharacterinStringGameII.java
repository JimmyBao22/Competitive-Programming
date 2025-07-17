import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FindtheKthCharacterinStringGameII {

    // https://leetcode.com/problems/find-the-k-th-character-in-string-game-ii/
    
    public char kthCharacter(long k, int[] operations) {
        int n = operations.length;

        List<Boolean> doOperation = new ArrayList<>();
        
        // since k only goes up to 1e14, only need to care about first 50 transformations
        long[] pow2 = new long[51];
        pow2[0] = 1;
        for (int i = 1; i <= 50; i++) {
            pow2[i] = pow2[i-1] << 1;
        }

        for (int i = Math.min(n-1, 50); i >= 0; i--) {
            long base = pow2[i];
            if (k > base) {
                doOperation.add(true);
                k -= base;
            } else {
                doOperation.add(false);
            }
        }

        Collections.reverse(doOperation);

        int curChar = 0;
        for (int i = 0; i < doOperation.size(); i++) {
            if (doOperation.get(i) && operations[i] == 1) {
                curChar = (curChar + 1) % 26;
            }
        }

        return (char)('a' + curChar);
    }
}

/*

Each time string doubles in length, so I feel like you can come from end to beginning and figure it out

example 2

4 operations —> lengths 1,2,4,8,16 (final = 16)
10 = 8 + 2, so comes from character 2 in operation 4
2 = 2, so comes from character 2 in operation 3
2 = 2, so comes from character 2 in operation 2
2 = 1 + 1, so comes from character 1 in operation 1

“a” -> “a”
“a” -> “a”
“a” -> “a”
“a” -> “b”

if you were looking for char 15:
15 = 8 + 7, comes from character 7 in operation 4
7 = 4 + 3, comes from character 3 in operation 3
3 = 2 + 1, comes from character 1 in operation 2
1 = 1, comes from character 1 in operation 1

“a” -> “a”
“a” -> “b”
“b” -> “b”
“b” -> “c”

*/