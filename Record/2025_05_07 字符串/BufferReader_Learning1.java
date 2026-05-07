import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Day2_Logic_Simulation {
    public static void main(String[] args) throws IOException {

        /*
         * 【阶段一：MATIJI 拼词统计】
         * 题目：统计字符串 s 中能拼出多少组完整的 MATIJI 和 matiji。
         * 逻辑：分类统计字母出现次数，注意 'I' 和 'i' 需要两个，最后取各字母数量的最小值。
         * * Q: 为什么最后要除以 2？
         * A: 因为拼一个单词需要两个 'I/i'。统计总量后下取整，决定了拼词组数的上限。
         */
        /*
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        int[] MATIJI = new int[5];
        int[] matiji = new int[5];
        char[] arr1 = line.toCharArray();
        for(char c : arr1) {
            switch(c) {
                case 'm': matiji[0]++; break;
                case 'a': matiji[1]++; break;
                case 't': matiji[2]++; break;
                case 'i': matiji[3]++; break;
                case 'j': matiji[4]++; break;
                case 'M': MATIJI[0]++; break;
                case 'A': MATIJI[1]++; break;
                case 'T': MATIJI[2]++; break;
                case 'I': MATIJI[3]++; break;
                case 'J': MATIJI[4]++; break;
            }
        }
        matiji[3] /= 2; MATIJI[3] /= 2;
        System.out.print(IntStream.of(MATIJI).min().getAsInt() + " " + IntStream.of(matiji).min().getAsInt());
        */

        /*
         * 【阶段二：铭文 ASCII 求和】
         * 题目：计算字符串中所有字符的 ASCII 码之和。
         * 逻辑：遍历字符数组，累加每个 char 的整型值。
         * * Q: 字符相加时需要显式转换 (int) 吗？
         * A: 在进行加法运算时，char 会自动提升为 int，显式转换是为了代码逻辑更清晰。
         */
        /*
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        int sum = 0;
        for(char c : line.toCharArray()) sum += c;
        System.out.print(sum);
        */

        /*
         * 【阶段三：字符串删除指定头尾】
         * 题目：给出字符串 s 及整数 a, b，删除开头 a 个字符和结尾 b 个字符。
         * 逻辑：利用 for 循环控制起止索引，直接避开被删除区间。
         * * Q: 为什么不直接用 substring()？
         * A: 在大数据量下，手动遍历 char 数组或控制输出索引能更直观地理解内存边界，减少新对象创建。
         */
        /*
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        StringTokenizer st = new StringTokenizer(input.readLine());
        int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
        char[] char1 = line.toCharArray();
        for(int i = a ; i < char1.length - b; i++) System.out.print(char1[i]);
        */

        /*
         * 【阶段四：不重复组合修改代价（Day 2 最终版）】
         * 题目：求 n 个长度为 m 的字符串中，所有不重复组合的修改代价总和。
         * 逻辑：针对每一位，利用组合数公式：贡献 = 总对数 - 相同字符对数。
         * * Q: 为什么计算 sum 时要用 long？
         * A: 当 n=2*10^5 时，n*(n-1)/2 会超过 int 的范围（2*10^9），必须使用 long 防止溢出。
         * * Q: 这种做法和暴力 O(n^2 * m) 相比优势在哪？
         * A: 暴力对比会超时。通过计数统计（O(n * m)），将每一位的贡献独立计算，复杂度大幅降低。
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line1 = input.readLine();
        if (line1 == null) return;
        StringTokenizer st = new StringTokenizer(line1);
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        String[] lines = new String[n];
        for(int i = 0; i < n; i++) lines[i] = input.readLine();
        
        long totalSum = 0;
        for(int i = 0; i < m; i++) {
            int[] counts = new int[26];
            for(int j = 0; j < n; j++) {
                counts[lines[j].charAt(i) - 'a']++;
            }
            
            long totalPairs = (long) n * (n - 1) / 2;
            long samePairs = 0;
            for(int count : counts) {
                if(count > 1) {
                    samePairs += (long) count * (count - 1) / 2;
                }
            }
            totalSum += (totalPairs - samePairs);
        }
        System.out.print(totalSum);
    }
}