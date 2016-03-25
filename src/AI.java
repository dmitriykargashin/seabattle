import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dimon on 07.03.2016.
 * класс искусственного интеллекта. вынес в отлельный класс, т.к. содержит приличный анализ игровой ситуации
 * будет подсказывать компьютерному игроку куда стрелять дальше
 */
public class AI {
    private GameOptions gameOptions;
    private GUIConsole gameGUIConsole;

    public AI(GameOptions gameOptions, GUIConsole gameGUIConsole) {
        this.gameOptions = gameOptions;
        this.gameGUIConsole = gameGUIConsole;
    }

    public void perform30RandomShoots(Player player1, Player player2) {
        for (int i = 0; i < 30; i++) {
            gameGUIConsole.showMessage("\n" + i + " turn");
            //System.out.println();
            performRandomShoot(player1, player2); // первый игрок ходит к второму
            performRandomShoot(player2, player1); // второй игрок ходит к первому
        }
    }

    public CoordinateState performRandomShoot(Player player1, Player player2) {

        // тут проверим, есть ли приоритетные координаты для обстрела, если есть, то обрабатываем их в первую очередь
        if (!player1.getPriorCoordstoShoot().isEmpty()) {
            return performRandomShootByPriorCoordinates(player1, player2);
        }

        // тут нужно сгенерить случайную координату
        Random randNumber = new Random();
        CoordinateState coordinateState;// состояние координаты
        int x;
        int y;

        do { // тут проверим, что в эту координату мы уже не стреляли раньше (добавим мозгов :))
            x = randNumber.nextInt(SeaField.fieldSideSizeMatrix);
            y = randNumber.nextInt(SeaField.fieldSideSizeMatrix);
        } while ((player2.getOwnField().getCoordinates(x, y).getCoordState() == CoordinateState.COORD_STATE_HIT)
                || (player2.getOwnField().getCoordinates(x, y).getCoordState() == CoordinateState.COORD_STATE_MISSED));

        // стреляем в собственное поле другого игрока с указанными координатами и вовращаем состояние после выстрела
        coordinateState = player2.getOwnField().shoot(x, y);

        // устанавливаем состояние координаты поля чужого игрока первому
        Coordinate coordinate = player1.getAlienSeaField().getCoordinates(x, y);
        coordinate.setCoordState(coordinateState);
        gameGUIConsole.showMessage(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + coordinate.toString() + ")");
        //System.out.println(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + coordinate.toString() + ")");
        gameGUIConsole.showPlayerFields(player1);

        if (coordinateState == CoordinateState.COORD_STATE_HIT) { // если попали, то даётcя дополнительный выстрел
            do {
                coordinateState = performAdditionalRandomShootByPriorCoordinates(player1, player2, coordinate);
            } while (coordinateState == CoordinateState.COORD_STATE_HIT);
        }

        // вернём состояние последней координаты

        return coordinateState;

    }

    public CoordinateState performRandomShootByPriorCoordinates(Player player1, Player player2) {

        Random randNumber = new Random();
        int coordIndex = randNumber.nextInt(player1.getPriorCoordstoShoot().size());// любую координату из списка получим

        // стреляем в собственное поле другого игрока с указанными координатами и вовращаем состояние после выстрела
        Coordinate newCoordinate = player1.getPriorCoordstoShoot().get(coordIndex);

        CoordinateState coordinateState;// состояние координаты
        coordinateState = player2.getOwnField().shoot(newCoordinate.x, newCoordinate.y);

        // удалим из списка, ту координату, куда уже стреляли
        player1.getPriorCoordstoShoot().remove(coordIndex);

        // устанавливаем состояние координаты поля чужого игрока первому
        player1.getAlienSeaField().getCoordinates(newCoordinate.x, newCoordinate.y).setCoordState(coordinateState);
        gameGUIConsole.showMessage(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + newCoordinate.toString() + ")");
        //System.out.println(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + newCoordinate.toString() + ")");

        gameGUIConsole.showPlayerFields(player1);

        return coordinateState;

    }

    public CoordinateState performAdditionalRandomShootByPriorCoordinates(Player player1, Player player2, Coordinate coordinate) {
        // выполняем дополнительный выстрел по приритетным координатам относительно последнего удачного выстрела

        // получим приоритетные координаты для выстрела
        gameGUIConsole.showMessage(" \n" + player1.getUserName() + " perform additional Shot to " + player2.getUserName() + " by the near coordinates (" + coordinate.toString() + ")");
        //System.out.println();

        ArrayList<Coordinate> notShootedNearCoords = player2.getOwnField().getNotShootedNearCoords(coordinate);

        if (notShootedNearCoords.isEmpty()// если нет приоритетных координат
                && player1.getPriorCoordstoShoot().isEmpty())//и список приоритетных координат тоже пустой
        {
            return performRandomShoot(player1, player2);// то стреляем обычно
        }// иначе идём дальше

        player1.getPriorCoordstoShoot().addAll(notShootedNearCoords);// пополним список приоритетных координат пользователя

        //стреляем по приортитетным координатам
        return performRandomShootByPriorCoordinates(player1, player2);
    }

}
