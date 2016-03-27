import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dimon on 22.02.2016.
 * Поле для игры
 */
public class SeaField {
    static int fieldSideSizeMatrix = 0;
    static int lastFieldSideMatrixIndex = 0;
    private Coordinate[][] fieldMatrix;// двумерная матрица игрового поля

    public Coordinate[][] getFieldMatrix() {// получение всей матрицы игрового поля
        return fieldMatrix;
    }

    public SeaField(int fieldSideSize) {// тут сразу создам поле
        fieldSideSizeMatrix = fieldSideSize;
        lastFieldSideMatrixIndex = fieldSideSize - 1;
        fieldMatrix = new Coordinate[fieldSideSizeMatrix][fieldSideSizeMatrix];


        for (int i = 0; i <= lastFieldSideMatrixIndex; i++) {
            for (int j = 0; j <= lastFieldSideMatrixIndex; j++) {
                fieldMatrix[i][j] = new Coordinate(i, j);
            }
        }
    }




    public boolean shipIsFitToCoordinates(Ship ship) {
        // проверка может ли корабль быть разещён тут
        ArrayList<Coordinate> shipCoordinates = ship.getCoordinates();
        //int shipLength = ship.getShipLength();

        for (Coordinate coordinate : shipCoordinates) {
            //System.out.println(coordinate.x + " " + coordinate.y + "-" + ship.getShipLength());

            if (coordinate.x > lastFieldSideMatrixIndex || coordinate.y > lastFieldSideMatrixIndex || coordinate.x < 0 || coordinate.y < 0) {
                return false;
            } // в поле не влезло, выходим

            Coordinate fieldMatrixCoord = fieldMatrix[coordinate.x][coordinate.y];
            if (fieldMatrixCoord.getCoordState() != CoordinateState.COORD_STATE_EMPTY || // если занята сама координата
                    isNearCoordAtFieldNotEmpty(fieldMatrixCoord)) {//также ещё проверить соседние координаты поля, чтобы были свободны
                return false;// занята ячейка, выходим
            }
        }

        return true;
    }

    private boolean isNearCoordAtFieldNotEmpty(Coordinate coordinate) {
        //проверить соседние координаты поля в радиусе 1, чтобы были свободны. По диагонали разрешим соприкасаться

        if ((coordinate.x < lastFieldSideMatrixIndex) && (fieldMatrix[coordinate.x + 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_EMPTY)) {
            return true;
        }

        if ((coordinate.x > 0) && (fieldMatrix[coordinate.x - 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_EMPTY)) {
            return true;
        }

        if ((coordinate.y < lastFieldSideMatrixIndex) && (fieldMatrix[coordinate.x][coordinate.y + 1].getCoordState() != CoordinateState.COORD_STATE_EMPTY)) {
            return true;
        }

        if ((coordinate.y > 0) && (fieldMatrix[coordinate.x][coordinate.y - 1].getCoordState() != CoordinateState.COORD_STATE_EMPTY)) {
            return true;
        }

        // если вокруг свободно
        return false;

    }

    public ArrayList<Coordinate> getNotShootedNearCoords(Coordinate coordinate) {
        // получить список нестрелянных координат вокруг указанной
        // пригодится для обстрела координат вокруг координаты после попадания
        // прверим четыре направления в радиусе 1

        ArrayList<Coordinate> priorCoords = new ArrayList<>(); // список координат для приритетного обстрела

        if ((coordinate.x < lastFieldSideMatrixIndex) && (fieldMatrix[coordinate.x + 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_HIT)
                && (fieldMatrix[coordinate.x + 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_MISSED)) {
            priorCoords.add(fieldMatrix[coordinate.x + 1][coordinate.y]);
        }

        if ((coordinate.x > 0) && (fieldMatrix[coordinate.x - 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_HIT)
                && (fieldMatrix[coordinate.x - 1][coordinate.y].getCoordState() != CoordinateState.COORD_STATE_MISSED)) {
            priorCoords.add(fieldMatrix[coordinate.x - 1][coordinate.y]);
        }

        if ((coordinate.y < lastFieldSideMatrixIndex) && (fieldMatrix[coordinate.x][coordinate.y + 1].getCoordState() != CoordinateState.COORD_STATE_HIT)
                && (fieldMatrix[coordinate.x][coordinate.y + 1].getCoordState() != CoordinateState.COORD_STATE_MISSED)) {
            priorCoords.add(fieldMatrix[coordinate.x][coordinate.y + 1]);
        }

        if ((coordinate.y > 0) && (fieldMatrix[coordinate.x][coordinate.y - 1].getCoordState() != CoordinateState.COORD_STATE_HIT)
                && (fieldMatrix[coordinate.x][coordinate.y - 1].getCoordState() != CoordinateState.COORD_STATE_MISSED)) {
            priorCoords.add(fieldMatrix[coordinate.x][coordinate.y - 1]);
        }

        return priorCoords;
    }

    public void setShipToField(Ship ship) {
        // установка корабля на поле
        ArrayList<Coordinate> shipCoordinates = ship.getCoordinates();
        for (Coordinate coordinate : shipCoordinates) {
            fieldMatrix[coordinate.x][coordinate.y].setCoordState(CoordinateState.COORD_STATE_SHIP); // отмечаем, что поле занято кораблём
            fieldMatrix[coordinate.x][coordinate.y].setShip(ship);
        }
    }

    public Coordinate getCoordinates(int x, int y) {
        //получить координату
        return fieldMatrix[x][y];
    }

    public CoordinateState shoot(int x, int y) {
        switch (getCoordinates(x, y).getCoordState()) {
            case COORD_STATE_EMPTY: {
                getCoordinates(x, y).setCoordState(CoordinateState.COORD_STATE_MISSED);
                break;
            }
            case COORD_STATE_SHIP: {
                getCoordinates(x, y).setCoordState(CoordinateState.COORD_STATE_HIT);
                getCoordinates(x, y).getShip().hitByCoordinates(x, y);// отметить в коорднтатх корабля, что в него попали
                break;
            }
        }

        return getCoordinates(x, y).getCoordState();
    }
}
