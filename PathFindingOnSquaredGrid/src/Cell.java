
/**
 *
 * @author VikhiG
 */
public class Cell {
    int x; 
    int y;
    int gCost;
    int hCost;
    Cell parent;
    
    public Cell(int x,int y){
        this.x=x;
        this.y=y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getG(){
        return gCost;
    }
        
    public int getFCost(){
        return gCost+hCost;
    }
    
    public void setParent(Cell parent){
        this.parent=parent;
    }
    
    public void setG(int g){
        this.gCost=g;
    }
    
    public void setG(Cell origin, int metrics){
        switch(metrics){
    
        case 1: this.gCost=Math.abs(origin.getX()-x)+Math.abs(origin.getY()-y)+origin.getG();
            break;
         case 2: this.gCost = (int) Math.sqrt(Math.abs(origin.getX()-x)+Math.abs(origin.getY()-y)+origin.getG());
            break;
         case 3: this.gCost = Math.max( Math.abs(origin.getX()-x), Math.abs(origin.getY()-y))+origin.getG();
            break;
        }
    }
    
    public int calculateGCost(Cell origin, int metrics){
        switch(metrics){
    
         case 1: return Math.abs(origin.getX()-x)+Math.abs(origin.getY()-y)+origin.getG();   
         case 2: return (int) Math.sqrt(Math.abs(origin.getX()-x)+Math.abs(origin.getY()-y)+origin.getG());
         case 3: return Math.max( Math.abs(origin.getX()-x), Math.abs(origin.getY()-y))+origin.getG();
         default: return Math.abs(origin.getX()-x)+Math.abs(origin.getY()-y)+origin.getG(); 
        }    
        
    }
    
    public void setH(Cell end, int metrics){
        switch(metrics){
    
        case 1: this.hCost= Math.abs(end.getX()-x+Math.abs(end.getY()-y));
            break;
         case 2: this.hCost = (int) Math.sqrt(Math.abs(end.getX()-x)+Math.abs(end.getY()-y));
            break;
         case 3: this.hCost = Math.max( Math.abs(end.getX()-x), Math.abs(end.getY()-y));
            break;
        }    }
    
    public boolean equalsTo(Cell cell){
        return x==cell.getX() && y==cell.getY();
    }
    
    public Cell getParent(){
        return this.parent;
    }
    
    public String toString(){
        return "(" +this.x  + ", "+this.y+")";
    }
}
