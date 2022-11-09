
import java.util.*;
import java.io.*;

public class Cryptography {

	// https://codeforces.com/edu/course/2/lesson/4/4/practice/contest/274684/problem/B
	
	static long r;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Cryptography"));
		Reader in = new Reader();
		
		//StringTokenizer st = new StringTokenizer(in.readLine());
		r = in.nextLong();
		int n = in.nextInt();
		int m = in.nextInt();
		SegTree s = new SegTree(n);
		Matrix[] arr = new Matrix[n];
		for (int i=0; i<n; i++) {
			long[][] c = new long[2][2];
			//st = new StringTokenizer(in.readLine());
			c[0][0] = in.nextLong()%r;
			c[0][1] = in.nextLong()%r;
			//st = new StringTokenizer(in.readLine());
			c[1][0] = in.nextLong()%r;
			c[1][1] = in.nextLong()%r;
			Matrix cur = new Matrix(c);
			arr[i] = cur;
			in.readLine();
		}
		s.build(arr);
		
		StringBuilder sb = new StringBuilder("");
		for (int i=0; i<m; i++) {
			//st = new StringTokenizer(in.readLine());
			int left = in.nextInt()-1;
			int right = in.nextInt();
			Matrix ret = s.comp(left, right);
			sb.append(ret.grid[0][0]).append(" ").append(ret.grid[0][1]).append("\n");
			sb.append(ret.grid[1][0]).append(" ").append(ret.grid[1][1]).append("\n");
			sb.append("\n");
		}
		System.out.println(sb);
	}

	// source: https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
	static class Reader 
    { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 
  
        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream(new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) 
            { 
                if (c == '\n') 
                    break; 
                buf[cnt++] = (byte) c; 
            } 
            return new String(buf, 0, cnt); 
        } 
  
        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do
            { 
                ret = ret * 10 + c - '0'; 
            }  while ((c = read()) >= '0' && c <= '9'); 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
  
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
  
            if (c == '.') 
            { 
                while ((c = read()) >= '0' && c <= '9') 
                { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 
  
        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 
  
        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }
	
	static class Matrix {
		long[][] grid;
		Matrix(long[][] g) {
			grid = g;
		}
	}
	
	static class SegTree {
		
		int size;
		Matrix[] tree;
		Matrix g;
		
		public SegTree(int n) {			
			size = 1;
			long[][] d = new long[2][2];
			d[0][0]=1; d[1][1]=1;
			g = new Matrix(d);
			while (size < n) size *= 2;
			tree = new Matrix[2*size];
			for (int i=0; i<2*size; i++) {
				long[][] c = new long[2][2];
				tree[i] = new Matrix(c);
			}
		}
				
		// use for random computation
		public Matrix comp(int l, int r) {
			return comp(l, r, 0, 0, size);
		}
		
		public Matrix comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return g;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			Matrix one = comp(l, r, 2*x+1, lx, m);
			Matrix two = comp(l, r, 2*x+2, m, rx);
			return mult(one, two);
		}
		
		public void build(Matrix[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(Matrix[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					tree[x] = arr[lx];
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			tree[x] = mult(tree[2*x+1], tree[2*x+2]);
		}
		
		public Matrix mult(Matrix a, Matrix b) {
			long[][] ans = new long[2][2];
			ans[0][0] = (a.grid[0][0] * b.grid[0][0] + a.grid[0][1] * b.grid[1][0])%r;
			ans[0][1] = (a.grid[0][0] * b.grid[0][1] + a.grid[0][1] * b.grid[1][1])%r;
			ans[1][0] = (a.grid[1][0] * b.grid[0][0] + a.grid[1][1] * b.grid[1][0]) % r;
			ans[1][1] = (a.grid[1][0] * b.grid[0][1] + a.grid[1][1] * b.grid[1][1])%r;
//			for (int i=0; i<2; i++) {
//				for (int j=0; j<2; j++) {
//					for (int k=0; k<2; ++k) {
//						ans[i][k] += a.grid[i][j] * b.grid[j][k] % r;
//						ans[i][k] %= r;
//					}
//				}
//			}
			Matrix prod = new Matrix(ans);
			return prod;
		}
	}
}
/*
	Keep track of multiplication within the tree instead of addition

*/