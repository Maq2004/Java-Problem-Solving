package GuoSai;

import java.io.*;
import java.util.*;

public class day5_0714 {

    // =====================================================================
    // 【全局武器库】针对不同类型的题目，配置不同的搜索/数据结构配件
    // =====================================================================
    
    // --- 动态连通图 (并查集 DSU) 专属配件 ---
    static int[] parent; // 记录每个节点的老大
    
    // --- 二维网格地图 (DFS) 专属配件 ---
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {1, -1, 0, 0}; // 方向盘 (上下左右)
    static int[] dy = {0, 0, 1, -1};
    static int Count = 0; // 连通块计数器


    public static void main(String[] args) throws IOException {
        // 【控制台】想运行哪道题，就取消那道题的注释即可
        // solveSongJiang();    // 题目一：宋江寻魂灵 (纸老虎公式题 + 极简组合数学)
        // solveJinWu();        // 题目二：西晋灭吴之战 (双指针 + 贪心状态维护)
        // solveDaGuanYuan();   // 题目三：社交大观园 (并查集动态连通性)
        
        solveLianYingMath(); // 题目四(解法A)：连营阵图 (欧拉公式找规律 O(N) 秒杀)
        // solveLianYingDFS();  // 题目四(解法B)：连营阵图 (二维网格 DFS 洪水填充)
    }

    /**
     * =====================================================================
     * 【题目一：组合数学与公式求值】
     * =====================================================================
     * 给定一个长度为 n 的序列 a_1 ~ a_n。
     * 请计算以下两部分得分的总和：
     * * 【第一部分：奇数配对】
     * 遍历数组中所有不重复的数字对 (a[i], a[j])，
     * 如果 (a[i] + a[j]) % 2 == 1，则本部分结果 +1。
     * * 加上 (+)
     * * 【第二部分：偶数配对】
     * 遍历数组中所有不重复的数字对 (a[i], a[j])，
     * 如果 (a[i] + a[j]) % 2 == 0，则本部分结果 +1。
     * 直接输入结果
     */
    public static void solveSongJiang() {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextLong()) return;
        
        long n = input.nextLong();
        
        // C(n, 2) 公式：n * (n - 1) / 2
        long result = n * (n - 1) / 2;
        
        System.out.println(result);
    }

    /**
     * =====================================================================
     * 题目二：西晋灭吴之战 (序列匹配)
     * 核心考点：双指针 + 状态维护 (布尔数组) + 贪心策略 (尽早匹配)
     * =====================================================================
     * * 【题目描述】
     * 在西晋军(长序列 N)中找两支互不重叠的吴军(子序列 M)。找到输出 Yes。
     * * 【考场致命避坑提示】
     * 1. 不能只不回头地遍历一次！因为两个子序列可能交错分布。
     * 2. 正确贪心：用外层 for 循环跑两次（找两支队伍），每次找完一支队伍后，
     * 子序列指针 v 必须重置为 0，且被选中的士兵用 ArrNFlag 标记拉黑，防止重复使用。
     */
    public static void solveJinWu() throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        
        st.nextToken(); int n = (int) st.nval;
        st.nextToken(); int m = (int) st.nval;
        
        int[] ArrN = new int[n];
        int[] ArrM = new int[m];
        boolean[] ArrNFlag = new boolean[n]; // 状态记录：是否被招募
        
        for (int i = 0; i < n; i++) {
            st.nextToken(); ArrN[i] = (int) st.nval;
        }
        for (int i = 0; i < m; i++) {
            st.nextToken(); ArrM[i] = (int) st.nval;
        }
        
        int MatchCount = 0;
        
        // 跑两遍，找两支队伍
        for (int i = 0; i < 2; i++) {
            int u = 0; // 指向西晋军
            int v = 0; // 指向吴军
            
            while (u < n) {
                // 如果人对上了，且这个兵还没被前面的队伍招募
                if (ArrN[u] == ArrM[v] && !ArrNFlag[u]) {
                    v++;
                    ArrNFlag[u] = true; // 标记招募
                }
                u++;
                
                // 找齐了一支队伍
                if (v == m) {
                    MatchCount++;
                    break; // 结束这支队伍的寻找，外层循环准备找下一支
                }
            }
        }
            
        if (MatchCount == 2) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    /**
     * =====================================================================
     * 【题目三：动态连通性查询】
     * =====================================================================
     * 有 n 个人，编号为 1 ~ n。接下来会发生 m 次操作，操作分为以下两种：
     * * 1. 输入格式 "1 x y"：
     * 表示编号为 x 和编号为 y 的人互相添加为好友（如果已经是好友则无影响）。
     * 2. 输入格式 "2 x y"：
     * 查询编号为 x 和编号为 y 的人，能否直接或间接地通过好友网络联系上。
     * 若能联系上，输出 "YES"；若不能，输出 "NO"。
     */
    public static void solveDaGuanYuan() throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        
        st.nextToken(); int n = (int) st.nval;
        st.nextToken(); int m = (int) st.nval;
        
        InitDSU(n); // 初始化并查集
        
        for (int i = 0; i < m; i++) {
            st.nextToken(); int type = (int) st.nval;
            st.nextToken(); int x = (int) st.nval;
            st.nextToken(); int y = (int) st.nval;
            
            if (type == 1) {
                Union(x, y); // 操作1：加好友
            } else if (type == 2) {
                // 操作2：查老大是不是同一个人
                if (Find(x) == Find(y)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }

    // --- 并查集三大核心组件 ---
    public static void InitDSU(int n) {
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i; // 一开始，每个人的老大都是自己
        }
    }
    
    public static int Find(int x) {
        if (parent[x] != x) {
            parent[x] = Find(parent[x]); // 路径压缩，直接挂在最高老大名下
        }
        return parent[x];
    }
    
    public static boolean Union(int x, int y) {
        int rootX = Find(x);
        int rootY = Find(y);
        if (rootX != rootY) {
            parent[rootX] = rootY; // 认大哥
            return true;
        }
        return false;
    }

    /**
     * =====================================================================
     * 【题目四：二维网格连通块数量计算】 解法A:欧拉联通公式
     * =====================================================================
     * 给定一个 2 行 n 列的二维方格矩阵，每个方格内仅包含字符 "0" 或 "1"。
     * 要求计算该矩阵中连通块的总数量。
     * * 两个格子属于同一个连通块，当且仅当它们直接或间接相连。
     * 直接相连必须同时满足以下两个条件：
     * 1. 两个格子在同一行或同一列上相邻。
     * 2. 两个格子内的字符完全相同（同为 "0" 或同为 "1"）。
     */
    public static void solveLianYingMath() {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) return;
        
        int n = input.nextInt();
        int total = 2 * n; // 初始假设各自为战
        
        map = new int[2][n];
        for (int i = 0; i < 2; i++) {
            String line = input.next();
            for (int j = 0; j < n; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }
        
        int One = 0; // 记录相邻且相同的边
        // 横向找兄弟
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (map[i][j] == map[i][j + 1]) {
                    One++;
                }
            }
        }
        // 纵向找兄弟
        for (int j = 0; j < n; j++) {
            if (map[0][j] == map[1][j]) {
                One++;
            }
        }
        
        int Two = 0; // 找 2x2 完全相同的方块，补回多减的边
        for (int j = 0; j < n - 1; j++) {
            if (map[0][j] == map[1][j] && map[0][j] == map[0][j + 1] && map[0][j + 1] == map[1][j + 1]) {
                Two++;
            }
        }
        
        System.out.println(total - One + Two);
    }

    /**
     * =====================================================================
     * 【题目四：二维网格连通块数量计算】 解法B：DFS
     * =====================================================================
     * 给定一个 2 行 n 列的二维方格矩阵，每个方格内仅包含字符 "0" 或 "1"。
     * 要求计算该矩阵中连通块的总数量。
     * * 两个格子属于同一个连通块，当且仅当它们直接或间接相连。
     * 直接相连必须同时满足以下两个条件：
     * 1. 两个格子在同一行或同一列上相邻。
     * 2. 两个格子内的字符完全相同（同为 "0" 或同为 "1"）。
     */
    public static void solveLianYingDFS() {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) return;
        
        int n = input.nextInt();
        map = new int[3][n + 1];
        visited = new boolean[3][n + 1];
        Count = 0;
        
        for (int i = 1; i <= 2; i++) {
            String line = input.next();
            for (int j = 1; j <= n; j++) {
                map[i][j] = line.charAt(j - 1) - '0';
            }
        }
        
        // 扫街寻找新大陆
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= n; j++) {
                if (!visited[i][j]) {
                    Count++; // 发现新连通块
                    DFS_Search(i, j, n, map[i][j]); // 放出油漆桶蔓延
                }
            }
        }
        System.out.println(Count);
    }

    public static void DFS_Search(int i, int j, int n, int LastData) {
        // 1. 越界保护及已访问拦截
        if (i < 1 || i > 2 || j < 1 || j > n || visited[i][j]) {
            return;
        }
        
        // 2. 阵营识别 (不是自己人，立刻停止蔓延)
        if (map[i][j] != LastData) {
            return;
        }
        
        // 3. 打上已访问标记 (超级关键，防止鬼打墙)
        visited[i][j] = true;
        
        // 4. 四向蔓延
        for (int a = 0; a < 4; a++) {
            int newI = i + dx[a];
            int newJ = j + dy[a];
            DFS_Search(newI, newJ, n, LastData);
        }
    }
}