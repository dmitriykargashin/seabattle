import java.awt.*;

/**
 * Created by Dimon on 22.02.2016.
 */
public class Coordinate extends Point { // наследуемся от класса Point

    private CoordinateState coordinateState;

    private Ship ship; // если есть корабль на этой координате

    public Coordinate(int x, int y) {
        super(x, y);
        coordinateState = CoordinateState.COORD_STATE_EMPTY;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;

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


}
