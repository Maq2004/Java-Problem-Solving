import java.io.*;
import java.util.*;

/**
 * 学习演进记录 - 码蹄杯备战 Day 1
 * 目标：Java 组字符串模拟、高效 IO、映射查找
 */
public class BufferReader_Learning {
    public static void main(String[] args) throws IOException {

        /*
         * 【阶段一：单词翻转】
         * 题目：输入一行字符串（中间有空格），将其中的单词顺序翻转输出。
         * 逻辑：利用 StringTokenizer 切分单词，List 存储后逆序遍历输出。
         * * Q: 为什么弃用 Scanner？
         * A: Scanner 内部解析逻辑复杂，处理 10^5 级别数据容易超时；BufferedReader 直接读取缓冲区，效率极高。
         * * Q: StringTokenizer 与 split() 的区别？
         * A: split() 基于正则且会一次性生成数组，内存消耗大；StringTokenizer 是流式读取，快且省内存。
         */
        /*
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        StringTokenizer st = new StringTokenizer(line);
        List<String> arraylist = new ArrayList();
        while(st.hasMoreTokens()) {
            arraylist.add(st.nextToken());
        }
        for(int i = arraylist.size() - 1 ; i>= 0 ; i-- ) {
            System.out.print(arraylist.get(i) + (i == 0 ? "" : " "));
        }
        */

        /*
         * 【阶段二：模拟按键】
         * 题目：模拟 27 个按键，a-z 为添加字符，'D' 为删除序列末尾字符（若为空则忽略）。输出最终序列，若为空输出 "!!!!"。
         * 逻辑：遍历字符数组，遇 'D' 则索引减一（逻辑删除），否则存入新数组并索引加一。
         * * Q: 为什么使用 char2Index-- 而不是真的删除数组元素？
         * A: 真正的删除涉及数组移动，复杂度 O(n)；移动索引指针仅需 O(1)，这是典型的“物理存储、逻辑删除”思想。
         * * Q: 为什么要抛出 IOException？
         * A: readLine() 是检查型异常，必须声明或捕获。算法竞赛中常用 throws 保证代码精简。
         */
        /*
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = input.readLine();
        char[] char1 = line.toCharArray();
        char[] char2 = new char[char1.length];
        int char2Index = 0;
        for(int i = 0; i < char1.length; i++) {
            if(char1[i] != 'D') {
                char2[char2Index++] = char1[i];
            } else if(char2Index > 0) {
                char2Index--;
            }
        }
        if(char2Index == 0) System.out.print("!!!!");
        else {
            for(int i = 0; i < char2Index; i++) System.out.print(char2[i]);
        }
        */

        /*
         * 【阶段三：豫让破译密信（当前最终版）】
         * 题目：给定 n 对编号与暗语的映射。将密信中形如 "#编号" 的片段替换为对应暗语。
         * 逻辑：使用 HashMap 存储字典，StringBuilder 遍历拼接，识别 '#' 后提取连续数字进行查表替换。
         * * Q: 为什么使用 HashMap 而不是两个 List？
         * A: List 查找复杂度 O(n)，HashMap 查找接近 O(1)，在大规模数据下（n=2*10^5）能防止超时。
         * * Q: StringBuilder 在循环内外输出的区别？
         * A: 循环内频繁 toString() 和 System.out.print() 会触发大量 IO 和内存复制，导致 TLE；
         * 循环外一次性输出是高性能代码的核心准则。
         */

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        // 1. 读入字典数量（使用 Integer.parseInt 规避 read() 的字符陷阱）
        String nStr = input.readLine();
        if (nStr == null) return;
        int num = Integer.parseInt(nStr.trim());
        
        // 2. 建立映射字典
        Map<String, String> dict = new HashMap<>();
        for (int i = 0; i < num; i++) {
            StringTokenizer st = new StringTokenizer(input.readLine());
            String p = st.nextToken();
            String m = st.nextToken();
            dict.put(p, m);
        }
        
        // 3. 密信解析
        String secret = input.readLine();
        if (secret == null) return;
        
        StringBuilder result = new StringBuilder();
        int i = 0;
        int len = secret.length();
        
        while (i < len) {
            char a = secret.charAt(i);
            if (a == '#') {
                i++; // 跳过 '#'
                StringBuilder code = new StringBuilder();
                // 提取 # 后面的数字
                while (i < len && Character.isDigit(secret.charAt(i))) {
                    code.append(secret.charAt(i));
                    i++;
                }
                // 查表替换
                String word = dict.get(code.toString());
                if (word != null) result.append(word);
            } else {
                result.append(a);
                i++;
            }
        }
        
        // 4. 输出
        System.out.println(result.toString());
    }
}