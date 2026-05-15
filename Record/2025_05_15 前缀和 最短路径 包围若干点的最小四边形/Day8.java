// 2026-05-15
import java.io.*;
import java.util.*;


public class Day8 {

	public static void main(String[] args) {
		/*
		 * 一个只有1 和 0 的矩阵中，求有多少个 1的个数大于0的个数的子矩阵
		 
		
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int m = input.nextInt();
		
		int[][] P = new int[n+1][m+1];
		// 二维前缀和数组，P[i][j]含义是从矩阵最左上角[0][0]开始到右下角[i][j]的子矩阵的数字总和
		int[][] arr = new int[n+1][m+1];
		
		for(int i = 1 ; i <=n ; i++) {
			for(int j = 1 ; j <=m ; j++) {
				int a = input.nextInt();
				
				if( a == 1  ) {
					arr[i][j] = a;
				}else {
					arr[i][j] = -1;	
				}
				
				P[i][j] = P[i-1][j] + P[i][j-1] - P[i-1][j-1] + arr[i][j];
				
				
			}
		}
		int count = 0;
		for(int i = 1 ; i <= n ; i++) {
			for(int j = 1 ; j <= m ; j++) {
				for( int a = i ;  a <= n ; a ++ ) {
					for(int b = j ; b<= m ; b++) {
						
						if( P[a][b] - P[i-1][b] -P[a][j-1] + P[i-1][j-1] > 0 ) {
							count++;
							
						}
					}
				}
			}
		}
		
		
		
	System.out.print(count);
		*/
		/*
		 * 给若干个坐标，包围全部坐标的四条边平行于X Y轴的最小的矩形面积是？
		 * 
		 */
		
		/*
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		int[] Xarr = new int[n];
		int[] Yarr = new int[n];
		
		for(int i = 0; i < n ; i++) {
			Xarr[i] = input.nextInt();
			Yarr[i] = input.nextInt();
		}
		
		// 需要找出X最高的那个点 ， 需要找出X最低的那个点
		// 需要找出y最大的那个点，需要找出y最小的那个点
		
		
		
		int Xmin = 10000;
		int Xmax = 0;
		int Ymin = 10000;
		int Ymax = 0;
		
		for(int i = 0; i < n ; i ++) {
			if(Xmin > Xarr[i]) {
				Xmin = Xarr[i];
				
			}
			
			if( Xmax < Xarr[i] ) {
				Xmax = Xarr[i];
			}
			
			if(Ymin > Yarr[i]) {
				Ymin = Yarr[i];
				
			}
			
			if( Ymax < Yarr[i] ) {
				Ymax = Yarr[i];
			}
			
		}
		
		System.out.print((Xmax - Xmin) * (Ymax - Ymin));
		*/
		
		/*，若一个人当前在坐标(x,y)，
		 * 那么这个人可以花一秒的时间走到(x+1,y),(x−1,y)、(x,y+1)、(x,y−1)中的其中一个坐标。
		 * 已知大家可以同时移动，问最少需要多少秒，可以使得所有人聚集在一个房子内（最终汇合的房子一定是初始有人的房子）？
		 * 当然，当只有一座房子时，只需要0秒即可使得所有人聚集到一座房子。
		 * 
		 */
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		int[] Xarr = new int[n];
		int[] Yarr = new int[n];
		
		for(int i = 0; i < n ; i++) {
			Xarr[i] = input.nextInt();
			Yarr[i] = input.nextInt();
		}
		
		//假设第i位置是最佳位置
		// 当前的最小值是：
		int min = Integer.MAX_VALUE;
		for(int i = 0 ; i< n ; i++) {
			
		
			
			// 当i为最佳位置的时候，最多的秒数是:
			int max = Integer.MIN_VALUE;
			for(int j = 0 ; j < n ; j ++ ) {
			
				
				if(  max <  Math.abs(Xarr[j] - Xarr[i]) + Math.abs(Yarr[j] - Yarr[i])) {
					max = Math.abs(Xarr[j] - Xarr[i]) + Math.abs(Yarr[j] - Yarr[i]);
					
				}
				
			}
			
			if( min > max ) {
				min = max;
				
			}
		}
		System.out.print(min);
		
		
		/*
		int Xmin = Integer.MAX_VALUE;
		int Xmax = 0;
		int Ymin = Integer.MAX_VALUE;
		int Ymax = 0;
		
		for(int i = 0; i < n ; i ++) {
			if(Xmin > Xarr[i]) {
				Xmin = Xarr[i];
				
			}
			
			if( Xmax < Xarr[i] ) {
				Xmax = Xarr[i];
			}
			
			if(Ymin > Yarr[i]) {
				Ymin = Yarr[i];
				
			}
			
			if( Ymax < Yarr[i] ) {
				Ymax = Yarr[i];
			}
		}
		
	
		
		
		int XCountMin = 0; //离着中点最近的那个点的X坐标
		int YCountMin = 0; //离着中点最近的那个点的Y坐标
		int CountMin = Integer.MAX_VALUE;
		
		for(int i = 0; i< n; i++) {
		 if( CountMin > Math.abs((Xmax + Xmin) / 2 - Xarr[i]) + Math.abs((Ymax + Ymin) / 2 - Yarr[i])) {
			 
			 CountMin = Math.abs((Xmax + Xmin) / 2 - Xarr[i]) + Math.abs((Ymax + Ymin) / 2 - Yarr[i]);
			 XCountMin = Xarr[i];
			 YCountMin = Yarr[i];
			 
		 }
			
			
		} // 离中点最近的那个点
		
		int TotalMax = Integer.MIN_VALUE; // 
		
		for(int i = 0 ; i < n ; i++) {
			
			if(	TotalMax < Math.abs( Xarr[i] -XCountMin ) + Math.abs( Yarr[i] -YCountMin ) ) {
				TotalMax =Math.abs( Xarr[i] -XCountMin ) + Math.abs( Yarr[i] -YCountMin ) ;
			}
			
			
		}
		
		
		System.out.print(TotalMax);
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
