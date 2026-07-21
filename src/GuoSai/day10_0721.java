package GuoSai;

import java.io.*;
import java.util.*;

public class day10_0721 {

    // === 【全局武器库】 ===
    // 本文件侧重局部状态维护，无跨题目共享的全局变量。

    public static void main(String[] args) {
        // ===================================================================== 
        // 主函数导航：解除以下注释即可运行对应题目的逻辑
        // ===================================================================== 
        
        // solveLongestValidParentheses(); // 题目1：最长有效括号 (断点法)
        // solveKthLargestElement();       // 题目2：寻找第K大元素
        // solveTopKSmallestSums();        // 题目3：MT2123 前K小数 (多路归并)
    }

    /**
     * ===================================================================== 
     * 题目1：最长有效括号 (断点法)
     * 核心考点：栈记录断点下标 / 逆向思维求极值
     * ===================================================================== 
     * * 【题目描述】
     * 计算字符串中最长的格式正确的括号子串的长度及数量。
     * * 【考场致命避坑提示】
     * 1. 核心转折：放弃直接找匹配的括号长度，转而用栈记录匹配不上的断点下标。 
     * 2. 空指针警告：利用 !Stack.isEmpty() && line.charAt(Stack.peek()) == '(' 避开空指针异常。 
     */
    private static void solveLongestValidParentheses() {
        Scanner input = new Scanner(System.in); 
        String line = input.next(); 
        
        Deque<Integer> Stack = new ArrayDeque(); 
        
        for(int i = 0;i <line.length() ; i++) { 
            // 如果是左括号是等待被匹配的对象，只能进入栈中 
            // 如果是右括号有两种情况，如果是栈首的是左括号则两者全部弹出，如果是空或者栈首同为右括号则匹配不上，此处就是断点 
            if( line.charAt(i) =='(' ) { 
                Stack.push(i); 
            } else {
                if(!Stack.isEmpty()  && line.charAt(Stack.peek()) == '(' ) { 
                    // 这里就是能匹配上，全部弹出 
                     Stack.pop(); 
                } else {
                    // 匹配不上，当作字符串中的断点 
                    Stack.push(i); 
                }
            }
        }
        
        // 到这里栈中的剩余的全部是断点，只要计算这些断点就能求取最长的字符串 
        if( Stack.isEmpty() ) { 
            // 如果栈中为空，说明整个字符串都是正常的 
            System.out.print(line.length()+" " + "1"); 
            return; 
        }
        
        // 接下来就是将栈中的下标相减吗？ 
        // 如何计算断点之间的长度？ 
        int Right = line.length(); 
        int maxLength = 0; 
        int maxCount = 0; 
        
        while( !Stack.isEmpty()) { 
            int left = Stack.pop(); 
            int length = Right - left -1; 
            
            if(length > maxLength) { 
                maxLength = length; 
                maxCount = 1; 
            } else if (length == maxLength) { 
                maxCount++; 
            }
            Right = left; 
        }
            
        // 特殊处理空栈情况，则最后一个数据就是长度 
        int length = Right; 
        
        if(length > maxLength) { 
            maxLength = length; 
            maxCount = 1; 
        } else if (length == maxLength) { 
            maxCount++; 
        }
            
        if( maxLength == 0 ) { 
            System.out.print("0" + " " + "1"); 
        } else {
            System.out.print(maxLength + " " + maxCount); 
        }
    }

    /**
     * ===================================================================== 
     * 题目2：寻找第K大元素
     * 核心考点：小顶堆维护 Top K / 极值筛选
     * ===================================================================== 
     * * 【题目描述】
     * 在给定无序数组中找出第 K 大的元素。
     * * 【考场致命避坑提示】
     * 1. 逆向思维：找“最大”反而用小顶堆。维护一个大小为 K 的小顶堆，自动淘汰较小的数字。
     * 2. 效率优势：时间复杂度直接降维，完胜冒泡排序。
     */
    private static void solveKthLargestElement() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); 
        Scanner input = new Scanner(System.in); 
        
        int n = input.nextInt(); 
        int[] nums = new int[n]; 
        
        for(int i= 0; i< n ; i++) { 
            nums[i] = input.nextInt(); 
        }
        int k = input.nextInt(); 
        
        for(int i = 0; i< n ; i++) { 
            minHeap.offer(nums[i]); 
            
            if( minHeap.size() > k ) { 
                minHeap.poll(); 
            }
        }
        System.out.print(minHeap.poll()); 
    }

    /**
     * ===================================================================== 
     * 题目3：MT2123 前K小数 (多路归并)
     * 核心考点：堆内存入数组包 / 双指针下沉
     * ===================================================================== 
     * * 【题目描述】
     * 两个长度为 n 的数列 a, b，通过 a_i + b_j 可以构造出 n*n 个数，求构造出的数中前 k 小的数。
     * * 【考场致命避坑提示】
     * 1. 必须先排序：保证 a[0] 和 b[0] 是最小的作为初始化基础。 
     * 2. 状态打包机制：堆里不能只存“和”，必须用 new int[]{和, a下标, b下标} 存入数组包，以便后续出队时能继续通过下标向后衍生。 
     */
    private static void solveTopKSmallestSums() {
        Scanner input = new Scanner(System.in); 
        int n = input.nextInt(); 
        int k = input.nextInt(); 
        
        int[] a = new int[n]; 
        for(int i = 0; i < n; i++) a[i] = input.nextInt(); 
        
        int[] b = new int[n]; 
        for(int i = 0; i < n; i++) b[i] = input.nextInt(); 
        
        // 1. 必须先排序，保证 a[0] 和 b[0] 是最小的 
        Arrays.sort(a); 
        Arrays.sort(b); 
        
        // 2. 建立小顶堆，里面存 int[] 数组。 
        // (x, y) -> x[0] - y[0] 的意思是：对比两个数组时，只看它们的第 0 个元素（也就是和）谁大谁小 
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((x, y) -> x[0] - y[0]); 
        
        // 3. a1+b1, a2+b1 ... an+b1 全部打包入堆 
        for(int i = 0; i < n; i++) { 
            // 数组格式：{当前的和, a的下标, b的下标} 
            minHeap.offer(new int[]{a[i] + b[0], i, 0}); 
        }
        
        // 4. 循环 K 次：每次弹出一个最小的，然后把它的 b 下标往后挪一位，重新拼一个包扔进去 
        for(int count = 0; count < k; count++) { 
            // 直接用 poll() 把最小的元素“拿”出来并存进 arr，一举两得 
            int[] arr = minHeap.poll();  
            System.out.print(arr[0] + " "); 

            if (arr[2] + 1 < n) { 
                minHeap.offer(new int[] {a[arr[1]] + b[arr[2] + 1], arr[1], arr[2] + 1}); 
            }
        }
    }
}