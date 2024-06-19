package variables;

public class Step {
    private Point firststep;
    private Point secondste;

    private int value;

    public Step(){
        firststep = new Point(8, 8);
        secondste = new Point(8, 8);
        value = 0;
    }

    public void setFirststep(int x1, int y1){
        firststep.x = x1;
        firststep.y = y1;
    }

    public void setSecondstep(int x1, int y1){
        secondste.x = x1;
        secondste.y = y1;
    }

    public Point getFirststep(){
        return firststep;
    }

    public Point getSecondstep(){
        return secondste;
    }
}
