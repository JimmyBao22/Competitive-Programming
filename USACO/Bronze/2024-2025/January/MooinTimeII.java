import java.util.*;

public class MooinTimeII {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1468
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int[] pref = new int[n];    // stores prefix sum of (+1) for first time seeing a value
        boolean[] seen = new boolean[n+1];
        pref[0] = 1;
        seen[a[0]] = true;
        for (int i = 1; i < n; i++) {
            pref[i] = pref[i-1];
            if (!seen[a[i]]) {
                pref[i]++;
                seen[a[i]] = true;
            }
        }
        
        long moos = 0;
        int[] included = new int[n+1];  // 0 = not seen, 1 = seen once, 2 = seen twice
        for (int i = n-1; i >= 0; i--) {
            included[a[i]]++;
            if (included[a[i]] == 2) {
                // this is the 2nd to last a[i]. We can now form triplets of (x, a[i], a[i])
                moos += (pref[i] - 1);  // exclude (a[i], a[i], a[i])
            }
        }

        System.out.println(moos);
    }
}
