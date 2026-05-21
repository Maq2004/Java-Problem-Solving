import java.io.*;
import java.util.*;

public class Day6 {
	public static void main(String[] agrs) throws IOException {
		
		/*
		 * * 专诸记录了一个长度为 n 的整数序列 a[1] ~ a[n]，表示一系列动作的初始熟练度。
		 * 在接下来的 q 次训练调整中，系统会给出 q 次操作。
		 ** 【操作类型】
		 * 每次操作可能是以下三种之一：
		 * * 1. 区间加法 (1 l r x) : 将序列中 a[l] 到 a[r] 的每个元素都加上 x。
		 * 2. 区间取小 (2 l r x) : 对于 a[l] 到 a[r] 的每个元素，若它大于 x，则将其强制变成 x（否则保持不变）。
		 * 3. 区间求和 (3 l r)   : 询问并输出序列中 a[l] 到 a[r] 的所有元素总和。
		 * * 【输出要求】
		 * 请处理所有的操作，并按顺序输出每次“类型 3”操作的求和结果。
		Scanner input = new Scanner(System.in);
		int n = input.nextInt(); // 整数序列的长度
		int q = input.nextInt();// 接下来有几次操作

		int[] arr = new int[n+1]; // 因为l 和 r的是从1开始数的，为了对齐
		
		for(int i = 1 ; i<=n ; i++) {
			arr[i] = input.nextInt();
		}
		
		while(q >0) {
			int number = input.nextInt();
			int l = input.nextInt();
			int r = input.nextInt();
			
			if( number == 1 ) {
				int x = input.nextInt();
					// number == 1 的情况
				for(int i = l ; i <=r ; i++) {
					arr[i] += x;
				}
			}else if (number == 2) {
				int x = input.nextInt();
				// number ==2 的情况
				for( int i = l ; i <= r ; i++) {
					if( arr[i] > x) {
						arr[i] = x;
					}
				}
				
			}
			else {
				int sum = 0;
				// number == 3 的 情况
				for(int i = l; i <= r;i++) {
					sum += arr[i];
				}
				System.out.println(sum);
				
			}
			q--;
		}
		*/
		
		
		/*
         * 求多次事件操作后，所有市井摊位的分数总和（每次事件使得区间外的摊位分数值+1）。
         * 所有的摊位处于一个连续的整数集合 [min, max] 中。
         * 面对高达 3*10^5 的操作次数，我们绝不能去遍历每个摊位模拟加分
         * 每次事件 [l, r] 发生时，实际上只有两种情况：
         * 1. 摊位被 [l, r] 覆盖（交集内）：分数不变。
         * 2. 摊位未被 [l, r] 覆盖（交集外）：分数 +1。
         * * 终极结果 = (总摊位数 n - [min, max] 与 [l, r] 的交集摊位数) 的累加。
         */

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        /* * ==========================================
         * 【QA 笔记：思维复盘】
         * Q1: 为什么之前的代码会算错甚至漏算？
         * A: 我在寻找重叠区间时，想当然地认为 Right - Left 就是覆盖数，却忽略了“单点重合”和“完全不重合”的物理意义。
         *
         * Q2: 为什么检查不出错？
         * A: 大脑发生了“惯性作弊”。画图推演时只画了最常见的“部分相交”情况，
         * 潜意识避开了去测试极端边缘状态（如区间完全分离，或者刚好贴在一个点上）。
         *
         * Q3: 核心判断标准究竟是什么？
         * A: 两个区间是否有交集的【唯一绝对数学条件】是：交集的左边界 <= 交集的右边界 (Left <= Right)。
         * ==========================================
         */

        // --- 正确代码实现（直球对决思维 / 拒绝巧合） ---
        
        /* * 【关键改进 1】
         * 之前我想的是 min = Number.length，这是一个极具误导性的巧合。
         * 寻找最小值必须用无穷大（Long.MAX_VALUE）打底，最大值用无穷小打底。
         * 这样才能抵御任何极其奇葩的测试数据。
         */
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        st = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            long number = Long.parseLong(st.nextToken());
            if (min > number) min = number;
            if (max < number) max = number;
        }

        long total = 0;

        while (m-- > 0) {
            st = new StringTokenizer(input.readLine());
            long l = Long.parseLong(st.nextToken());
            long r = Long.parseLong(st.nextToken());

            // 寻找实际被覆盖的有效区间交集
            long Left = Math.max(min, l);
            long Right = Math.min(max, r);

            /* * 【关键改进 2】
             * 放弃主观臆断的 Left < Right，采用严谨的 Left <= Right。
             * 并且补全了 else 分支：一旦发现无效交集，意味着全部不在范围内，全员加分！
             */
            if (Left <= Right) {
                // 存在有效交集，总摊位数减去交集数量（注意 +1 补齐植树问题差值）
                total += n - (Right - Left + 1);
            } else {
                // 完全无交集（Left > Right），所有摊位全部加分
                total += n;
            }

            // 防止中间过程过大，每步取模
            total %= 10007;
        }

        System.out.println(total % 10007);

        /*
         * ==========================================
         * 【对照组：错误逻辑回顾】
         * * // 1. 致命的初始化边界错误：
         * long min = Number.length; // 导致如果真实摊位坐标极大，min 永远无法被更新
         * * // 2. 缺失的边界与遗漏的 else：
         * if( Left < Right ) { 
         * // 致命伤 1：把 [5, 5] 这种单点覆盖的情况给吞了，少算了覆盖数。
         * total += n - (Right - Left + 1);
         * } else {
         * ; 
         * // 致命伤 2：当完全没交集时，居然什么都没加。本该全员加 n 分，这里凭空蒸发了。
         * }
         * ==========================================
         * * 提醒未来自己：
         * 1. 极值初始化法：求最大/最小值，永远用 Long.MIN_VALUE / Long.MAX_VALUE 起手，绝对不能用数组长度去凑。
         * 2. MECE 原则 (相互独立，完全穷尽)：写下 if 的那一刻，必须强迫大脑去想 else 里的世界长什么样，哪怕里面是空的，也要写上注释证明自己想过了。
         * 3. 植树问题敏锐度：涉及到求区间内的元素个数，一旦出现相减（Right - Left），必须停下来反问自己：“要不要 +1？”
         */
		
		
		
		
		
		
		
		
		
	}

}
