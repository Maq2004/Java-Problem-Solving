package GuoSai;

import java.io.*;
import java.util.*;

public class day6_0715 {

    // =====================================================================
    // 【全局武器库】
    // =====================================================================
    // 本次刷题主要侧重于数学思维降维和数组状态预处理，较少使用全局状态变量，
    // 变量多封闭在各自的方法内部以防多次查询（q次）时发生状态污染。

    public static void main(String[] args) throws IOException {
        // 【控制台】想运行哪道题，就取消那道题的注释即可
        // solveSubarrayPopcount(); // 题目一：子数组和的 popcount (前缀和降维)
        // solveBigNumberModulo();  // 题目二：大数取模 (霍纳法则 + 同余定理)
        // solveMatrixFlip();       // 题目三：矩阵异或翻转 (数学容斥原理)
        solveTriplet();          // 题目四：寻找严格上升三元组 (前后缀极值预处理)
    }

    /**
     * =====================================================================
     * 题目一：子数组和的 popcount
     * 核心考点：前缀和优化 (将 O(N^3) 降维至 O(N^2))
     * =====================================================================
     * * 【题目描述】
     * 记录每日精进程度为 n 个数值，求所有可能的连续子数组的和的二进制中 1 的个数总和。
     * * 【考场致命避坑提示】
     * 1. 暴力写法的第三层循环（重新计算区间和）极易导致 TLE（超时）。
     * 2. 利用前缀和数组 SumArr，使得任意区间 [i, j] 的和可以通过 SumArr[j] - SumArr[i-1] 
     * 在 O(1) 时间内求出。
     * 3. 巧妙利用 Java 内置的高效位运算 Integer.bitCount() 替代手动循环转二进制。
     */
    public static void solveSubarrayPopcount() throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        st.nextToken();
        int n = (int) st.nval;
        int[] arr = new int[n + 1];
        int[] SumArr = new int[n + 1]; // 前缀和数组
        
        // 边读取边构建前缀和
        for (int i = 1; i <= n; i++) {
            st.nextToken();
            arr[i] = (int) st.nval;
            SumArr[i] = SumArr[i - 1] + arr[i];
        }

        int sumCount = 0;
        // O(N^2) 遍历所有可能的子数组区间
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                // O(1) 极速求出连续区间和
                int currentSum = SumArr[j] - SumArr[i - 1];
                sumCount += Integer.bitCount(currentSum);
            }
        }
        System.out.print(sumCount);
    }

    /**
     * =====================================================================
     * 题目二：大数取模
     * 核心考点：多项式展开 (霍纳法则) + 同余定理
     * =====================================================================
     * * 【题目描述】
     * 给定一个长度高达 10^6 的二进制字符串，求其转为十进制后对 (popcount(x) + 1) 取模的结果。
     * * 【考场致命避坑提示】
     * 1. 绝对不能试图将 10^6 位的二进制完整转成整数再求余，会直接引发内存溢出。
     * 2. 运用同余定理：每次乘法或加法后立刻取模，可以将中间变量 ans 永远压制在模数大小范围内。
     * 3. 代码公式：ans = (ans * Base + currentDigit) % M。此规律适用于任意进制转换求模！
     */
    public static void solveBigNumberModulo() {
        Scanner input = new Scanner(System.in);
        String line = input.next();
        
        int sum = 0; // 记录 1 的个数 (popcount)
        for (int i = 0; i < line.length(); i++) {
            sum += (line.charAt(i) - '0');
        }
        
        int mod = sum + 1; // 题目要求的模数
        long ans = 0;      // 使用 long 防止 ans * 2 溢出 int 范围
        
        // 边遍历、边解析、边取模，像滚雪球一样混合计算
        for (int i = 0; i < line.length(); i++) {
            int currentDigit = line.charAt(i) - '0';
            ans = (ans * 2 + currentDigit) % mod;
        }
        
        System.out.print(ans);
    }

    /**
     * =====================================================================
     * 题目三：矩阵异或翻转
     * 核心考点：剥离模拟动作，基于最终状态推导容斥方程
     * =====================================================================
     * * 【题目描述】
     * 一个 n*m 的全 0 矩阵，可任意次翻转某行或某列（异或 1）。问能否恰好呈现 c 个 1？
     * * 【考场致命避坑提示】
     * 1. 摒弃动态模拟操作！异或具有重复抵消性，最终结果只取决于“有多少行”和“有多少列”被翻了 1 次。
     * 2. 利用容斥原理推导最终 1 的总数公式：Total = u*m + v*n - 2*u*v（u为翻转行数，v为翻转列数）。
     * 3. 两层 for 循环枚举可能的 u 和 v 即可，将无限次的模拟问题降维成求解代数方程。
     */
    public static void solveMatrixFlip() {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        
        for (int i = 0; i < T; i++) {
            int n = input.nextInt();
            int m = input.nextInt();
            int c = input.nextInt();

            boolean result = false;
            
            // 枚举最终被翻转的行数 u 和 列数 v
            for (int u = 1; u <= n; u++) {
                for (int v = 1; v <= m; v++) {
                    // 核心容斥公式：扣除两次交叉点的重叠计算
                    if (u * m + v * n - 2 * (u * v) == c) {
                        result = true;
                        break;
                    }
                }
                if (result) break; // 找到一组解直接跳出
            }

            if (result) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

    /**
     * =====================================================================
     * 题目四：寻找严格上升三元组 (局部多次查询版)
     * 核心考点：空间换时间 (O(N) 预处理) + 严格对齐 0-based 下标
     * =====================================================================
     * * 【题目描述】
     * 给定 q 次查询，每次给出区间 [l, r]，问该区间内是否存在长度 >= 3 的严格上升子序列。
     * * 【考场致命避坑提示】
     * 1. 针对局部区间的查询，预处理绝不能用全局范围，否则会“越界借用”区间外的极大极小值导致误判。
     * 2. 为了消除脑力负担，避免计算偏差，强烈建议在读取 [l, r] 时立刻将其转为 0-based（全部减 1）。
     * 3. 逻辑核心：枚举中间点 j，只要满足 左侧最小值 < arr[j] < 右侧最大值，即可破案。
     */
    public static void solveTriplet() {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int q = input.nextInt();

        // 统一采用 0-based 规范
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }

        for (int i = 0; i < q; i++) {
            // 接收 1-based 查询，入口处立刻归一化为 0-based
            int l = input.nextInt() - 1;
            int r = input.nextInt() - 1;
            int len = r - l + 1;

            if (len < 3) {
                System.out.println("No");
                continue;
            }

            // 每次查询动态创建符合区间长度的预处理数组
            int[] leftMin = new int[len];
            int[] rightMax = new int[len];

            // 1. 左侧最小预处理 (维护递增区间的下限)
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < len; j++) {
                int actualIndex = l + j; // 映射回原数组真实下标
                if (min > arr[actualIndex]) {
                    min = arr[actualIndex];
                }
                leftMin[j] = min;
            }

            // 2. 右侧最大预处理 (倒序遍历，维护递增区间的上限)
            int max = Integer.MIN_VALUE;
            for (int j = len - 1; j >= 0; j--) {
                int actualIndex = l + j;
                if (max < arr[actualIndex]) {
                    max = arr[actualIndex];
                }
                rightMax[j] = max;
            }

            // 3. 上帝视角查表：剥离首尾，枚举每一个可能的中间数
            boolean found = false;
            for (int j = 1; j < len - 1; j++) {
                int index = l + j;
                // 如果当前元素比左侧历任最小值大，且比右侧历任最大值小，则凑齐三元组！
                if (leftMin[j - 1] < arr[index] && arr[index] < rightMax[j + 1]) {
                    found = true;
                    break;
                }
            }

            if (found) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }
}