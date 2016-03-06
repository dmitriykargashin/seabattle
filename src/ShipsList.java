import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dimon on 22.02.2016.
 * Список кораблей одного игрока
 */
public class ShipsList {
    private int liveShipsCount = 0; //общее количество оставщихся кораблей //todo пока непонятно, нужна ли эта переменная
    private ArrayList<Ship> shipsList; // корабли игрока

    public ShipsList() {
        shipsList = new ArrayList<>();
    }

    public void generateRandom(int deck1ShipsCount, int deck2ShipsCount, int deck3ShipsCount, int deck4ShipsCount, SeaField seaField) {
        //тут генерим указанного количества корабли на указанном поле
        for (int i = 0; i < deck1ShipsCount; i++) {
            generateAndSetShip(ShipType.DECK1, seaField);
        }

        for (int i = 0; i < deck2ShipsCount; i++) {
            generateAndSetShip(ShipType.DECK2, seaField);
        }

        for (int i = 0; i < deck3ShipsCount; i++) {
            generateAndSetShip(ShipType.DECK3, seaField);
        }

        for (int i = 0; i < deck4ShipsCount; i++) {
            generateAndSetShip(ShipType.DECK4, seaField);
        }

    }


    private void generateAndSetShip(ShipType shipType, SeaField seaField) {
        // тут поищем случайные координаты, случайный поворот
        Random randNumber = new Random();
        Ship nextShip;// размещённый корабль
        boolean isFit; // корабль вмещается на поле или нет
        do {
            int x = randNumber.nextInt(SeaField.fieldSideSizeMatrix);
            int y = randNumber.nextInt(SeaField.fieldSideSizeMatrix);
            Coordinate coordinate = new Coordinate(x, y);
            int shipRot = randNumber.nextInt(3);// TODO туповато так указывать, но столько типов поворота корабля
            ShipRotation shipRotation = ShipRotation.values()[shipRot];

            nextShip = new Ship(coordinate, shipType, shipRotation);
            if ((seaField.shipIsFitToCoordinates(nextShip))) isFit = true;
            else isFit = false;

        } while (!isFit); //TODO теоретически тут может зациклиться
        // тут поставим корабль на поле.
        seaField.setShipToField(nextShip);// поместим корабль на поле

        shipsList.add(nextShip);// добавим корабль в список кораблей игрока
    }


}

