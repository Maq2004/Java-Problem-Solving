// 2026-05-19
/*
 * 统计不同数值的个数
 * 
 * 有一个数字串，接下来有q次修改，给三个整数 l r v 表示将l r 区间内的数字修改为v
		 * q次修改完成后输出当前数字串状态
		 * 
		 
		 
		 
		 
		 给定n个坐标 再给m次圆心和半径 ，
		 * 结果输出每个坐标被圆心圈中了多少次
		 * 
		 
		 
		 
		 
		 一个长度为 n 的整数序列 a1 ~ an。
		 *
		 * 现在，他想知道有多少个四元组 (q, w, e, r) 满足：
		 *
		 *      1 <= q < w < e < r <= n
		 *      且
		 *      a[q] < a[w] = a[e] > a[r]
		 *
		 * 这种模式表示：
		 * 两道美味度相同且较高的菜品出现时，
		 * 吴王僚的注意力被吸引，
		 * 是动手的良机。
		 *
		 * 请输出满足条件的四元组数量，
		 * 并对 10007 取模。
		 *
		 * -------------------------
		 * 输入格式：
		 *
		 * 第一行：
		 *      一个整数 n
		 *      (1 <= n <= 3000)
		 *
		 * 第二行：
		 *      n 个整数 a1 ~ an
		 *      (1 <= ai <= 1e9)
		 *
		 * -------------------------
		 * 输出格式：
		 *
		 * 输出一个整数，
		 * 表示满足条件的四元组数量。
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 有n个数将他们的高四位和低四位互换，输出这n个数的和
 */

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class Day11 {
	public static void main(String[] args) {
		/*
		 * 统计不同数值的个数
		 

		Scanner input = new Scanner (System.in);
		
		int n = input.nextInt();
		Set<Integer> NumberSet = new HashSet();
		
		for(int i = 0 ; i < n ; i++) {
			NumberSet.add(input.nextInt());
			
		}
		
		System.out.print(NumberSet.size());
		*/
		
		
		/*
		 * 有一个数字串，接下来有q次修改，给三个整数 l r v 表示将l r 区间内的数字修改为v
		 * q次修改完成后输出当前数字串状态
		
		
		
		Scanner input  = new Scanner(System.in);
		
		int n = input.nextInt();
		int q = input.nextInt();
		
		int[] arr = new int[n+1];
		
		for(int i = 1 ; i <= n ; i++) {
			arr[i] = input.nextInt();
			
		}
		
		for(int i = 0 ; i< q ; i++) {
		
			int l = input.nextInt();
			int r = input.nextInt();
			int v = input.nextInt();
			
			
			
			
			for(int j = l ; j <= r ; j++) {
				
				arr[j] = v;
				
				
			}
			
		}
		
		
		for(int j = 1 ; j <= n ; j++) {
			
			System.out.print(arr[j]+ " ");
			
		
		}
			 */
		/*
		 * 给定n个坐标 再给m次圆心和半径 ，
		 * 结果输出每个坐标被圆心圈中了多少次
		 
		
		
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		int m = input.nextInt();

		int[] Xarr = new int[n];
		int[] Yarr = new int[n];
		
		
		for(int i = 0 ; i< n ; i++) {
			
			int x = input.nextInt();
			int y = input.nextInt();
			
			Xarr[i] = x;
			Yarr[i] = y;
			
		}
		int[] IndexNumber = new int[n];
		
		for(int i  = 0 ;  i < m ; i++) {
			int x = input.nextInt();
			int y = input.nextInt();
			int r = input.nextInt();
			
			
			for(int j = 0 ; j < n ; j++ ) {
				
				if( (Xarr[j] - x)*(Xarr[j] - x)+(Yarr[j] - y)*(Yarr[j] - y) <= r*r  ) {
					IndexNumber[j]++;
				}
				
			}
			
			
		}
		
		
		for(int j = 0 ; j < n ; j++ ) {
			
			System.out.println(IndexNumber[j] + " ");
			
		}
		*/
		
		
		/*
		 * 公子光设宴邀请吴王僚，吴王虽心存疑虑但仍前来赴宴。
		 * 公子光早已布置武士埋伏在四周，并让专诸将短剑藏于烤鱼之中，
		 * 准备在宴席上发动刺杀。
		 *
		 * 为了评估动手的最佳时机，
		 * 公子光记录了宴会上每道菜的美味度，
		 * 形成一个长度为 n 的整数序列 a1 ~ an。
		 *
		 * 现在，他想知道有多少个四元组 (q, w, e, r) 满足：
		 *
		 *      1 <= q < w < e < r <= n
		 *      且
		 *      a[q] < a[w] = a[e] > a[r]
		 *
		 * 这种模式表示：
		 * 两道美味度相同且较高的菜品出现时，
		 * 吴王僚的注意力被吸引，
		 * 是动手的良机。
		 *
		 * 请输出满足条件的四元组数量，
		 * 并对 10007 取模。
		 *
		 * -------------------------
		 * 输入格式：
		 *
		 * 第一行：
		 *      一个整数 n
		 *      (1 <= n <= 3000)
		 *
		 * 第二行：
		 *      n 个整数 a1 ~ an
		 *      (1 <= ai <= 1e9)
		 *
		 * -------------------------
		 * 输出格式：
		 *
		 * 输出一个整数，
		 * 表示满足条件的四元组数量。
		
		
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		long[] NumberArr = new long[n];
		
		for(int i = 0 ; i < n ; i++) {
			NumberArr[i] = input.nextInt() % 10007;		
		}
		int[] left_smaller = new int[n]; // 记录i左边有多少个数小于它
		int[] right_smaller = new int[n] ; // 记录i右边有多少个数小于它
		
		
		for(int i = 0 ; i < n ; i++) {
		
			// 只遍历i的左边就是 小于i
			 
			for(int m =0 ; m< i ; m++ ) {
				if(NumberArr[m] < NumberArr[i]) {
					left_smaller[i]++;
				}
				
			}
			
			//只遍历i 的右边就是从i开始 到n 虽然从i开始会有一次指向i但是因为要找的是小于i的数据所以那一次不会被计入
			for(int m =i ; m< n ; m++ ) {
				if(NumberArr[m] < NumberArr[i]) {
					right_smaller[i]++;
				}
				
			}
		}
		
		//接下来寻找 两个数  a b 相等 
		//a在NumberArr中有个下标，这个下标左边小于a的数据存在了left_smailer[a下标]中，同理b右边小于b的数据存在了right_smailer[b下标]中，他俩相乘加入总次数中就可以了
		long total = 0;
		for(int i = 0 ; i < n ; i++) {
			/*
			 * 这样写有可能会去查找两次  加入下标1 和下标3相等，下标1 和下标三会匹配两次
			
			for(int j = i+1 ; j < n ; j++) {
				if( i != j && NumberArr[i] == NumberArr[j]) {
					
				total = (total%10007 + (long)(left_smaller[i] * right_smaller[j])%10007)%10007;
				
				}
			}
		}
		
		
		
		
		
		System.out.print(total);
		
		 */
		
		
		
		
		
		
		int total = 0;
		
		Scanner input = new Scanner(System.in);
		int m = input.nextInt();
		
		for(int i  = 0 ; i < m ; i++) {
			int n = input.nextInt();
			total += (n & 0x0f) << 4 | (n & 0xf0)>>4;
			
				
		}
		System.out.print(total);
		
		
		
		
		
		
	}

}
