import java.util.ArrayList;

/**
 * Created by Dimon on 21.02.2016.
 * Корабль с основными свойствами
 */
public class Ship {

    private ShipType shipType; // тип корабля
    private ArrayList<Coordinate> coordinates = new ArrayList<>(); //координаты от первой палубы
    private ShipRotation shipRotation; // угол поворота корабля относительно первой палубы
    private int length; //длина корабля;

    public Ship(Coordinate coordinate, ShipType shipType, ShipRotation shipRotation) {
        this.shipType = shipType;
        this.shipRotation = shipRotation;
        coordinates.add(coordinate); // добавим координату первой палубы
        calculateShipLength();

        // теперь нужно рассчитать остальные координаты в зависимости от типа и угла размещения
        calculateAnotherCoordinates();

    }

    private void calculateAnotherCoordinates() {
        //заполнение остальных координат корабля на основании длины, и угла поворота от начальной точки
        switch (shipRotation) {
            case ROTATION_0:
                calculateCoordinates0Angle();
                break;
            case ROTATION_90:
                calculateCoordinates90Angle();
                break;
            case ROTATION_180:
                calculateCoordinates180Angle();
                break;
            case ROTATION_270:
                calculateCoordinates270Angle();
                break;
        }
    }

    private void calculateCoordinates0Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x + i, firstCoordinate.y);// движемся по x вправо
            coordinates.add(NextCoordinate);
        }
    }

    private void calculateCoordinates90Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x, firstCoordinate.y - i);// движемся по у вверх
            coordinates.add(NextCoordinate);
        }
    }


    private void calculateCoordinates180Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x - i, firstCoordinate.y);// движемся по x влево
            coordinates.add(NextCoordinate);
        }
    }

    private void calculateCoordinates270Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x, firstCoordinate.y + i);// движемся по у вниз
            coordinates.add(NextCoordinate);
        }
    }

    private void calculateShipLength() {
        // расчёт длины корабля
        switch (shipType) {
            case DECK1:
                length = 1;
                break;
            case DECK2:
                length = 2;
                break;
            case DECK3:
                length = 3;
                break;
            case DECK4:
                length = 4;
                break;
        }

    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public int getShipLength() {
        return length;
    }
}
