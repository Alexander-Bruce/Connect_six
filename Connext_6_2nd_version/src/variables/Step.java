package variables;

public class Step {
    private Point firststep;
    private Point secondstep;

    public int value;

    public Step(){
        firststep = new Point(8, 8);
        secondstep = new Point(8, 8);
        value = 0;
    }

    public Step(Point p1, Point p2, int value){
        firststep = p1;
        secondstep = p2;
        this.value = value;
    }

    public void setFirststep(int x1, int y1){
        firststep.x = x1;
        firststep.y = y1;
    }

    public void setSecondstep(int x1, int y1){
        secondstep.x = x1;
        secondstep.y = y1;
    }

    public void setSecondstep(Point p){
        secondstep = p;
        value = firststep.mark + secondstep.mark;
    }

    public Point getFirststep(){
        return firststep;
    }

    public Point getSecondstep(){
        return secondstep;
    }


    public String toString(){
        return firststep.toString() + " " + secondstep.toString();
    }
}
