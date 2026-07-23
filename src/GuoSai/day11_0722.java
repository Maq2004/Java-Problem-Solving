package GuoSai;
import java.io.*;
import java.util.*;


public class day11_0722 {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		int T = input.nextInt();
		
		for(int i = 0; i<T ; i++) {
		
			int n = input.nextInt();
			int m = input.nextInt();
			int[] arrA = new int[n];
			int[] arrB = new int[n];
			int[] arrC = new int[n];
			
			for(int j = 0; j< n ; j++) {
				arrA[j] = input.nextInt();
			}
			for(int j = 0; j< n ; j++) {
				arrB[j] = input.nextInt();
			}
			for(int j = 0; j< n ; j++) {
				arrC[j] = input.nextInt();
			}
			
			
			PriorityQueue<long[]> minHeap = new PriorityQueue<>( (x,y) -> Long.compare(x[0], y[0]));
			
			Arrays.sort(arrA);
			Arrays.sort(arrB);
			Arrays.sort(arrC);
			
			int x = 0;
			int y = 0;
			
			for( int z = 0; z< n ; z++ ){
				long Sum = (long)arrA[z] *arrB[x] *arrC[y];
				minHeap.offer(new long[] { Sum ,z,x,y});
			}
			
			
			for( int f = 0; f< m ; f ++ ) {
				
				long[] arr = minHeap.poll();
				System.out.print(arr[0] + " ");
				
				
				//x+1
				if( arr[2]+1 < n ) {
					 long temp = (long)arrA[(int) arr[1]] * arrB[(int) (arr[2]+1)] * arrC[(int) arr[3]] ;
					 minHeap.offer(new long[] {temp  , arr[1] , arr[2]+1 , arr[3]}); 
						
				}
				
				 //y+1
				if( arr[2] == 0 && arr[3]+1 < n ) {
					long temp = (long) arrA[(int) arr[1]] * arrB[(int) arr[2]] * arrC[(int) (arr[3]+1)];
					
					 minHeap.offer(new long[] {  temp , arr[1] , arr[2] , arr[3]+1}); 
				}
				
		
				
			}
			System.out.println();
			
		}
		
		
	}
}
