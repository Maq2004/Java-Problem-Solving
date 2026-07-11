package GuoSai;

import java.io.*;
import java.util.*;

public class day2_0711 {
    
    public static void main(String[] args) throws IOException {
        // 【控制台】想运行哪道题，就取消那道题的注释即可
        // solveLedgerQuery();       // 题目一：小码哥的账本查询 (基础前缀和)
        // solveServerStressTest();  // 题目二：小码哥的服务器集群压测 (差分+双重前缀和)
        // solveMathExam();          // 题目三：高数考试 (差分+滚动变量空间压缩)
        solveMaxAverage();           // 题目四：最大的平均值 (二分答案+前缀和状态维护)
    }

    /**
     * =====================================================================
     * 题目一：小码哥的账本查询 (前缀和 - 核心基础模板题)
     * =====================================================================
     * * 【题目描述】
     * 小码哥开了一家算法小卖部，他记录了过去 N 天里每天的营业额（可能是正数赚了，也可能是负数亏了）。
     * 现在年终盘点，老板也就是你，要对他进行 Q 次灵魂拷问：
     * 每次拷问给出两个日子 L 和 R，你需要立刻回答出从第 L 天到第 R 天（包含 L 和 R）的营业额总和。
     * 如果小码哥算得慢了，小卖部就要倒闭了。
     * * 【输入格式】
     * 第一行包含两个正整数 N 和 Q，用空格隔开，分别表示天数和询问次数。
     * 第二行包含 N 个整数，表示每天的营业额 a_i。
     * 接下来 Q 行，每行两个正整数 L 和 R，表示一次询问的起止天数。
     * * 【输出格式】
     * 输出共 Q 行，每行一个整数，对应每一次询问的营业额总和。
     * * 【数据范围与备注】
     * 1 <= N, Q <= 10^5
     * -10^4 <= a_i <= 10^4
     * 1 <= L <= R <= N
     */
    public static void solveLedgerQuery() throws IOException {
        BufferedReader Bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer Sz = new StringTokenizer(Bf.readLine());
        
        int N = Integer.parseInt(Sz.nextToken());
        int Q = Integer.parseInt(Sz.nextToken());
        
        int[] arr = new int[N+1];
        int[] SumArr = new int[N+1];
        int sum = 0;
        
        Sz = new StringTokenizer(Bf.readLine());
        for(int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(Sz.nextToken());
            sum += arr[i];
            SumArr[i] = sum;
        }
        
        StringBuilder Sb = new StringBuilder();
        
        for(int i = 0; i < Q; i++) {
            Sz = new StringTokenizer(Bf.readLine());
            int L = Integer.parseInt(Sz.nextToken());
            int R = Integer.parseInt(Sz.nextToken());
            
            Sb.append(SumArr[R] - SumArr[L-1]).append("\n");
        }
        
        System.out.print(Sb.toString());
    }

    /**
     * =====================================================================
     * 题目二：小码哥的服务器集群压测 (差分数组 + 双重前缀和 终极缝合怪)
     * =====================================================================
     * * 【题目描述】
     * 小码哥目前管理着机房里的 N 台服务器（编号从 1 到 N），初始时每台服务器的负载量均为 0。
     * 为了迎接即将到来的“双十一”大促，小码哥需要对服务器集群进行 M 次高并发压测。
     * 每次压测，他会选中一个区间 [L, R] 的服务器，并向这个区间内的每台服务器发送 V 的并发请求（即负载量增加 V）。
     * * 压测结束后，为了撰写性能报告，老板向小码哥发出了 Q 次查询指令。
     * 每次查询给出一段区间 [X, Y]，要求小码哥立刻汇报：经过所有压测后，区间 [X, Y] 内所有服务器的最终负载量总和是多少？
     * * 如果小码哥不能在 1 秒内给出所有查询的答案，就会被老板开除。请你拯救小码哥的职业生涯！
     * * 【数据范围与备注】
     * 1 <= N, M, Q <= 100,000
     * 1 <= L <= R <= N
     * 1 <= X <= Y <= N
     * 1 <= V <= 10,000
     */
    public static void solveServerStressTest() throws IOException {
        BufferedReader Sr = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer St = new StringTokenizer(Sr.readLine());
        
        int N = Integer.parseInt(St.nextToken());//  数组的大小
        int M = Integer.parseInt(St.nextToken());//  差分修改的次数
        int Q = Integer.parseInt(St.nextToken());//  前缀和查询的次数
        
        long[] arr = new long[N+2]; // 防越界、防溢出神装
        
        // 阶段一：求差分 (疯狂打标记)
        for(int i = 1; i <= M; i++) {
            St = new StringTokenizer(Sr.readLine());
            int L = Integer.parseInt(St.nextToken());
            int R = Integer.parseInt(St.nextToken());
            int V = Integer.parseInt(St.nextToken());
            
            arr[L] += V;
            arr[R+1] -= V;
        }
        
        // 阶段二：求差分的前缀和得到普通数组 (状态覆盖 In-place)
        for(int i = 1; i <= N; i++) {
            arr[i] = arr[i-1] + arr[i];
        }
        
        // 阶段三：求普通数组的前缀和 (再次状态覆盖，生成终极账本)
        for(int i = 1; i <= N; i++) {
            arr[i] = arr[i-1] + arr[i];
        }
        
        StringBuilder Sb = new StringBuilder();
        
        // 阶段四：根据终极前缀和来极速查询答案
        for(int i = 0; i < Q; i++) {
            St = new StringTokenizer(Sr.readLine());
            int L = Integer.parseInt(St.nextToken());
            int R = Integer.parseInt(St.nextToken());
            
            Sb.append(arr[R] - arr[L-1]).append("\n");
        }
        System.out.print(Sb.toString());
    }

    /**
     * =====================================================================
     * 题目三：高数考试 (MT2068)
     * 核心考点：差分数组 + 滚动变量空间压缩 + 极限快读 (StreamTokenizer)
     * =====================================================================
     * * 【题目描述】
     * 数学老师想捞同学一把，一遍遍地给某些区间同学增加分数。
     * 求更改分数后，全班的最低分。
     * * 【数据范围与备注】
     * 对于 100% 的数据，有 n <= 5 * 10^6，p <= n，学生初始成绩 <= 100，z <= 100。
     * * 考场避坑提示：
     * 1. n 达到五百万，不能开两个全长数组，必须将初始成绩直接转化为差分数组。
     * 2. 空间限制严苛，最后求前缀和还原并找最小值时，使用滚动变量。
     * 3. 必须使用 StreamTokenizer 应对海量 IO。
     */
    public static void solveMathExam() throws IOException {
        StreamTokenizer In = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        
        In.nextToken(); int n = (int)In.nval;
        In.nextToken(); int p = (int)In.nval; // 操作次数
        
        int[] diff = new int[n+2];
        int LastNumber = 0;
        
        // 边读边生成差分，极致省内存
        for(int i = 1; i <= n; i++) {
            In.nextToken();
            diff[i] = (int)In.nval - LastNumber;
            LastNumber = (int)In.nval; // 必须存当前的真正分数作为下一轮的“上一项”
        }
        
        for(int i = 0; i < p; i++) {
            In.nextToken(); int L = (int)In.nval;
            In.nextToken(); int R = (int)In.nval;
            In.nextToken(); int Z = (int)In.nval;
            
            diff[L] += Z;
            diff[R+1] -= Z;
        }
        
        int SumLastNumber = 0; // 滚动变量：相当于屏幕上的数字
        int min = Integer.MAX_VALUE;
        
        for(int i = 1; i <= n; i++) {
            SumLastNumber += diff[i]; // 向前滚动，计算当前同学的最终真实成绩
            if(min > SumLastNumber) {
                min = SumLastNumber;
            }
        }
        
        System.out.print(min);
    }

    /**
     * =====================================================================
     * 题目四：最大的平均值 (MT2058)
     * 核心考点：二分答案 (浮点数二分) + 前缀和状态维护 (O(N) 验证)
     * =====================================================================
     * * 【题目描述】
     * 给一个数组，长度为 n，找一个长度大于等于 m 的子区间，使这个区间的元素的平均值最大。
     * * 【数据范围与备注】
     * 1 <= n <= 100000
     * 1 <= m <= n
     * 0 <= a_i <= 10000000 (1e7)
     */
    public static void solveMaxAverage() throws IOException {
        StreamTokenizer In = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        
        In.nextToken(); int n = (int)In.nval;
        In.nextToken(); int m = (int)In.nval;
        
        double[] arr = new double[n+1];
        double max = Double.MIN_NORMAL;
        
        for(int i = 1; i <= n; i++) {
            In.nextToken(); 
            arr[i] = (double)In.nval;
            if(max < arr[i]) {
                max = arr[i];
            }
        }
        
        double left = 0;
        double right = max;
        
        // 浮点数二分，精度控制在 1e-5
        while(right - left > 1e-5) {
            double mid = left + (right - left) / 2.0;
            
            if (check(mid, n, m, arr)) {
                left = mid; // 能达到，说明平均值还能更大
            } else {
                right = mid; // 达不到，只能减小猜测值
            }
        }
        
        // 最后结果乘以 1000 并向下取整
        System.out.print((int)(right * 1000));
    }
    
    // 验证函数：在 O(N) 时间内判断是否存在长度 >=m 且 平均值 >=mid 的子区间
    public static boolean check(double mid, int n, int m, double[] arr) {
        double[] Sum = new double[n+1];
        
        // 降维打击：将所有数减去 mid，转为纯前缀和问题
        for(int i = 1; i <= n; i++) {
            Sum[i] = Sum[i-1] + (arr[i] - mid);
        }
        
        double min_val = Double.MAX_VALUE;
        
        for(int R = m; R <= n; R++) {
            // R 右移，Sum[R-m] 获得成为左边界的资格，打擂台更新历史最低点
            if(Sum[R - m] < min_val) {
                min_val = Sum[R - m];
            }
            
            // 当前前缀和减去历史最低点，如果 >= 0，说明存在合法的区间！
            if(Sum[R] - min_val >= 0) {
                return true;
            }
        }
        
        return false;
    }
}