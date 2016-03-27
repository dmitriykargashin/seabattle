import java.util.ArrayList;

/**
 * Created by Dimon on 21.02.2016.
 * Информация о пользователе
 */
public class Player {
    private String userName; // имя игрока
    private ShipsList shipsList = new ShipsList();// список кораблей игрока

    private SeaField ownSeaField; //собственное поле
    //  private SeaField alienSeaField; // чужое поле

    private ArrayList<Coordinate> priorCoordstoShoot = new ArrayList<>(); // тут будем хранить приортетный список координат для выстрела


    public Player(String userName, int fieldSideSize) {

        this.userName = userName;
        ownSeaField = new SeaField(fieldSideSize);// создаем своё поле с указанным размером
        //    alienSeaField = new SeaField(fieldSideSize);// создаем чужое поле с указанным размером
    }

    public String getUserName() {
        return userName;
    }

    public ShipsList getShipsList() {
        return shipsList;
    }

    public SeaField getOwnField() {
        return ownSeaField;
    }

//    public SeaField getAlienSeaField() {
//        return alienSeaField;
//    }

    public ArrayList<Coordinate> getPriorCoordstoShoot() {// получим список приортетных координат
        return priorCoordstoShoot;
    }

}
