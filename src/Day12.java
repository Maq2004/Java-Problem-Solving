//2026-05-21
import java.io.*;
import java.util.*;

public class Day12 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		/*
		 * 有一个仅包含字符 0 和字符 1 的长度为 n 的字符串，
		 * 求有多少个子串满足该子串至少包含一个字符1 
	
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		
		String line = input.next();
		// 0 1 2 3 4 5 6 7 8 9
		
		// 1 0 0 0 0 0 0 0 0 0   10
		// 0 0 1 0 0 0 0 0 0 0   16
		// 0 1 0 0 0 0 0 0 0 0   18
		// 1 1 0 0 0 0 0 0 0 0   18
		// 1 1 1 0 0 0 0 0 0 0
		// 1 1 1 1 1 1 1 1 1 1 
		// 1 1 1 1 1 1 1 1 1 0
		// 1 1 1 1 1 1 1 1 0 0
		int count = 0;

		
		for(int i = 0 ; i < n ; i++) {
			
			for(int j = i ; j< n ; j++) {
				
				if(line.charAt(j) == '1') {
					0 1 2
					1 1 1
					下标1 与下标2 组成的字符串 和下标2 与下标3组成的字符串虽然长的一摸一样，但是不被视为同一个子字符串。
					
					 
 					// 只要 j的位置是1 ， j 下标之后的所有的字符串就都有1 了
					
					count += line.length() - j;
					break;
					
				}
			}
			
		}
		System.out.print(count);
		*/
		
		/*
		 * 有一个正整数序列 a1 - an 求这些序列中有多少( i , j ) 1 <= i <= j <= n 满足 ai > aj。 算出的数量对100取模
		 * 
		 *  ai的取值范围是 1 - 100 ， 可以用数组下标来表示这些值
		 
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(input.readLine());
		StringTokenizer st = new StringTokenizer(input.readLine());
		int[] arr = new int[101];
		long sum = 0;
		
		for(int i = 0 ; i< n ; i++) {
			int NowNumber = Integer.parseInt(st.nextToken());
			arr[NowNumber]++;
			for(int j = NowNumber+1 ; j < 101 ; j++) {
				// j是从当前的数字大小向更大的数字遍历，这些更大的数早就已经存在了说明是 i 而NowNumber 是j
				sum =(sum+ arr[j])%100;
			}
			
		}
		System.out.print(sum%100);
		
		// 0 1 2 3 4 5 6 7 8 9
		
		// 6 8 9 5 2 1 3 4 7 8
		
		
		*/
		
		/*
		 * 有一个长度为n的序列，现在需要对序列中的数字重新赋值，对与第i个元素来说，新的数值就是ai在整个序列中是第几小的
		 * 第几小就是小于当前元素的数字个数+1
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(input.readLine());
		StringTokenizer st = new StringTokenizer(input.readLine());
		int[] arr = new int[n];
		//int[] daxiao = new int[1000000001];
		
		for(int i = 0 ; i< n ; i++) {
			
			arr[i] = Integer.parseInt(st.nextToken());
			//daxiao[Integer.parseInt(st.nextToken())]++;
			
		}
		
		int[] newarr =arr.clone();
		Arrays.sort(newarr);
		
		for(int i = 0; i < n ; i++) {
			// 在一个有序数组中找一个数可以用二分查找
		// 找到arr[i]在newarr数组中第一次出现的位置，使用二分查找
			int count = 0;
			int left = 0;
			int right = n-1;
			
			while(left <= right) {
				int mid = left +(right-left)/2;
				
				if( newarr[mid] >= arr[i] ) {
					count = mid;
					right = mid -1;
				}else {
					left = mid+1;
				}
			}
			
			System.out.print(count+1 + " ");
		}
		
		 */


		
		
		HashMap<Integer, Integer> ma = new HashMap();
		
		
		ma.put(1, 1);
		ma.put(2, 2);
		ma.put(3, 3);
		ma.put(4, 4);
		
		
		for(Map.Entry<Integer, Integer> entry : ma.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			
		}
		
		
		
		
		
	
	
		
		
		
		
		
		
		
		
	}

}
