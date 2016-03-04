import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dimon on 21.02.2016.
 * Корабль с основными свойствами
 */
public class Ship {

    private ShipType shipType; // тип корабля
    private ArrayList<Coordinate> coordinates = new ArrayList<>(); //координаты первой палубы
    private ShipRotation shipRotation; // угол поворота корабля относительно первой палубы
    private int length; //длина корабля;

    public Ship(Coordinate coordinate, ShipType shipType, ShipRotation shipRotation) {
        this.shipType = shipType;
        this.shipRotation = shipRotation;
        coordinates.add(coordinate); // добавим координату первой палубы
        CalculateShipLength();

        // теперь нужно рассчитать остальные координаты в зависимости от типа и угла размещения
        CalculateAnotherCoordinates();

    }

    private void CalculateAnotherCoordinates() {
        //заполнение остальных координат корабля на основании длины, и угла поворота от начальной точки
        switch (shipRotation) {
            case ROTATION_0:
                CalculateCoordinates0Angle();
                break;
            case ROTATION_90:
                CalculateCoordinates90Angle();
                break;
            case ROTATION_180:
                CalculateCoordinates180Angle();
                break;
            case ROTATION_270:
                CalculateCoordinates270Angle();
                break;
        }
    }

    private void CalculateCoordinates0Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x + i, firstCoordinate.y);// движемся по x вправо
            coordinates.add(NextCoordinate);
        }
    }

    private void CalculateCoordinates90Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x, firstCoordinate.y - i);// движемся по у вверх
            coordinates.add(NextCoordinate);
        }
    }


    private void CalculateCoordinates180Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x - i, firstCoordinate.y);// движемся по x влево
            coordinates.add(NextCoordinate);
        }
    }

    private void CalculateCoordinates270Angle() {
        Coordinate firstCoordinate = coordinates.get(0);
        for (int i = 1; i < length; i++) {
            Coordinate NextCoordinate = new Coordinate(firstCoordinate.x, firstCoordinate.y + i);// движемся по у вниз
            coordinates.add(NextCoordinate);
        }
    }

    private void CalculateShipLength() {
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
