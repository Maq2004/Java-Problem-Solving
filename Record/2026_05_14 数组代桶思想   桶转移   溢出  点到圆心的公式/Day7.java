import java.io.*;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        
        /* =================================================================
         * 阶段一：计算阶乘取模
         * =================================================================
         * 【题目】输入一个整数 n，计算 n 的阶乘 (1 * 2 * 3 * ... * n)，并将最终结果对 2026 取模输出。
         * * Q: 为什么可以每步取模，而不是最后算完再取模？
         * A: 根据同余定理，(A * B) % M = [(A % M) * (B % M)] % M。
         * 乘法过程中每步取模不会改变最终结果，且能死死压住 sum 的大小，防止 long 溢出。
         * * 历史代码：
         * Scanner input = new Scanner(System.in);
         * int n = input.nextInt();
         * long sum = 1;
         * for(int i = 1 ; i <= n ; i++) {
         * sum = (sum * i) % 2026; // 步步取模防溢出
         * }
         * System.out.print(sum);
         */

        
        /* =================================================================
         * 阶段二：坐标点被圆形覆盖数量统计
         * =================================================================
         * 【题目】输入 n 个目标点坐标。接着进行 m 次查询，每次给出 1 个圆心坐标和 1 个半径 r，问每次查询的圆形区域内覆盖了多少个目标点。
         * * Q: 为什么不用 HashMap 存坐标？
         * A: HashMap 的 Key 必须唯一。遇到 X 坐标相同的点，后面的 Y 值会把前面的覆盖掉，导致丢点。存坐标老老实实用二维数组或两个一维数组。
         * * Q: 为什么判断条件是距离的平方，而不是直接对比坐标的 UpX/DownX？
         * A: 坐标边界对比画出来的是“正方形包围盒”，会把角落外、圆内外的点算错。
         * 圆的判断要用欧几里得距离公式。为了避免 Math.sqrt 开根号带来的精度丢失和性能损耗，直接用距离的平方与半径的平方作比较。
         * * 历史代码：
         * int[] Xarry = new int[n];
         * int[] Yarry = new int[n];
         * // ... 存入数据 ...
         * for(int i = 0 ; i < m ; i++) {
         * int x = input.nextInt();
         * int y = input.nextInt();
         * int r = input.nextInt();
         * int count = 0;
         * for(int j = 0 ; j < Xarry.length ; j++) {
         * // 两点间距离公式的平方运算
         * if( ((Xarry[j]-x)*(Xarry[j]-x) + (Yarry[j]-y)*(Yarry[j]-y) ) <= r*r  ) {
         * count++;
         * }
         * }
         * System.out.println(count);
         * }
         */

        
        /* =================================================================
         * 阶段三：区间内出现频率最高的数字（数组代桶思想）
         * =================================================================
         * 【题目】给定一个长度为 n 的整数序列（数字范围 1 到 1000000），进行 q 次查询。每次查询给定一个区间 [l, r]，求该区间内出现次数最多的数字出现了多少次。
         * * Q: 为什么不用 HashMap 统计频率？
         * A: 题目提示数字范围最大只有 1000000。直接开一个大数组 `arrCount` 作为桶，利用物理下标访问，速度碾压 HashMap，防止超时。
         * * Q: 为什么查完之后不直接用 Arrays.fill 清空 count 数组？
         * A: 数组长度有 100 万，全量清空极慢。只需原路再遍历一次区间 [l, r]，把刚才加过的格子减回 0 即可，精准撤销状态。
         * * 历史代码：
         * int[] arrCount = new int[1000005];
         * // ... 读入数据 ...
         * for(int a = 0; a < q ; a++) {
         * int l = input.nextInt();
         * int r = input.nextInt();
         * int max = 0;
         * for(int j = l; j <= r ; j++ ) { 
         * arrCount[arr[j]]++;
         * if( max < arrCount[arr[j]]) {
         * max = arrCount[arr[j]];
         * }
         * }
         * System.out.println(max);
         * // 状态撤销
         * for(int i = l ; i <= r  ; i++) {
         * arrCount[arr[i]] = 0; // 或 arrCount[arr[i]]--
         * }
         * }
         */


        /* =================================================================
         * 阶段四：鱼肠剑斩铁锭（倒桶转移）
         * =================================================================
         * 【题目】有 n 块铁锭，每块初始硬度在 1 到 2000 之间。进行 q 次修改操作，每次操作给定一个除数 x，每块铁锭的硬度变为向下取整的 (原硬度 / x)。求每次操作结束后，所有铁锭的硬度总和。
         * * Q: 10^6 块铁，怎么避免 10^12 次计算超时？
         * A: 铁虽然多，但硬度最多只有 2000 种。将“遍历铁锭”转化为“遍历硬度桶”，计算量瞬间降至 2000 次。
         * * Q: 新老状态交替时，为什么用 `+=` 而不是 `=`？
         * A: int newVal = j / x; 不同的旧硬度被砍后，可能会变成相同的数值（例如 10/2=5，11/2=5）。必须用累加，否则会弄丢铁锭。
         * * Q: 计算公式为什么是 `硬度 / x` 而不是 `数量 / x`？
         * A: 结合物理意义，剑砍下去是让铁锭变软（硬度除以 x），铁锭的数量不会凭空消失。
         */

       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pt = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 初始桶：记录 1-2000 每个硬度有多少块铁
        int[] arr = new int[2005];
        st = new StringTokenizer(br.readLine());
        for(int i = 0 ; i < n; i++) {
            arr[Integer.parseInt(st.nextToken())]++;
        }
        
        int xCount = 0; // 记录有效挥剑次数
        
        for(int i = 0; i < q ; i++) {
            long sum = 0; // 必须用 long 防溢出
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            
            // 剪枝 1：力度为 1，硬度不变
            if( x == 1 ) {
                for( int j = 0; j <= 2000 ; j++) {
                    sum += ((long)arr[j] * j);
                }
                pt.println(sum);
                continue;
            }
            
            // 有效挥剑
            xCount++;
            
            // 剪枝 2：有效挥剑超 11 次，硬度 2000 也归 0 了
            if( xCount >= 11 ) {
                pt.println(0);
                continue;
            } else {
                // 倒桶：准备一张新桌子
                int[] newArr = new int[2005];
                
                for(int j = 0; j <= 2000 ; j++) {
                    if( arr[j] > 0 ) {
                        int newJ = j / x;
                        newArr[newJ] += arr[j]; // 铁块搬家，数量累加
                    }
                }
                
                arr = newArr; // 弃用旧桶，替换为新状态
                
                for(int a = 0; a <= 2000 ; a++) {
                    sum += ((long)newArr[a] * a);
                }
                
                pt.println(sum);
            }
        }
        
        // 循环结束后统一输出缓冲区内容，防止 I/O 阻塞超时
        pt.flush();
    }
}