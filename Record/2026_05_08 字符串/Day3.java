import java.util.*;
import java.io.*;

public class Day3 {
	public static void main(String[] args) throws IOException {

/*
 * 【第一题：元音计数】
 * 逻辑：通过 switch 穿透特性简化代码，统一处理 a, e, i, o, u 计数。
 * * Q: 为什么使用 switch 而不是多个 if？
 * A: switch 在处理固定常量匹配时效率更高，且利用 case 穿透（不写 break）可以将多个匹配条件归类到同一个逻辑块。
 * * Q: toCharArray() 有什么好处？
 * A: 将字符串转为字符数组后，可以通过索引直接访问元素，在循环中比 charAt() 更符合 C 语言风格且在某些 JVM 下效率微优。
 */
/*
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int count = 0;
		char[] lineChar = input.readLine().toCharArray();
		for(int i = 0; i < lineChar.length ; i++ ) {
			switch(lineChar[i]) {
				case 'a': case 'e': case 'i': case 'o': case 'u':
					count++; break;
				default: break;
			}
		}
		System.out.print(count);
*/

/*
 * 【第二题：子串翻转比对】
 * 逻辑：定位两个字符串首尾不一致的边界（Left, Right），翻转该区间后再校验是否全等。
 * * Q: 为什么不直接用 StringBuilder.reverse()？
 * A: 手动双指针交换（temp 变量交换）能精准控制翻转区间 [Left, Right]，更利于理解底层内存操作。
 * * Q: 如何处理“分段不连续”的错误情况？
 * A: 只允许翻转“一个”区间。逻辑上先找出左右最外层的不匹配点，强制翻转后如果仍不相等，说明不连续的不匹配点无法通过单次翻转修复。
 */
/*
		Scanner input = new Scanner(System.in);
		int num = input.nextInt();
		int m = 0;
		char[] YN = new char[num];
		while(m < num) {
			String S = input.next();
			String T = input.next();
			if (S.equals(T)) {
				YN[m] = 'Y';
			} else {
				char[] Schar = S.toCharArray();
				char[] Tchar = T.toCharArray();
				int count = Schar.length;
				int i = 0, j = count - 1;
				while(i < count && Schar[i] == Tchar[i]) i++;
				int Left = i;
				while(j > 0 && Schar[j] == Tchar[j]) j--;
				int Right = j;
				
				while(Left < Right) {
					char temp = Tchar[Right];
					Tchar[Right] = Tchar[Left];
					Tchar[Left] = temp;
					Left++; Right--;
				}
				YN[m] = Arrays.equals(Schar, Tchar) ? 'Y' : 'N';
			}
			m++;
		}
		for(char res : YN) System.out.println(res);
*/

/*
 * 【第三题：子串搜索计数】
 * 逻辑：滑动窗口思想。遍历主串，当发现首字母匹配时，截取固定长度子串进行比对。
 * * Q: 为什么循环条件是 line.length - 5？
 * A: 因为目标子串 "matiji" 长度为 6，如果当前索引 i 距离末尾不足 6 位，则不可能构成完整子串，防止越界。
 * * Q: StringBuilder 在此处的必要性？
 * A: 频繁拼接字符时，StringBuilder 避免了产生大量 String 临时对象，节省内存开销。
 */
		Scanner input = new Scanner(System.in);
		char[] line = input.next().toCharArray();
		int i = 0;
		int count = 0;
		
		while(i <= line.length - 6) { // 修正：应包含等于，确保末尾检查
			if(line[i] == 'm') {
				int j = 0;
				StringBuilder Sb = new StringBuilder();				
				while(j < 6) {
					Sb.append(line[i + j]);
					j++;
				}
				if ("matiji".equals(Sb.toString())) {
					count++;
				}
			}
			i++;
		}
		System.out.print(count);
	}
}