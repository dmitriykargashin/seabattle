import java.util.ArrayList;

/**
 * Created by Dimon on 22.02.2016.
 * Поле для игры
 */
public class SeaField {
    static int fieldSideSizeMatrix = 0;
    static int lastFieldSideMatrixIndex = 0;
    private Coordinate[][] fieldMatrix;// двумерная матрица игрового поля


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


    public void showField() { //вывод на экран указанного поля с разным состоянием ячеек
        System.out.print("  ");
        for (int i = 0; i <= lastFieldSideMatrixIndex; i++) {
            System.out.print(" " + (char) ('A' + i) + " ");
        }
        System.out.print("\n");

        for (int i = 0; i <= lastFieldSideMatrixIndex; i++) {
            System.out.print(i + " ");
            for (int j = 0; j <= lastFieldSideMatrixIndex; j++) {
                switch (fieldMatrix[i][j].getCoordState()) {
                    case COORD_STATE_EMPTY:
                        System.out.print("\033[36m . \033[0m");
                        break;
                    case COORD_STATE_HIT:
                        System.out.print("\033[31m[X]\033[0m");
                        break;
                    case COORD_STATE_MISSED:
                        System.out.print("\033[33m о \033[0m");
                        break;
                    case COORD_STATE_SHIP:
                        System.out.print("\033[34m[+]\033[0m");
                        break;
                }

            }
            System.out.print("\n");

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

    public void setShipToField(Ship ship) {
        // утсновка корабля на поле
        ArrayList<Coordinate> shipCoordinates = ship.getCoordinates();
        for (Coordinate coordinate : shipCoordinates) {

            fieldMatrix[coordinate.x][coordinate.y].setCoordState(CoordinateState.COORD_STATE_SHIP); // отмечаем, что поле занято кораблём

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
                break;
            }
        }

        return getCoordinates(x, y).getCoordState();
    }
}
