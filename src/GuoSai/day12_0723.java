package GuoSai;
import java.io.*;
import java.util.*;

public class day12_0723 {
	public static void main(String[] args) {
		/*
		 * MC0409池中有 n 朵圣莲，每朵圣莲都蕴含着一丝残存的灵力，灵力值均是正整数，
		 * 分别为 a_1, a_2,...., a_n。
		 * 要让圣莲池恢复生机，必须进行 k 次净化仪式，每次净化按照以下步骤进行：
		 * 1.吸收灵力：选择灵力最强的圣莲，将其灵力值吸收并加入恢复之力（初始恢复之力为0）。
		 * 2.莲花枯萎或再生：若选中的圣莲灵力值仅剩1，则彻底枯萎消失；
		 * 否则，该圣莲的灵力衰减1，继续存在于池中。
		 * 算算 k 次仪式后积攒了多少恢复之力！”
		PriorityQueue<Long> minHeap = new PriorityQueue<Long>((x,y)->Long.compare(x, y));
		Scanner input = new Scanner(System.in);
		
		long n = input.nextLong();
		long k = input.nextLong();
		
		
		long Sum = 0;
		
		for(int i =0; i<n; i++)  {
			
			minHeap.offer(input.nextLong());
			
		}
		
		
	
		while( k != 0 ) {
			
			long num = minHeap.poll();
			
			if( k >= num  ) {
				Sum += (num +1 )*num /2;
				k -=num;
				continue;
				
			}

			
			// 到达这里说明 k已经比最小值还要小了
			// 那么求和公式的首项就是 num 尾项是 num-k+1;
			
			Sum += ( num + num - k +1 ) * k /2;
			k = 0;
			
		}
		System.out.print(Sum);
		*/
		
		/*
		 * 爬楼梯
		 */
		
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		
		int[] dp = new int[n];
		
		dp[1]= 1;
		dp[2] = 2;
		
		for(int i = 3 ; i <= n ; i++) {
			dp[i] = dp[i-1] *dp[i-2];
		}
		System.out.print(dp[n]);
		
		
	}

}
