// 2026-05-16 
import java.io.*;
import java.util.*;

public class Day9 {
	public static void main(String[] args) throws IOException {
		/*
		 * 
		 * 
		 * 字符串 s 长度为 n，对它进行 q 次询问，每次询问包括两个参数 l, r (l <= r)，他必须迅速回答区间 s_l ~ s_r 中，有多少种字母出现次数为偶数，有多少种为奇数。
		 * * 输入格式：
		 * 第一行两个整数 n, q (1 <= n, q <= 10^5)。
		 * 第二行一个长度为 n 的字符串 s。
		 * 接下来 q 行，每行两个整数 l, r (1 <= l <= r <= n)。
		 * * 输出格式：
		 * 输出 q 行，每行两个整数 x, y，x 表示出现次数为偶数的字母数量，y 表示出现次数为奇数的字母数量。
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int q = input.nextInt();
		
		int[][] sum = new int[n+1][26];// 从第一个到第i个之间出现的每个字母计数
		String line = input.next();
		
		for(int i = 1 ; i <=n ; i++) {
			for(int j = 0 ; j < 26 ; j++) {
				// 继承上一个状态 ， 无论n为多大只循环二十六次
				sum[i][j] = sum[i-1][j];
				
			}
			sum[i][line.charAt(i-1) - 'a']++;
		}
		
		int OshuCount = 0;
		for(int i = 0 ; i< q ; i++) {
			int l = input.nextInt();
			int r = input.nextInt();
			OshuCount = 0;
			
			for(int j = 0 ; j < 26 ; j++) {
				if( (sum[r][j] - sum[l-1][j]) %2 == 0 ) {
					OshuCount++;
					
				}
	
			}
			System.out.println(OshuCount +" "+ (26 - OshuCount));
		}
		*/
		
		/*一个长度为 n 的整数序列 a_1 ~ a_n。
		 * 你 q 次询问，每次询问给你两个参数 l 和 r，你需要计算出：
		 * 1 * a_l + 2 * a_{l+1} + 3 * a_{l+2} + ... + (r-l+1) * a_r 的和。
		 * 如果答案很大，你只需要算出其对 998244353 取模的结果。若你能算出正确结果，便可解封符咒，助你们通过火焰山。”
		 * * 输入格式：
		 * 第一行两个整数 n, q (1 <= n, q <= 5 * 10^5)。
		 * 第二行 n 个整数 a_1 ~ a_n (1 <= a_i <= 10^9)。
		 * 接下来 q 行，每行两个整数 l, r (1 <= l <= r <= n)，表示 q 组询问。
		 * * 输出格式：
		 * 输出 q 行，表示对于每次询问的答案
		
		
		
	
		// 使用 BufferedReader 和 StringTokenizer 替代 Scanner，解决输入超时问题
		 */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 使用 PrintWriter 替代 System.out.println，解决输出超时问题
        long MOD = 998244353;
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // sum1: 普通前缀和，记录 a[1] + ... + a[i]
        long[] sum1 = new long[n + 1];
        // sum2: 带权前缀和，记录 1*a[1] + ... + i*a[i]
        long[] sum2 = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            long a = Long.parseLong(st.nextToken());
            
            // 预处理普通前缀和，步步取模
            sum1[i] = (sum1[i - 1] + a) % MOD;
            
            // 预处理带权前缀和，步步取模
            // 注意：i * a 最大可能达到 5*10^5 * 10^9 = 5*10^14，在 long 的安全范围内，不会溢出
            sum2[i] = (sum2[i - 1] + i * a % MOD) % MOD;
        }

        // 处理 q 次查询
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 获取区间 [l, r] 的带权和，注意防负数陷阱 (+ MOD)
            long part2 = (sum2[r] - sum2[l - 1] + MOD) % MOD;
            
            // 获取区间 [l, r] 的普通和，注意防负数陷阱 (+ MOD)
            long part1 = (sum1[r] - sum1[l - 1] + MOD) % MOD;

            // 套用公式：目标和 = 区间带权和 - (l - 1) * 区间普通和
            long ans = (part2 - (long)(l - 1) * part1 % MOD + MOD) % MOD;

            // 写入缓冲区
            out.println(ans);
        }

        // 最后一次性将缓冲区的所有结果输出
        out.flush();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int q = input.nextInt();
		int[] NumberArr = new int[n+1];
		int[] P = new int[n+1];
		int[] Q = new int[n+1];
		
		int Numbersum = 0;
		for(int i = 1 ; i <=n ; i++) {
			NumberArr[i] = input.nextInt();			
			Numbersum += NumberArr[i];
			Q[i] = Q[i-1]+ NumberArr[i];
			
			
		}
		int j = n;
		for(int i = 0 ; i<= n ; i++) {
			P[i] = Numbersum - Q[j];
			j--;
			// z1+z2+z3
			// 0
			// z1
			// z1+z2
			// z1+z2+z3
			
		}
		for(int i = 0 ;  i < q ; i++) {
			int l = input.nextInt();
			int r = input.nextInt();
			long sum = 0;
			for(int m = P.length-r ;  m <= (P.length - l) ; m++) { //映射
				sum += P[m] - P[P.length-(r+1)];
				sum %= 998244353;
				
				//
				// 3 2 1
				//	 l r
				//	 2 3
				
				// 1 *2 + 2 * 1;
				
				//
				//		   1 1
				//	   2 + 1 2
				// 3 + 2 + 1 3
				
				
				
				
				// 1  2
				// z1 z2 z3 z4 z5 z6 z7
				//  l  r  		
				// 1*z1 + 2*z2
				//
				//             
				//				    z7 1	
				//				 z6+z7 2
				//			  z5+z6+z7 3 	
				//		   z4+z5+z6+z7 4	
				//		z3+z4+z5+z6+z7 5
				//   z2+z3+z4+z5+z6+z7 6
				//z1+z2+z3+z4+z5+z6+z7 7
				
			
		}
		

		System.out.println(sum);

		
	}
	*/
  }
}

