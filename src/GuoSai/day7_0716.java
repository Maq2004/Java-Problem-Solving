package GuoSai;

import java.util.*;
import java.io.*;


public class day7_0716 {
	public static void main(String[] args) {
		
		/*
		Scanner input = new Scanner(System.in);
		int M = input.nextInt();
		M = Math.abs(M);
		int N = input.nextInt();
		N = Math.abs(N);
		
		
		int Num = N - M +1;
		int[] Count = new int[10];
		
		for(int i = 1 ; i<= Num ; i++ ,M++) {
			int MNum= M;
		
			while( MNum != 0  ) {
				int Number = MNum % 10;
				MNum /= 10;
				
				Count[Number]++;
			}
			
			
		}
		
		for(int i = 0; i< 10; i++) {
			
			System.out.print(  Count[i] + " ");
		}
		*/
		
		
		/*
		Scanner input = new Scanner(System.in);
		int n  =  input.nextInt();
		int m  =  input.nextInt();
		
		int[][] arr = new int[n+1][m+1];
		
		
		for(int i = 1 ; i<= n ; i++ ) {
		
			for(int j = 1 ; j<= m ; j++ ) {
				arr[i][j] = input.nextInt();
				
			}
		}
		int Power = arr[1][1] ;
		
		String line = input.next();
		int x = 1;
		int y = 1;
		
		boolean  Victory = true;
		
		
		for(int i = 0; i< line.length() ; i ++) {
			
			
			if( line.charAt(i) ==  'R' ) {
				y++;
			}else {
				x++;
			}
			
			
			if( arr[x][y] < Power ) {
				
				Power += arr[x][y];
				
			}else {
				Victory = false;
				break;
				
			}
			
			
		}
		
		if(Victory) {
			
			System.out.print("Victory"+ " " + Power);
		}else {
			System.out.print("Defeat");
		}
		*/
		
		/*
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		int sum = 0;
		
		for(int x = 1 ; x <= n ; x++) {
			sum += ( x - ( n/x ) );	
		}
		
		System.out.print(sum);
		*/
		
		
		
		
	}
}
