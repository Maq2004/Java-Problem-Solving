import java.io.*;
import java.util.*;

public class JingSai {
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        /* =========================================================
         * 题目一：将一个三位数的数字逆序输出
         * 正确
         * ========================================================= */
        /*
        int n = input.nextInt();
        
        for (int i = 0; i < 3; i++) {
            System.out.print(n % 10);
            n /= 10;
        }
        */
        
        /* =========================================================
         * 题目二：字符串前缀匹配计数
         * 有一个字符串S，有一个字符串T，在T字符串的长度内，如果S字符串的前缀相等，则该字符串计数+1。
         * 现在有n个S这样的字符串，m个T这样的字符串，将每个T字符串与所有的S字符串相比较，输出所有S字符串各自的计数。
         * 正确
         * ========================================================= */
        /*
        int n = input.nextInt();
        int m = input.nextInt();
        
        String[] S = new String[n];
        String[] T = new String[m];
        int[] SNumber = new int[n];
        
        for (int i = 0; i < n; i++) {
            S[i] = input.next();
        }
        
        for (int i = 0; i < m; i++) {
            T[i] = input.next();
        }
        
        StringBuilder Sb = new StringBuilder();
        for (int j = 0; j < n; j++) {
            for (int a = 0; a < m; a++) {
                Sb = new StringBuilder();

                for (int i = 0; i < T[a].length() && i < S[j].length(); i++) {
                    Sb.append(S[j].charAt(i));
                }
                
                if (Sb.toString().equals(T[a])) {
                    SNumber[j]++;
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            System.out.print(SNumber[i] + " ");
        }
        */
        
        /* =========================================================
         * 题目三：区间内最长严格上升子序列长度是否大于3
         * 有一个长度为n的正整数序列a1 - an，有q次给出一个区间[l,r]，问这段区间内的最长严格上升子序列的长度是否大于3？
         * 错误
         * ========================================================= */
        int n = input.nextInt();
        int q = input.nextInt();
        int[] arr = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            arr[i] = input.nextInt();
        }
        
        int[] Nuu = new int[3];                
        for (int i = 0; i < q; i++) {
            int len = input.nextInt();
            int right = input.nextInt();
            
            int index = 1;
            for (int a = len; a <= right; a++) {
                Nuu = new int[3];
                index = 1;
                Nuu[0] = arr[a];    
                
                for (int b = a; b <= right; b++) {
                    if (arr[b] > Nuu[index - 1]) {
                        Nuu[index] = arr[b];
                        index++;
                    }
                    
                    if (index == 3) {
                        break;
                    }
                }
                
                if (index == 3) {
                    break;
                }
            }
            
            if (index >= 3) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
        
        /* =========================================================
         * 题目四：计算大于平均数的正整数个数
         * 有n个正整数，求出他们的平均值，再计算有多少正整数大于他们的平均数
         * 正确
         * ========================================================= */
        /*
        int n = input.nextInt();
        int[] arr = new int[n];
        long sum = 0;
        
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
            sum += arr[i];
        }
        
        sum /= n;
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            if (arr[i] > sum) {
                count++;
            }
        }
        
        System.out.print(count);
        */
        
        /* =========================================================
         * 题目五：连续子段的数组种类之和
         * 有n个正整数，m次给你一个整数L，问你长度为L的连续子段的数组种类之和是？
         * 正确但是超时
         * ========================================================= */
        /*
        int n = input.nextInt();
        int m = input.nextInt();
        HashSet<Integer> Set = new HashSet();
        int[] arr = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            arr[i] = input.nextInt();
        }
        
        for (int i = 0; i < m; i++) {
            int l = input.nextInt();
            int count = 0;
            
            for (int u = 1; u <= n - l + 1; u++) {
                Set.clear();
                
                for (int v = u; v < u + l; v++) {
                    Set.add(arr[v]);
                }
                
                Object[] newarr = Set.toArray();
                count += newarr.length;
            }
            
            System.out.println(count);
        }
        */
        

    }
}