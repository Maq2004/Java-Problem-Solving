// 2026-05-22 
// 奇偶问题
// 最少步骤修改字符串问题（回文）
// 最小值之和问题
// 求序列乘积的末尾非零问题（未解决）
import java.io.*;
import java.util.*;

public class Day13 {
	public static void main(String[] args) {
		/*
		// 奇数+奇数 = 偶数
		// 偶数+偶数 = 偶数
		// 奇数+偶数 = 奇数
		
		//奇数*奇数 = 奇数
		//偶数*偶数 = 偶数
		//奇数*偶数 = 偶数
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int AJiOCount = 0; // 记录A中的奇数，如果A中的奇数是偶数,那么A序列的综合就是偶数
		int BJiOCount = 0; // 记录B中的奇数，如果B中的奇数是偶数,那么B序列的综合就是偶数
		
		int[] Aarr = new int[n];
		int[] Barr = new int[n];
		
		for(int i = 0 ; i < n ; i++) {
			Aarr[i] = input.nextInt();
			
			if( Aarr[i] % 2 != 0 ) {
				AJiOCount++;
			}
			
		}
		
		for(int i = 0 ; i < n ; i++) {
			Barr[i] = input.nextInt();
			
			if( Barr[i] % 2 != 0 ) {
				BJiOCount++;
			}
			
		}
		
		int A = 0;
		int B = 0;
		
		if(AJiOCount % 2 == 0) {
			A = 1;
		}
		if(BJiOCount % 2 == 0) {
			B = 1;
		}
		//  只要A序列和B序列之中有一个是偶数则整个结果就是偶数
		if(A +B >= 1 ) {
			System.out.print("even");
		}else {
			System.out.print("odd");			
		}
		*/
		
		
		
		
		
		/*
		 * 有一个由小写英文字母构成的字符串n ，每次步骤能修改其中一个字符， 现在需要用最少的步骤将其改为回文状态，即从左向右读取和从右向左读取的一致。
		 * 
		
		
		
		Scanner input = new Scanner(System.in);
		String line = input.next();
		
		int len  = 0;
		int right = line.length()-1;
		
		int count = 0;
		
		while(len <= right && len < line.length() && right >=0) {
			if(line.charAt(len) != line.charAt(right)) {
				count ++;
			}
			
			len++;
			right--;
			
			
		};
		System.out.print(count);
		 */
		
		/*
		 * 有一个整数序列a1 - an ， 求出整个序列中所有子序列的最小值并相加输出
	
		
		
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		int[] arr = new int[n];
		
		for(int i  = 0 ; i< n ; i++) {
			arr[i] = input.nextInt();
			
		}
		
		
		int min = Integer.MAX_VALUE;
		long minCount = 0;
		
		for(int i = 0 ; i< n ; i++) {
			min = Integer.MAX_VALUE; // 如果1下标是整个序列中的最小值，那么从1下标开始到n下标结尾的子序列都没有问题，最小值就是1
									// 但是从2下标开始到n下标结束的子序列来说，下标1不是其中的值，但是它却最小，所以需要重置最小值。
			
			for(int j = i ; j < n ; j++) {
				
				if(arr[j] <= min ) {
					// 只要多一个数就是一个新的序列，如果新加入的数 和当前记录的最小值相等，那么就要计入最小值综合之中
					min = arr[j];
					
					
				}
				minCount += min;
				
			}
			
		}
		System.out.print(minCount);
		
		
	*/
		/*
		 * 有一个整数序列 a1 - an; 求出a1 * a2 * ... * an  的在十进制表示下的最后一个非0的数位
		 */
		
		
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int[] arr = new int[n];
		int sum = 1;
		long mod = 1000000000;

		for(int i = 0 ; i < n ; i++) {
			sum  *= input.nextInt();
			while( sum % 10 == 0 ) {
				sum /= 10;
				
			}
			sum %= mod;
			
		}
		
		
		System.out.print(sum%10);
		
		
	}
}
