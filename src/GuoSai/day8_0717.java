package GuoSai;
import java.io.*;
import java.util.*;
public class day8_0717 {

	public static void main(String[] args) {
		/*
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		

		int sum = 0;
		
		for(int i = n; i>= 1 ; i--) {
			
			sum += (i-1)*(i) /2;
			
		}
		
		System.out.print(sum);
		*/
		
		/*
		Scanner input = new Scanner(System.in);
		
		
		int T1 = input.nextInt();
		int Q1 = input.nextInt();// 时间段1的电量

		int T2 = input.nextInt();
		int Q2 = input.nextInt();// 时间段2的点亮
	
		if( Q2 > Q1 ) {
		
			
			System.out.print( ( (100-Q2)* (T2-T1)/ (Q2- Q1 ) )+T2 );
			
			
		}else {
			// 放电速率
			
			int Gradient = (Q1- Q2 )/ (T2-T1);
			
			System.out.print( (Q2 * (T2-T1)/ (Q1- Q2 ))+T2 );
			
			
		}
		
		*/
		
		/*
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		
		double flag = 1;
		
		double sum = 0;
		
		for(int i = 1 ;i<= n; i++) {
			
			sum += (Math.sqrt(i)*flag);
			flag *= -1;
			
		}
		String result = String.format("%.6f", sum);
		
		System.out.print(result);
		*/
		
		
		Scanner input = new Scanner(System.in);
		int a = input.nextInt();
		String S = String.valueOf(a);
		int b = S.length() - 1;
		String Apart = S.charAt(0) + "." + S.substring(1 , S.length());
		
		if( b == 0) {
			System.out.print(S);
			return;
			
		}
		
		while(Apart.endsWith("0")) {
			Apart = Apart.substring(0 , Apart.length() - 1);
		}
		
		if(Apart.endsWith("1")) {
			Apart = Apart.substring(0 , Apart.length() - 1);
		}
		
		System.out.print(Apart + "E" + b);
	}
}
