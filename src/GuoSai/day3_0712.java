package GuoSai;

import java.io.*;
import java.util.*;

public class day3_0712 {
    
    // =====================================================================
    // 【全局武器库】针对不同类型的题目，配置不同的搜索配件
    // =====================================================================
    
    // --- 迷宫类 (隐式图) 专属配件 ---
    static int[][] maze = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };
    static boolean[][] visited2D; // 2D 记号笔：地点的名字是 (x,y)，所以是二维
    static int[] dx = {-1, 1, 0, 0}; // 方向盘
    static int[] dy = {0, 0, -1, 1};
    static int totalPaths = 0;    // 找所有路径时的计数器

    // --- 图论类 (显式图) 专属配件 ---
    static List<Integer>[] map;   // 邻接表大柜子：每个抽屉装一个 ArrayList 记事本
    static boolean[] visited1D;   // 1D 记号笔：地点的名字只有 ID (如 1 号站)，所以是一维
    static boolean canReach = false; // 是否能送达的全局标志


    public static void main(String[] args) throws IOException {
        // 【控制台】想运行哪道题，就取消那道题的注释即可
        // solveWorker();     // 题目一：打工人 (前缀和 + 二分答案 + 数学分块)
        // solveMaze();       // 题目二：小码哥的密室寻宝 (2D 迷宫 DFS + 恢复现场)
        solveLogistics();  // 题目三：物流中转 (图论邻接表建图 + 连通性 DFS)
    }

    /**
     * =====================================================================
     * 题目一：打工人 (MC0331 - 钻石级)
     * 核心考点：二分答案 + 高中数列求和公式 (O(1) 极速前缀和)
     * =====================================================================
     * * 【题目描述】
     * 小码哥第 1 天完成 1 项任务，接下来的 2 天（第 2 到 3 天）每天完成 2 项，
     * 接下来的 3 天（第 4 到 6 天）每天完成 3 项... 以此类推。
     * 给定查询天数 x，求出前 x 天一共完成了多少项任务。
     * * 【考场致命避坑提示】
     * 1. 钻石题的数据规模极大，天数 x、计算的块数 k、总任务量必须【全员使用 long】，否则直接溢出变成负数！
     * 2. 极限大数据下，StreamTokenizer (底层为 double) 会丢失精度，必须退回使用 
     * BufferedReader + StringTokenizer + Long.parseLong() 的绝对安全组合。
     */
    public static void solveWorker() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int q = Integer.parseInt(st.nextToken());
        
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            long L = Long.parseLong(st.nextToken());
            long R = Long.parseLong(st.nextToken());
            
            System.out.println(getSum(R) - getSum(L - 1));
        }
    }

    // 核心黑盒：以 O(1) 复杂度求出第 1 天到第 x 天的总任务量
    public static long getSum(long x) {
        if (x == 0) return 0;
        long number = 0;
        
        long Kuai = check(x); // check返回的是经历的完整块数
        
        // 前 Kuai 个块完成的总任务量 (高中平方和公式)
        long KuaiNumber = Kuai * (Kuai + 1) * (2 * Kuai + 1) / 6;
        
        // 剩余的零头天数 (总天数 - 完整块经过的天数)
        long SurplusDay = x - Kuai * (Kuai + 1) / 2; 
        
        // 零头天数每天完成的任务量是 Kuai + 1
        long SurplusNumber = SurplusDay * (Kuai + 1);
        
        number = KuaiNumber + SurplusNumber;
        return number;
    }
    
    // 二分找包含的最大完整块数
    public static long check(long x) {
        long left = 1;
        long right = x;
        long target = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2; // 防溢出二分求中点写法
            if (mid * (mid + 1) / 2 <= x) {
                target = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return target;
    }

    /**
     * =====================================================================
     * 题目二：小码哥的密室寻宝 (基础 DFS)
     * 核心考点：2D网格状态空间树映射 + 四向试探 + 回溯(恢复现场)
     * =====================================================================
     * * 【题目描述】
     * 3x3 的密室，起点 (0,0)，终点 (2,2)，中间 (1,1) 是深坑绝对不能走。
     * 求出一共有多少条不同的安全路线？
     */
    public static void solveMaze() {
        visited2D = new boolean[3][3];
        totalPaths = 0;
        
        dfsMaze(0, 0); // 从起点开始试探
        System.out.println("总共有 " + totalPaths + " 条路线");
    }

    public static void dfsMaze(int x, int y) {
        // 1. 撞墙拦截：越界、踩陷阱、走回头路
        if (x < 0 || x >= 3 || y < 0 || y >= 3 || maze[x][y] == 1 || visited2D[x][y]) {
            return;
        }
        
        // 2. 终点结算
        if (x == 2 && y == 2) {
            totalPaths++; // 找到一条合法路线
            return;       // 这条路结束，退回
        }
        
        // 3. 做标记 (涂黑)
        visited2D[x][y] = true;
        
        // 4. 方向盘四向探测
        for (int i = 0; i < 4; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            dfsMaze(nextX, nextY); // 把新坐标传给下一层
        }
        
        // 5. 核心奥义：恢复现场 (擦除脚印)
        // 因为要求出所有可能路线，所以死路退回时，必须擦掉痕迹，方便别的路线再来。
        visited2D[x][y] = false;
    }

    /**
     * =====================================================================
     * 题目三：物流中转 (图论连通性 DFS)
     * 核心考点：邻接表建图 + 查字典式遍历 + 【永久拉黑免回溯】
     * =====================================================================
     * * 【题目描述】
     * 物流网有 n 个中转站，m 条单向通路。
     * 给定路线图，问物件能否从 1 号站送到 n 号站？
     * * 【避坑提示】
     * 只判断“能不能连通”时，【严禁恢复现场】。如果发现死路，就应该被永久拉黑，
     * 以防止其他路线绕圈再跑来送死，同时还能彻底消灭死循环！
     */
    public static void solveLogistics() throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        
        st.nextToken(); int n = (int) st.nval; // 中转站数量
        st.nextToken(); int m = (int) st.nval; // 路线数量
        
        // 买柜子和记号笔
        map = new ArrayList[n + 1];
        visited1D = new boolean[n + 1];
        canReach = false;
        
        // 给每个柜子放一个记事本 (ArrayList)
        for (int i = 0; i <= n; i++) {
            map[i] = new ArrayList<Integer>();
        }
        
        // 读取路线并建图
        for (int i = 0; i < m; i++) {
            st.nextToken(); int x = (int) st.nval;
            st.nextToken(); int y = (int) st.nval;
            map[x].add(y); // 拉开 x 的抽屉，把目的站 y 记录下来
        }
        
        // 从 1 号站出发，寻找 n 号站
        dfsGraph(1, n);
        
        if (canReach) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private static void dfsGraph(int current, int target) {
        // 1. 越界保护与死路拦截 (防背刺/防死循环的盾牌)
        if (current <= 0 || current > target || visited1D[current] || canReach) {
            return;
        }
        
        // 2. 到达目的地
        if (current == target) {
            canReach = true;
            return;
        }
        
        // 3. 永久拉黑本站
        visited1D[current] = true;
        
        // 4. 查字典降维打击：不需要算 dx/dy，直接翻开本站的记事本挨个去就行
        for (int nextStation : map[current]) {
            dfsGraph(nextStation, target);
        }
        
        // 注意：这里什么都不写！(绝不恢复现场，死路直接封锁)
    }
}