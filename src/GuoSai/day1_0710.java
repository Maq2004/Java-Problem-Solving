package GuoSai;

import java.util.*;
import java.io.*;

/**
 * =====================================================================
 * 二分答案 (Binary Search on Answer) 心法与三板斧
 * =====================================================================
 *
 * 【一】 考场上如何一眼看穿是否为二分题？
 * 只要题目同时满足以下 2~3 条，连想都不用想，直接默写二分模板：
 * 1. 极限字眼：题目问“最大值最小是多少”、“最小值最大是多少”、“最多能分多少”、“最少需要多少”。
 * 2. 逆天数据：某个操作次数 K 或答案的值域大得离谱（如 10^9 或 10^14），但数组长度 N 只有 10^5 级别。
 * 3. 单调性（一刀切法则）：答案存在绝对的分水岭。
 * - 比如：要求网线切 200 米能凑够，那要求切 199 米【绝对也】能凑够；切 201 米不够，那 202 米【绝对也】不够。
 * - 只要满足“一旦某个值可行，比它更优的值全可行；一旦不可行，比它更差的值全不可行”，就是二分！
 * - [能凑够, 能凑够, 能凑够, ... , 能凑够 | 凑不够, 凑不够, 凑不够]
 * ---------------------------------------------------------------------
 *
 * 【二】 破题三板斧：完全没有思路时怎么推导？
 * * 第一斧：降维打击，手动模拟极小数据
 * - 绝对不要一上来就盯着字母 (n, mid, k) 空想公式！
 * - 在草稿纸上画个线段，随便写 3 个具体的极小数字（比如坐标 -2, 1, 4）。
 * - 把自己当成题目里的角色，走一遍流程，看人脑是怎么选出最优解的。大白话的逻辑，就是公式的雏形。
 * * 第二斧：寻找“不变的物理限制”，构建 check(mid)
 * - 二分的本质是把“求解问题”转化为“判定问题”。
 * - 问自己：“假设答案就是 mid，题目给我的【死限制】是什么？”
 * - 比如：刀数 <= K，或者 凑出的网线数 >= M，或者 总耗时 <= T。
 * - check() 函数的任务只有一个：遍历一遍数组，强行用 mid 去算出一个总量，然后和限制条件比大小。
 * * 第三斧：确定二分形态，套用对立模板
 * - 所有的二分答案只分两种形态：求下限（最小） vs 求上限（最大）。
 *
 * 形态 A：求满足条件的【最小值】（例：圣莲池找最低基准线）
 * - 贪心心态：当前 mid 可行，我还想看看能不能更小！
 * - 边界收缩：
 * if (check 成功) { targetMid = mid; right = mid - 1; } 
 * else { left = mid + 1; }
 *
 * 形态 B：求满足条件的【最大值】（例：切网线求最长、汤姆抓杰瑞求最多）
 * - 贪心心态：当前 mid 可行，我还想看看能不能更大！
 * - 边界收缩：
 * if (check 成功) { targetMid = mid; left = mid + 1; } 
 * else { right = mid - 1; }
 *
 * ---------------------------------------------------------------------
 *
 * 【三】 考场防溢出超时超内存指南
 * * 1. 遇事不决用 long：
 * 只要操作次数 K 很大，或者 check() 里要累加求和，累加变量（如 sum, count）和
 * 边界（left, right, mid）必须全部无脑用 long。
 * * 2. 状态绝对隔离：
 * 在 while 循环内部，验证状态的变量（如 sumCount）必须在每轮重新声明并初始化为 0。
 * * 3. 防溢出与防越界（兜底艺术）：
 * - 求中点永远写： mid = left + (right - left) / 2;
 * - 如果 mid = 0 会导致除数为 0 或数组下标越界（如 i+mid-1 变成 -1），
 * 就把 left 初始值设为 1。利用 targetMid = 0 的初始值来做安全兜底。
 * * 4. 慎用 Scanner：
 * 当 N >= 10^5 且平台内存限制极严（如 128MB）时，Scanner 极易导致 MLE（超内存）或 TLE（超时）。
 * 此时必须改用 BufferedReader + StringTokenizer 快读模板。
 * * =====================================================================
 */
public class day1_0710 {

    public static void main(String[] args) throws IOException {
        // solveLotusPool();       // 题目一：拯救圣莲池2
        // solveNetworkCable();    // 题目二：小码哥的机房布线
        // solveTomAndJerry();     // 题目四：汤姆抓杰瑞
        solveLumberjack();         // 题目三：伐木工人
    }

    /**
	 * 题目一：拯救圣莲池2 (二分答案 - 核心形态 A：求满足条件的最低下限)
	 * * 【题目描述】
	 * 圣莲池修复后不久，唐僧师徒正欲出城赶路，国王大使却匆匆赶来道：
	 * “圣僧留步，圣莲池又不行了，还望各位能再次出手相救啊！”
	 * 重返圣莲池，小码哥发现，池中依然有 n 朵圣莲，每朵圣莲都蕴含着一丝残存的灵力，
	 * 灵力值均是正整数，分别为 a_1, a_2, ..., a_n，可是祭坛铭文发生了变化。
	 * 要让圣莲池恢复生机，依然要进行 k 次净化仪式，但每次净化的步骤有了少许改变：
	 * 1. 吸收灵力：选择灵力最强的圣莲，将其灵力值吸收并加入恢复之力（初始恢复之力为 0）。
	 * 2. 莲花枯萎或再生：若选中的圣莲灵力值仅剩 1，则彻底枯萎消失；
	 * 否则，该圣莲的灵力衰减 1，继续存在于池中。
	 * 小码哥自信满满地说：“别急，让我再来算算，这下经过 k 次仪式后积攒了多少恢复之力！”
	 * * 【输入格式】
	 * 第一行包含两个整数 n 和 k，分别表示圣莲的数量和净化仪式的次数。
	 * 第二行包含 n 个正整数 a_1, a_2, ..., a_n，表示每朵圣莲初始的灵力值。
	 * * 【输出格式】
	 * 输出一个整数，表示经过 k 次仪式后，总共积攒的恢复之力。
	 * * 【数据范围】
	 * 1 <= n <= 10^5
	 * 1 <= k <= 10^14
	 * 1 <= a_i <= 10^9
	 */
    public static void solveLotusPool() {
        Scanner input = new Scanner(System.in);
        long n = input.nextLong(); // 数组的大小
        long k = input.nextLong(); // 循环的大小
        
        long[] Number = new long[(int)n];
        long max = 0;
        
        for(int i = 0; i < n; i++) {
            Number[i] = input.nextLong();
            if(max < Number[i]) {
                max = Number[i];
            }
        }
        
        long left = 0; 
        long right = max; 
        long targetmid = 0; 
        
        while(left <= right) {
            long mid = left + (right - left) / 2; 
            long sumCount = 0;
            for(int i = 0; i < n; i++) {
                if(Number[i] > mid) {
                    sumCount += (Number[i] - mid);
                }
            }
            if(sumCount > k) { 
                left = mid + 1;	
            } else { 
                targetmid = mid;
                right = mid - 1;
            }
        }
        
        long sum = 0;
        long UseCut = 0;
        for(int i = 0; i < n; i++) {
            if(Number[i] > targetmid) {
                UseCut += (Number[i] - targetmid);
                sum += (Number[i] + targetmid + 1) * (Number[i] - targetmid) / 2;
            }
        }
        sum += (k - UseCut) * targetmid;
        System.out.print(sum);
    }

    /**
	 * 题目二：小码哥的机房布线 (二分答案 - 核心形态 B：求满足条件的最高上限)
	 * * 【题目描述】
	 * 小码哥最近在为新机房搭建网络环境。他手里有 N 根不同长度的原装网线。
	 * 为了保证各个机柜的布线统一且美观，他需要从这些原装网线中，
	 * 等长地裁剪出 K 根网线，用来连接服务器。
	 * * 核心规则：
	 * 1. 网线只能裁剪，绝对不能拼接（比如两根 1 米的不能拼成 2 米的）。
	 * 2. 裁剪后剩下的零头会直接废弃。
	 * 3. 裁剪出的网线长度必须是正整数。
	 * * 请问，小码哥能够裁剪出的网线的最大长度是多少？
	 * * 【输入格式】
	 * 第一行包含两个正整数 N（原装网线的数量）和 K（需要的网线数量），用空格隔开。
	 * 接下来 N 行，每行包含一个正整数 L_i，表示第 i 根原装网线的长度。
	 * * 【输出格式】
	 * 输出一个正整数，表示能够裁剪出的最大网线长度。如果连长度为 1 的网线都凑不够 K 根，请输出 0。
	 * * 【数据范围】
	 * 1 <= N <= 100,000
	 * 1 <= K <= 100,000,000
	 * 1 <= L_i <= 1,000,000,000
	 * * 【样例输入】
	 * 4 11
	 * 802
	 * 743
	 * 457
	 * 539
	 * * 【样例输出】
	 * 200
	 */
    public static void solveNetworkCable() {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int K = input.nextInt();
        
        int[] Number = new int[N];
        int max = Integer.MIN_VALUE;
        
        for(int i = 0; i < N; i++) {
            Number[i] = input.nextInt();
            if(max < Number[i]) {
                max = Number[i];
            }
        }
        
        int left = 1; // 最小必须是1，防止除以0
        int right = max; 
        int targetMid = 0; 
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            long sumCount = 0; // 防溢出
            
            for(int i = 0; i < N; i++) {
                sumCount += (Number[i] / mid);
            }
            
            if(sumCount >= K) {
                targetMid = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.print(targetMid);
    }

    /**
	 * 题目三：伐木工人 (二分答案 - 核心形态 B：求满足条件的最高上限)
	 * 【重点】使用了 BufferedReader + StringTokenizer 解决海量数据读取时的内存溢出 (MLE)
	 * * 【题目描述】
	 * 伐木工人小码哥需要砍 M 米长的木材，但只被允许砍伐一排树（考虑到环保）。
	 * 小码哥的伐木机工作流程如下：设置一个高度参数 H（米），伐木机升起一个巨大的锯
	 * 片到高度 H，并锯掉所有树比 H 高的部分，就得到树木被锯下的部分。
	 * 请帮助小码哥找到伐木机锯片的最大整数高度 H，使得他能得到的木材至少为 M 米。
	 * 换句话说，如果再升高 1 米，他将得不到 M 米木材。
	 * * * 【输入格式】
	 * 第一行 2 个整数 N 和 M，N 表示树木的数量，M 表示需要的木材总长度；
	 * 第二行 N 个整数表示每棵树的高度。
	 * * * 【输出格式】
	 * 1 个整数，表示锯片的最高高度。
	 * * * 【样例输入】
	 * 4 7
	 * 20 15 10 17
	 * * * 【样例输出】
	 * 15
	 * * * 【数据范围与备注】
	 * 对于 100% 的测试数据，1 <= N <= 10^6，1 <= M <= 2 * 10^9
	 * 树的高度 < 10^9，所有树的高度总和 > M。
	 * 本题相关知识点：算法基础：二分|三分
	 */
    public static void solveLumberjack() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        long N = Long.parseLong(st.nextToken());
        long M = Long.parseLong(st.nextToken());
        
        int[] number = new int[(int)N];
        long max = 0;
        st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++) {
            number[i] = Integer.parseInt(st.nextToken());
            if(max < number[i]) {
                max = number[i];
            }
        }
        
        long left = 0;
        long right = max;
        long target = 0;
        
        while(left <= right) {
            long Mid = left + (right - left) / 2;
            long sum = 0;
            
            for(int i = 0; i < N; i++) {
                if(number[i] > Mid) {
                    sum += number[i] - Mid;
                }
            }
            
            if(sum >= M) { 
                target = Mid;
                left = Mid + 1;
            } else {
                right = Mid - 1;
            }
        }
        System.out.print(target);
    }

    /**
	 * 题目四：汤姆抓杰瑞 (二分答案结合滑动窗口 - 核心形态 B：求满足条件的最大数量)
	 * 【重点题型】一维坐标系上的距离贪心问题
	 * * 【题目描述】
	 * 某一天，老鼠杰瑞抓住了一个机会，成功的到达了冰箱的附近，正当杰瑞打开冰箱门，
	 * 想要享受美味的奶酪的时候，没想到冰箱里的奶酪太多了，奶酪洒了一地。汤姆猫听到
	 * 了这个动静，正在火速赶往冰箱想要抓住杰瑞。杰瑞凭借与汤姆多年对抗的经历，仅凭
	 * 借汤姆的脚步声便能推断汤姆还有多久抵达，现在，杰瑞并不怕汤姆，但汤姆抵达后必
	 * 然影响它吃奶酪。于是杰瑞想要知道，在汤姆到达前，最多能吃到多少奶酪。
	 * * 现在已知杰瑞与所有奶酪刚好排成一条直线，用坐标 x_i 记录每个奶酪的位置，杰瑞一开
	 * 始的坐标为 0，移动一个单位距离需要一个单位时间。
	 * 由于杰瑞能一口吃下一整块奶酪，因此吃奶酪的过程并不会花任何时间。
	 * * * 【输入格式】
	 * 第一行两个正整数 t, n，表示汤姆抵达需要的时间和奶酪的数量；
	 * 第二行 n 个整数，表示奶酪的坐标 x_i。
	 * * * 【输出格式】
	 * 一个整数，表示杰瑞最多能吃到的奶酪数量。
	 * * * 【样例输入】
	 * 30 16
	 * 8 5 18 2 11 0 7 -14 -5 17 -11 15 -6 -16 10 1
	 * * * 【样例输出】
	 * 13
	 * * * 【数据范围与备注】
	 * 1 <= n <= 10^5, 1 <= t <= 10^8, -10^8 <= x_i <= 10^8
	 * 同一坐标可以有多块奶酪。
	 */
    public static void solveTomAndJerry() {
        Scanner input = new Scanner(System.in);
        long t = input.nextLong(); // 时间，可能很大
        int n = input.nextInt(); // 奶酪个数
        
        long[] Number = new long[n];
        for(int i = 0; i < n; i++) {
            Number[i] = input.nextLong();
        }
        
        // 【核心前置步骤】在直线上吃东西，必须先排序，保证吃的永远是连续的一段
        Arrays.sort(Number);
        
        int left = 1; // 【防御机制】从1开始尝试，利用 targetMid = 0 兜底，避免数组越界
        int right = n;
        int targetMid = 0;
        
        while(left <= right) {
            int Mid = left + (right - left) / 2;
            long CostMin = Long.MAX_VALUE; // 防溢出
            
            // 滑动窗口：框出所有长度为 Mid 的连续奶酪区间
            for(int i = 0; i + Mid - 1 < n; i++) {
                long leftPoint = Number[i];
                long rightPoint = Number[i + Mid - 1];
                
                // 【核心推导公式】计算吃完区间内所有奶酪的最短时间
                long Cost = Math.min(Math.abs(leftPoint), Math.abs(rightPoint)) + (rightPoint - leftPoint);
                
                if(CostMin > Cost) {
                    CostMin = Cost;
                }
            }
            
            if(CostMin <= t) {
                targetMid = Mid;
                left = Mid + 1;
            } else {
                right = Mid - 1;
            }
        }
        System.out.print(targetMid);
    }
}
