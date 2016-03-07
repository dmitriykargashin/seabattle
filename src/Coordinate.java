import java.awt.*;

/**
 * Created by Dimon on 22.02.2016.
 */
public class Coordinate extends Point { // наследуемся от класса Point
    //private int x;
    //private int y;
    private CoordinateState coordinateState;


    public Coordinate(int x, int y) {
        super(x, y);
        //this.x = x;
        //this.y = y;
        coordinateState = CoordinateState.COORD_STATE_EMPTY;
    }

    @Override
    public String toString() {

        return (char) ('A' + y) + "," + x;
    }

    public CoordinateState getCoordState() {
        return coordinateState;
    }


    public void setCoordState(CoordinateState coordStateShip) {
        coordinateState = coordStateShip;
    }


    /*public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }*/
}
