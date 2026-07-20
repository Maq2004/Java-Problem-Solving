package GuoSai;

import java.io.*;
import java.util.*;

public class day9_0720 {

	 // === 【全局武器库】 ===
    // 本文件侧重局部状态维护，无跨题目共享的全局变量。

    public static void main(String[] args) {
        // =====================================================================
        // 主函数导航：解除以下注释即可运行对应题目的逻辑
        // =====================================================================
        
        // solveStringRotation();     // 题目1：旋转字符串判断
        // solveSingleNumber();       // 题目2：寻找单身狗元素
        // solveValidParentheses();   // 题目3：有效的括号
        // solveMinStack();           // 题目4：MT2113 栈的min (黄金)
        // solveStains();             // 题目5：MC0107 污渍 (白银)
    }

    /**
     * =====================================================================
     * 题目1：旋转字符串判断
     * 核心考点：字符串倍增拼接技巧 / 边界条件优先
     * =====================================================================
     * * 【题目描述】
     * 判断字符串 S2 是否能通过字符串 S1 循环移位（旋转）得到。
     * * 【考场致命避坑提示】
     * 1. 致命边界：必须优先判断 S1 和 S2 长度是否相等！长度不等时直接判定失败，否则 (S1+S1).contains(S2) 会导致较短的非旋转子串被误判。
     * 2. 空间换时间：(S1+S1) 包含了 S1 所有的旋转状态，直接调 API 匹配即可。
     */
    private static void solveStringRotation() {
        Scanner input = new Scanner(System.in);
        String s1 = input.next();
        String s2 = input.next();
        
        // 逻辑转折点：倍增原字符串
        String s3 = s1 + s1;

        // 边界判断先行，再进行内容校验
        if (s1.length() == s2.length() && s3.contains(s2)) {
            System.out.print("true");
        } else {
            System.out.print("false");
        }
    }

    /**
     * =====================================================================
     * 题目2：寻找单身狗元素
     * 核心考点：位运算异或律 (归零律与交换律) / O(1) 空间极值优化
     * =====================================================================
     * * 【题目描述】
     * 数组中除了一个元素只出现一次外，其他所有元素都成对出现，找出这个唯一元素。
     * * 【考场致命避坑提示】
     * 1. 运算律应用：A ^ A = 0，A ^ 0 = A。成对的元素异或后会相互抵消为 0。
     * 2. 数据类型警告：如果数组元素极大，累加可能溢出，但位运算不会产生进位溢出，非常安全。
     */
    private static void solveSingleNumber() {
        // 模拟输入数据
        int[] arr = {2, 3, 2, 4, 3}; 
        
        int singleNumber = 0;
        for (int i = 0; i < arr.length; i++) {
            // 状态转移：异或累加，相同元素会自动抵消
            singleNumber ^= arr[i];
        }
        System.out.print(singleNumber);
    }

    /**
     * =====================================================================
     * 题目3：有效的括号
     * 核心考点：栈的 LIFO 后进先出特性 / 符号配对消除
     * =====================================================================
     * * 【题目描述】
     * 判断由多种括号组成的字符串是否合法闭合。
     * * 【考场致命避坑提示】
     * 1. 空指针警告：在遇到右括号准备 peek() 或 pop() 之前，必须先判断 stack.isEmpty()，否则测试用例 "]" 会直接导致 NPE！
     * 2. 集合选用：推荐使用 ArrayDeque 代替老的 java.util.Stack。泛型需指定为 <Character> 避免强转。
     */
    private static void solveValidParentheses() {
        Scanner input = new Scanner(System.in);
        String line = input.next();
        boolean isValid = true;
        
        // 规范化：指定泛型，消除强转警告
        Deque<Character> stack = new ArrayDeque<>();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            // 遇到左括号，入栈等待匹配
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
                continue;
            }
            
            // 致命避坑：遇到右括号时，如果栈已空，说明右括号多余，直接判非
            if (stack.isEmpty()) {
                isValid = false;
                break;
            }
            
            // 查表判断：校验弹出的左括号是否匹配
            if (c == ')') {
                if (stack.peek() != '(') {
                    isValid = false;
                    break;
                }
                stack.pop();
            } else if (c == '}') {
                if (stack.peek() != '{') {
                    isValid = false;
                    break;
                }
                stack.pop();
            } else if (c == ']') {
                if (stack.peek() != '[') {
                    isValid = false;
                    break;
                }
                stack.pop();
            }
        }
        
        // 终局校验：不仅匹配过程不能出错，最后栈也必须被清空
        if (isValid && stack.isEmpty()) {
            System.out.print("true");
        } else {
            System.out.print("false");
        }
    }

    /**
     * =====================================================================
     * 题目4：MT2113 栈的min
     * 核心考点：双栈法维护历史状态 / 自动拆箱规避地址比较
     * =====================================================================
     * * 【题目描述】
     * 设计一个支持 push、pop、top 操作，并能在 O(1) 时间内检索到最小元素的栈。
     * * 【考场致命避坑提示】
     * 1. 历史状态回溯：最小值不能只用一个变量存，必须用辅助栈同步保存每个阶段的最小值，否则 pop 后无法找回次小值。
     * 2. Integer 比较陷阱：出栈对比时，利用 `int num1 = stack.pop();` 触发自动拆箱，避免大于 127 的 Integer 对象使用 `==` 比较地址导致错误。
     */
    private static void solveMinStack() {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) return;
        int n = input.nextInt();
        
        // 规范化：添加泛型钻石语法，统一驼峰命名
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> stackMin = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            int operation = input.nextInt();
            
            switch (operation) {
                case 1:
                    int num = input.nextInt();
                    stack.push(num);
                    // 状态转移：辅助栈为空，或者新元素小于等于当前最小值时，入辅助栈
                    if (stackMin.isEmpty() || stackMin.peek() >= num) {
                        stackMin.push(num);
                    }
                    break;
                case 2:
                    if (stack.isEmpty()) break;
                    // 利用自动拆箱，将 Integer 转为 int，安全使用 == 进行数值比较
                    int num1 = stack.pop();
                    if (num1 == stackMin.peek()) {
                        stackMin.pop();
                    }
                    break;
                case 3:
                    if (!stack.isEmpty()) System.out.println(stack.peek());
                    break;
                case 4:
                    if (!stackMin.isEmpty()) System.out.println(stackMin.peek());
                    break;
            }
        }
    }

    /**
     * =====================================================================
     * 题目5：MC0107 污渍 (白银)
     * 核心考点：二维坐标系几何交并集计算 / 容斥原理
     * =====================================================================
     * * 【题目描述】
     * 计算无限大方格纸上两滴墨水（正方形区域）污染的总格子数。
     * * 【考场致命避坑提示】
     * 1. 面积重叠去重：两个正方形可能会有重叠区域，总污染面积 = A面积 + B面积 - 交集面积（容斥原理）。
     * 2. 坐标边界细节：理清坐标系方向。重叠长宽（X 和 Y）必须同时大于 0 才存在有效交集面积，否则直接返回两正方形面积之和即可。
     */
    private static void solveStains() {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) return;
        
        // 读取第一个正方形的基准坐标 (a,b) 及其边长 u1
        int a = input.nextInt();
        int b = input.nextInt();
        int u1 = input.nextInt();
        
        // 读取第二个正方形的基准坐标 (c,d) 及其边长 u2
        int c = input.nextInt();
        int d = input.nextInt();
        int u2 = input.nextInt();
        
        // 逻辑转折点：分别计算 X 轴和 Y 轴方向上的投影交集长度
        // 交集长度 = 两个右边界(上边界)的较小值 - 两个左边界(下边界)的较大值
        int x = Math.min(a + u1, c + u2) - Math.max(a, c);
        int y = Math.min(b, d) - Math.max(b - u1, d - u2);
        
        // 状态转移：若长宽均有交集（大于0），则减去重叠部分；否则直接相加
        if (x > 0 && y > 0) {
            System.out.print(u1 * u1 + u2 * u2 - x * y);
        } else {
            System.out.print(u1 * u1 + u2 * u2);
        }
    }
}