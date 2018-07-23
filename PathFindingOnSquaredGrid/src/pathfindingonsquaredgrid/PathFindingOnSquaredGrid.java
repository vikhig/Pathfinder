
import java.util.*;
import java.util.Scanner;


public class PathFindingOnSquaredGrid {

        //1 - Mahattan
        //2 - Euclid
        //3 - Chebyshev
        
    static final int METRICS=2;
    
    
    static ArrayList <Cell>shortestPath=new ArrayList<Cell>();
    static LinkedList<Cell> open= new LinkedList<Cell>();
    static LinkedList<Cell> closed= new LinkedList<Cell>();
    static int maxX=10;
    static int maxY=10;
    static Cell[][] board=new Cell[maxX][maxY];
    
    static boolean[][] randomlyGenMatrix;
    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;
    
        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }
    	
        return full;
    }
    
    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i+1, j);   // down
        flow(open, full, i, j+1);   // right
        flow(open, full, i, j-1);   // left
        flow(open, full, i-1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N-1][j]) return true;
        }
    	
        return false;
    }
    
 // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
        	if (full[N-1][j]) {
        		// StdOut.println("Hello");
        		directPerc = 1;
        		int rowabove = N-2;
        		for (int i = rowabove; i >= 0; i--) {
        			if (full[i][j]) {
        				// StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
        				directPerc++;
        			}
        			else break;
        		}
        	}
        }
    	
        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true; 
        else return false;
    }
    
    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	if ((i == x1 && j == y1) ||(i == x2 && j == y2)) {
                		StdDraw.circle(j, N-i-1, .5);
                	}
                	else StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }
    
    public static void show(boolean[][] a, ArrayList<Cell> shortest) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == true)
                	if (shortest.contains(board[i][j])) {
                		StdDraw.circle(j, N-i-1, .5);
                	}
                	else StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }
    
    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }
    
    public static int shortestPath(int x1,int y1, int x2, int y2){
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    public static List<Cell> getNeigbours(Cell cell){
        ArrayList<Cell> neighbours=new ArrayList<Cell>();
        for(int x=-1;x<2;x++)
            for(int y=-1;y<2;y++){
                if(!(x==0 && y==0) &&validCoord(cell.getX()+x,cell.getY()+y)){
                    if(!closed.contains(board[cell.getX()+x][cell.getY()+y]) && !isFull(cell.getX()+x, cell.getY()+y)){
                             neighbours.add(board[cell.getX()+x][cell.getY()+y]);
                    }
                }
            }
        return neighbours;
    }
    
    public static boolean validCoord(int x, int y){
        if (x < 0 || x >= maxX) return false;
        if (y < 0 || y >= maxY) return false;
        return true;
    }
    
    public static boolean isFull(int x, int y){
        return !randomlyGenMatrix[x][y];
    }
    
    public static Cell shortestInOpen(){
        Cell result=open.get(0);
        for(Cell o:open){
            if(o.getFCost()<result.getFCost()){
                result=o;
            }
        }
        return result;
    }
    
    public static ArrayList<Cell> calculatePath(Cell origin, Cell end){
        //StdOut.println("(" + end.getX() + ", " + end.getY() + ")");
        
        
        shortestPath.add(end);
        if(origin.equals(end)){
            return shortestPath;
        }else{
            return calculatePath(origin,end.getParent());
        }
    }
    
    public static ArrayList<Cell> findShortestPath(int x1,int y1, int x2, int y2, int metrics){
        //1 - Mahattan
        //2 - Euclid
        //3 - Chebyshev
        
        initializeBoard();
        board[x1][y2].setG(0);
        open.add(board[x1][y1]);
        boolean done =false;
        Cell current;
        while(!done){
            current=shortestInOpen();
            closed.add(current);
            open.remove(current);
            
            if((current.getX()==x2)&&(current.getY()==y2)){
                StdOut.println("The total cost " + current.getG());
                return calculatePath(board[x1][y1],current);
            }
            List<Cell>neighbours=getNeigbours(current);
            for(int i=0;i<neighbours.size();i++){
                Cell currentNeighbour=neighbours.get(i);
                if(!open.contains(currentNeighbour)){
                    currentNeighbour.setParent(current);
                    currentNeighbour.setG(current,metrics);
                    currentNeighbour.setH(board[x2][y2],metrics);
                    open.add(currentNeighbour);
                }else{if(currentNeighbour.getG() > currentNeighbour.calculateGCost(current,metrics)) { 
                        currentNeighbour.setParent(current);
                        currentNeighbour.setG(current,metrics);
                    
                }
            }
        }if (open.isEmpty()) { // no path exists
                return shortestPath; // return empty list
            }    
        }
        return null;
    }
    
    public static void initializeBoard(){
        for(int x=0;x<maxX;x++){
            for(int y=0;y<maxY;y++){
                board[x][y]=new Cell(x,y);
            }
        }
    }
    
    // test client
    public static void main(String[] args) {
    	randomlyGenMatrix = random(10, 0.9);
    	
    	//StdArrayIO.print(randomlyGenMatrix);
    	show(randomlyGenMatrix, true);
    	
    	System.out.println();
    	System.out.println("The system percolates: " + percolates(randomlyGenMatrix));
    	
    	System.out.println();
    	System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
    	System.out.println();
    	
    	Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();
        
        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();
        
        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();
        
        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();
         
        Stopwatch timerFlow = new Stopwatch();
               
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);
        show(randomlyGenMatrix,findShortestPath(Ai,Aj,Bi,Bj,METRICS));
        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
    }

}

