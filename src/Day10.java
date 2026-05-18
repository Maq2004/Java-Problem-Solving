//2026-05-18
/*
 * 二维矩阵转置
 */
import java.util.*;
import java.io.*;

public class Day10 {
	public static void main(String[] args) throws IOException {
		
		Scanner input2 = new Scanner(System.in);
		
		int n =input2.nextInt(); // 画布的大小
		int m =input2.nextInt();; // 颜料和的大小
		int q =input2.nextInt();;// 下笔的次数
		
		int[][] Nint = new int[n+1][n+1];
		
		char[][] Mchar = new char[m+1][m+1];
	
		
		for(int i = 1 ; i<= m ; i++) {
			String lineString = input2.next();
			
			for(int j = 1 ; j<= m ; j++) {
				Mchar[i][j] = lineString.charAt(j-1);
			}
		}
		
	
		
		
		

		for(int a = 0 ; a < q ; a++) {
			
			int number = input2.nextInt();
			
			if( number == 1) {
				/*
				 * 1 2 3
				 * 4 5 6
				 * 7 8 9
				 * 
				 * 		1 2 3
				 * 
				 * 1	1 4 7
				 * 2	2 5 8
				 * 3	3 6 9
				 * 
				 * 
				 * 
				 * 
				*/
				char temp = 0;
				
				for(int i = 1 ; i<= m ; i++) {
					for(int j = i ; j<= m ; j++) {
					
						 temp = Mchar[i][j];
						Mchar[i][j] = Mchar[j][i];		
						Mchar[j][i] = temp;
					}
				}
				
				
				for(int i = 1 ; i<= m ; i++) {
					for(int j = 1 ; j<= m/2 ; j++) {
						 temp = Mchar[i][j];
						Mchar[i][j] = Mchar[i][m-j+1];		
						Mchar[i][m-j+1] = temp;
						
					}
				}
				
			}else {
				int x = input2.nextInt();
				int y = input2.nextInt();
				int  u = 1;
				/*
				 * 现在的难题是怎么让画图位置的xy和颜料盒的 1 1 对齐
				 * 
				 */
				for (int i = 1; i <= m; i++) {
			        for (int j = 1; j <= m; j++) {
			            // 只有当印章当前位置有颜料 '#' 时，才往白绢上盖色
			            if (Mchar[i][j] == '#') {
			                // 核心对齐公式：加上偏移量
			                Nint[x + i - 1][y + j - 1]++;
			            }
			        }
			    }
			
				
				
				
			}
			
		}
		
		
		for(int i = 1 ; i <= n  ; i++) {
			for(int j = 1 ; j <= n  ; j++) {
				System.out.print(Nint[i][j]);
			}
			System.out.println("");
		}

		
		
		
	}

}
