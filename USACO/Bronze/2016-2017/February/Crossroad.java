import java.io.*;
import java.util.*;

public class Crossroad {

    // https://usaco.org/index.php?page=viewproblem2&cpid=711
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("crossroad.in"));
        int n = in.nextInt();
        
        int[] cowPos = new int[11];
        Arrays.fill(cowPos, -1);
        
        int crossings = 0;
        for (int i = 0; i < n; i++) {
            int cow = in.nextInt();
            int pos = in.nextInt();
            if (cowPos[cow] == 1 - pos) {
                crossings++;
            }
            cowPos[cow] = pos;
        }

        PrintWriter pw = new PrintWriter(new File("crossroad.out"));
        pw.println(crossings);
        pw.close();
    }
}
