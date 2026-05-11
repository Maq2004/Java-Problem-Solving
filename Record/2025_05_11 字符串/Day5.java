// 2026-05-11
import java.io.*;
import java.util.*;

public class Day5 {

	public static void main(String[] args) throws IOException {
		/*
		 * 统计一个字符串中大写字符的个数
		 
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		char[] Charline = input.readLine().toCharArray();
		int i = 0;
		int[] CharNumber = new int[26];
		
		while( i<Charline.length ) {
			
			CharNumber[Charline[i] - 'A'] ++;
			i++;
			
		}
		int max = 0;
		i = 0;
		
		while(i < 26) {
			
			if(  CharNumber[max] < CharNumber[i]) {
				
				max = i;
			}
			
			i++;
		}
		
		System.out.print((char)(max+'A'));
		
		*/
		
		/*
		 * 判断
		 * 一个字符串长度是否大于6
		 * 是否同时包括了大小写字母和数字
		 * 数字和数字之间不能相连 
		 * 都满足输出True ， 不满足输出False
		 * 
	
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		char[] line = input.readLine().toCharArray();
		
		boolean flag = false; // 判断是否大于6  大于6 则为true
		boolean flag1 = false; // 判断是否同时包括了大写字母小写字母和数字 同时包括则为true
		
		boolean flag2 = true; // 判断数字之间是否相连 没有相连则为true
		
		
		flag = line.length > 6 ? true:false;
		int i = 0;
		int[] number = new int[3];
		
		while( i < line.length  ) {
			
			if( line[i] >='A' && line[i] <='Z' ) {
				number[0] ++;
				
			}
			
			if( line[i] >='a' && line[i] <='z' ) {
				number[1] ++;
				
			}
			
			if( line[i] >='0' && line[i] <='9' ) {
				
				// 判断当前字符的下一个字符是否为数字 ， 如果是数字说明相连了
				if( i!= line.length-1 && flag2 == true && line[i+1] >='0' && line[i+1] <='9' ) {
					
					flag2 = false;
					
				}
				
				number[2] ++;
				
			}
			
			if( flag1 ==false && number[0] > 0 && number[1] > 0 && number[2] >0) {
				flag1 = true;
				
			}
			i++;
			
		}
		
		
		if( flag == true && flag1 == true && flag2 == true ) {
			System.out.print("True");
		}else {
			System.out.print("False");
		}
		
		 */
		
		
		/*
         * 求将数个只有0和1的字符串拼接（可翻转）起来后，最大的前缀1或者后缀1的长度。
         * * 核心贪心思想：
         * 所有的字符串其实只有两种命运：
         * 1. 纯1串（SSR）：完美积木，中间没有0阻挡，不论多少个全都可以连在一起。
         * 2. 混合串（含0）：内部有0会阻断连接，所以只能挑出所有混合串中【一端最长】的那个1，接在SSR串的外边。
         * * 终极结果 = 所有纯1串的总长度 + 最大的一个混合串单边1长度。
         */

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());

        /* * ==========================================
         * 【QA 笔记：思维复盘】
         * Q1: 为什么之前的代码会错？
         * A: 我把“必要条件”当成了“充分条件”。纯1串一定满足左右两边1的数量相等，但我反推只要左右相等就是纯1串。
         * * Q2: 为什么检查不出错？
         * A: 大脑发生了“启发式偷懒”。被左右指针完全对称的假象欺骗，脑子里只去验证了 "111"，
         * 却完全忽略了去寻找 "能满足条件，但违背初衷" 的反例（如 "11011"）。
         * * Q3: 核心判断标准究竟是什么？
         * A: "纯1串" 的唯一绝对等价物理意义就是：数出来连续 1 的个数 == 字符串的总长度。
         * ==========================================
         */

        // --- 正确代码实现（直球对决思维 / 拒绝巧合） ---
        int maxMixedEdge = 0; // 记录混合字符串中，单边最长的1
        int sumPureOnes = 0;  // 记录所有纯1串的总长度

        for (int i = 0; i < n; i++) {
            String line = input.readLine();
            int len = line.length();
            
            int leftNumber = 0;
            int rightNumber = 0;
            
            // 统计前缀1的长度
            int left = 0;
            while (left < len && line.charAt(left) == '1') {
                leftNumber++;
                left++;
            }

            /* * 【关键改进】
             * 之前我想的是“找对称”：if (LeftNumber == RightNumber) ...
             * 现在我用的是“直球对决”：只要前缀1的长度等于总长度，它就绝对是纯1串。
             * 这样就不需要再去算一遍后缀了，直接累加！
             */
            if (leftNumber == len) {
                // 完美积木，直接累加
                sumPureOnes += len;
            } else {
                // 如果不是纯1串（包含了0），再去老老实实找后缀1
                int right = len - 1;
                while (right >= 0 && line.charAt(right) == '1') {
                    rightNumber++;
                    right--;
                }
                
                // 混合串只能贡献一端，所以取左右两端中最大的那个
                int currentMax = Math.max(leftNumber, rightNumber);
                
                // 维护全局最大的混合串边缘
                maxMixedEdge = Math.max(maxMixedEdge, currentMax);
            }
        }

        System.out.println(sumPureOnes + maxMixedEdge);

        /*
         * ==========================================
         * 【对照组：错误逻辑回顾】
         * * // 统计完前缀1和后缀1之后：
         * if (LeftNumber == RightNumber) { 
         * // 致命伤：把 "11011" 这种非纯1串也当成了纯1串直接累加，
         * // 导致它中间的 '0' 破坏了整个拼接链条。
         * SSRNumber += LeftNumber;
         * } else if (LeftNumber > RightNumber) {
         * // ... 繁琐且容易出错的嵌套比较
         * }
         * * ==========================================
         * 提醒未来自己：
         * 1. 代码回译法：写完核心 if 判断，把代码翻译成大白话问自己：“A 真的等价于 B 吗？”
         * 比如：“左右1相等，真的等价于全是1吗？”
         * 2. 极端防御库：以后碰到指针或两头比较的题，必须代入【轴对称镜像串】（如 "101", "11011"）进行测试。
         * 3. 拒绝巧合：能用最直白属性（如 长度==总长度）判断的，绝不用间接推导的属性来判断。
         */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
