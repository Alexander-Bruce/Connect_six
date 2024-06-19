package variables;

import java.util.Comparator;

public class Point{
    public int x;
    public int y;
    public int mark;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.mark = -1;
    }
    public Point(int x, int y, int mark){
        this.x = x;
        this.y = y;
        this.mark = mark;
    }


    @Override
    public String toString() {
        return (x + 1) + " " + (y + 1);
    }
}
