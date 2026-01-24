import java.io.*;
import java.util.*;

public class MooinTime {
    
    // https://usaco.org/index.php?page=viewproblem2&cpid=1445

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        String s = in.readLine();

        // try all possible moos
        List<String> mooList = new ArrayList<>();
        int moos = 0;
        for (char ci = 'a'; ci <= 'z'; ci++) {
            for (char cj = 'a'; cj <= 'z'; cj++) {
                if (ci == cj) continue;

                // count occurrences
                boolean[] usedPositions = new boolean[n];
                int count = 0;
                for (int i = 0; i + 2 < n; i++) {
                    if (s.charAt(i) == ci && s.charAt(i+1) == cj && s.charAt(i+2) == cj) {
                        count++;
                        usedPositions[i] = true;
                        usedPositions[i+1] = true;
                        usedPositions[i+2] = true;
                        i += 2;
                    } 
                }
                
                for (int i = 0; i + 2 < n; i++) {
                    if (usedPositions[i] || usedPositions[i+1] || usedPositions[i+2]) continue;

                    if (s.charAt(i+1) == cj && s.charAt(i+2) == cj) {
                        count++;
                        break;
                    } else if (s.charAt(i) == ci && s.charAt(i+2) == cj) {
                        count++;
                        break;
                    } else if (s.charAt(i) == ci && s.charAt(i+1) == cj) {
                        count++;
                        break;
                    }
                }

                if (count >= f) {
                    moos++;
                    mooList.add("" + ci + cj + cj);
                }
            }
        }

        Collections.sort(mooList);

        StringBuilder sb = new StringBuilder();
		sb.append(moos);
        sb.append("\n");
        for (int i = 0; i < mooList.size(); i++) {
            sb.append(mooList.get(i));
            sb.append("\n");
        }
        
        System.out.print(sb);
	}
}