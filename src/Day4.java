// 2026-05-10
import java.io.*;
import java.util.*;
import java.math.*;
public class Day4 {
	public static void main(String[] args) throws IOException {
		
		/*
		 * 统计字符串中大写字符的个数
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		char[] line = input.readLine().toCharArray();
		
		int i = 0;
		int count = 0;
		
		while(line[i] != '.') {
			
			if( line[i] >= 'A'  && line[i] <= 'Z' ) {
				count ++;
				
			}
			i++;
		}
		
		System.out.print(count);
		 */
		
		
		/*
		 * 有一个字符串T ， 如果字符串S 删除一些字符后与T相等，则输出Lucky! 否则输出QAQ Unlucky!
		 * 
		 
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String S = input.readLine(); // 要被删减的字符串
		String T = input.readLine(); // 要与被删减的字符串比较的字符串
		
		int i = 0;
		int j = 0;
		
		StringBuilder Sb = new StringBuilder();
		
		while( j < T.length() && i<S.length() ) {
		
			if( S.charAt(i) == T.charAt(j) ) {
				Sb.append(S.charAt(i));
				j++;
				
			}
			i++;
			
		}
		
		
		if( Sb.toString().equals(T) ) {
			System.out.print("Lucky!");
			
		}else {
			
			System.out.print("QAQ Unlucky!");
			
		}

		*/
		
		/*
		 * 
		 * 统计字符串中出现次数最多的字符并输出
		 
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String line = input.readLine();
		
		int[] char1 = new int[26];
		
	
		for(int i = 0 ; i < line.length() ; i++) {
			
			char1[line.charAt(i) - 'a']++;
			
			
		}
		int i = 0;
		int max = 0;
		
		while(i < char1.length) {
			
			if( char1[max] < char1[i]  ) {
				max = i;
				
			}
			
			i++;
			
		}
		
		
		System.out.print((char)(max + 'a'));
		
		*/
		
		/*
		 * 有一个按照如下规则加密的字符串
		 * 				将字符串A中所有的长度为2的子串从左向右拼接得到加密串
		 * 将加密串解密输出
		 * 
		 */
		
		/*
		 * abcdefgh
		 * a b b c c d d e e f f  g   g h
		 * 0 1 2 3 4 5 6 7 8 9 10 11 12 13
		 * abcdefgh
		 */
		/*
	 	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	 	
	 	String line = input.readLine();
	 	
	 	int i = 0;
	 	StringBuilder Sb = new StringBuilder();
	 	
	 	Sb.append(line.charAt(0));
	 	
	 	while( i <  line.length() ){
	 		
	 		if( i % 2 == 0 ) {
	 			i++;
	 			continue;
	 			
	 		}
	 		Sb.append(line.charAt(i));
	 		
	 		i++;
	 	}
	 	System.out.print(Sb.toString());
		*/
		
		
		
		/*
		 * 寻找字符串的中某个字符的位置
		 * 
		
		
		
		
		Scanner input = new Scanner(System.in);
		
		String line = input.next();
		char[] A1 = input.next().toCharArray();
		int i = 0;
		boolean flag = false;
		
		while( i < line.length() ) {
			
			if( line.charAt(i)  == A1[0]) {
				flag = true;
				
				break;
			}
			
			i++;
		}
		
		
		if( flag ) {
			System.out.print(i+1);	
		}else {
			System.out.print(-1);
		}
		 */
		
		/*
		 * 求一个数字串中，不重复数字最多的一个子串
		 * 
		 *  1 2 5 4 8 9 6 3 2 5 5 5 6 2
		 * 
		 *  12548963 因为这个子串中所有的数字都和其他所有的数字不一样。 
		 *  
		 *  如何保证子串中的数字各不相同？
		 *   HashSet
		 *  
		 *  如何规定子串的长度？
		 *    使用滑动思想 ， 规定两个指针
		 * 
		 */
		
		Scanner input = new Scanner(System.in);
        
        int n = input.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        /* * ==========================================
         * 【QA 笔记：思维复盘】
         * Q1: 为什么代码会错？
         * A: 脑中默认 left 右移 = 元素消失，但代码里 left++ 不等于 set.remove()。
         * 程序只执行我写的，不执行我脑补的。
         * * Q2: 为什么检查不出错？
         * A: 我在检查“思路”而非“执行”。只看重复项删没删，没看窗口整体状态。
         * * Q3: 核心不变量是什么？
         * A: HashSet 内容 ≡ 窗口 [left, right] 覆盖的元素。
         * ==========================================
         */

        // --- 正确代码实现（传送带模式 / 状态维护思维） ---
        int left = 0;
        int max = 0;
        HashSet<Integer> windowSet = new HashSet<>();

        for (int right = 0; right < n; right++) {
            /* * 【关键改进】
             * 之前我想的是“警察抓人”：if (numbers[left] == numbers[right]) ...
             * 现在我用的是“清理区域”：只要右边进不来，左边就得一直拆，直到安全。
             */
            while (windowSet.contains(numbers[right])) {
                // 强制同步：left 指向谁，谁就得从集合里消失
                windowSet.remove(numbers[left]);
                left++; 
            }
            
            // 此时保证了：当前 numbers[right] 在集合中绝无仅有
            windowSet.add(numbers[right]);
            
            // 计算当前安全区长度
            max = Math.max(max, right - left + 1);
        }

        System.out.println(max);

        /*
         * ==========================================
         * 【对照组：错误逻辑回顾】
         * * while(NumberSet.contains(numbers[right])) {
         * if(numbers[left] == numbers[right]) {
         * NumberSet.remove(numbers[left]); // 误区：以为只删这一条就够了
         * left++;
         * } else {
         * left++; // 致命伤：只动了指针，没动集合，导致状态不同步！
         * }
         * }
         * ==========================================
         * 提醒未来自己：
         * 1. 局部思维遮挡整体逻辑：不要只盯着重复的那一个点。
         * 2. 状态维护：left 每动一格，对应的“环境清理”必须显式表达。
         * 3. 调试：逐行模拟代码，想法没问题的情况下，盯着的执行看，不要盯着想法看。
         */

        
		
	}

}
