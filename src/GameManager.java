import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Dimon on 21.02.2016.
 * Основной класс игры, тут будеем управлять игрой
 */
public class GameManager {
    final static int FIELD_SIDE_SIZE = 10;
    final static int DECK_1_SHIPS_COUNT = 4;
    final static int DECK_2_SHIPS_COUNT = 3;
    final static int DECK_3_SHIPS_COUNT = 2;
    final static int DECK_4_SHIPS_COUNT = 1;
    private GameOptions gameOptions = new GameOptions();// это настройки нашей игры
    private Player gamePlayer1;//  первый игрок
    private Player gamePlayer2;//  второй игрок


    public GameManager() {
        System.out.println("Welcome to SeaBattle!\n"); // Поприветствуем
        askForAutoOrManualShipsReplacement(gameOptions);

        gamePlayer1 = createPlayer(); // получим первого игрока
        gamePlayer2 = createPlayer(); // получим второго игрока

        // генерируем всё игровое пространство для каждого игрока
        generateGameForPlayer(gamePlayer1);
        generateGameForPlayer(gamePlayer2);

        perform30RandomShoots(gamePlayer1, gamePlayer2);

        Random randWinner = new Random();
        int winner = randWinner.nextInt(2);

        //todo тут заглушка для выбора победителя. нужно сделать нормальную отработку выигрыша
        if (winner == 0) System.out.println("Congratulations " + gamePlayer1.getUserName() + "! You are the Winner!");
        else System.out.println("Congratulations " + gamePlayer2.getUserName() + "! You are the Winner!");

    }

    private void perform30RandomShoots(Player player1, Player player2) {
        for (int i = 0; i < 100; i++) {
            System.out.println("\n" + i + " turn");
            performRandomShoot(player1, player2); // первый игрок ходит к второму
            performRandomShoot(player2, player1); // второй игрок ходит к первому
        }
    }

    private CoordinateState performRandomShoot(Player player1, Player player2) {

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
        System.out.println(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + coordinate.toString() + ")");
        showPlayerFields(player1);

        if (coordinateState == CoordinateState.COORD_STATE_HIT) { // если попали, то даётcя дополнительный выстрел
            do {
                coordinateState = performAdditionalRandomShootByPriorCoordinates(player1, player2, coordinate);
            } while (coordinateState == CoordinateState.COORD_STATE_HIT);
        }

        // вернём состояние последней координаты

        return coordinateState;

    }

    private CoordinateState performRandomShootByPriorCoordinates(Player player1, Player player2) {

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

        System.out.println(" \n" + player1.getUserName() + " perform Shot to " + player2.getUserName() + " by the coordinates (" + newCoordinate.toString() + ")");

        showPlayerFields(player1);

        return coordinateState;

    }

    private CoordinateState performAdditionalRandomShootByPriorCoordinates(Player player1, Player player2, Coordinate coordinate) {
        // выполняем дополнительный выстрел по приритетным координатам относительно последнего удачного выстрела

        // получим приоритетные координаты для выстрела
        System.out.println(" \n" + player1.getUserName() + " perform additional Shot to " + player2.getUserName() + " by the near coordinates (" + coordinate.toString() + ")");

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


    private void generateGameForPlayer(Player player) {
// если автоматом, то будем генерить
        if (!gameOptions.isManualShipsReplacement()) {
            generateShips(player);// генерим корабли на поле
            System.out.println("\nShips generated!"); // Чужое поле
        } else { // попросим расставить вручную

        }
        showPlayerFields(player);
    }

    private void showPlayerFields(Player player) {
        System.out.println(player.getUserName() + " Sea field:"); // Собственное поле
        player.getOwnField().showField();// выведем сгенерированное поле на экран

        System.out.println("\nAlien Sea field:"); // Чужое поле
        player.getAlienSeaField().showField();// выведем сгенерированное поле на экран
    }

    private void generateShips(Player forPlayer) { // создадим случайным образом корабли для указанного игрока
        forPlayer.getShipsList().generateRandom(DECK_1_SHIPS_COUNT, DECK_2_SHIPS_COUNT, DECK_3_SHIPS_COUNT, DECK_4_SHIPS_COUNT, forPlayer.getOwnField());
    }

    private Player createPlayer()// создадим игрока
    {
        System.out.println("\nEnter Player Name/NickName:"); // Поприветствуем

        String userName = getUserInput();

        Player player = new Player(userName, FIELD_SIDE_SIZE);// создаём игрока с указанным именем и создаём его поля с указанным размером

        System.out.println("Hello " + player.getUserName()); // печатаем строку
        return player;
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in); // создали для чтения ввода пользователя
        return scanner.nextLine(); // читаем строку

    }

    private void askForAutoOrManualShipsReplacement(GameOptions gameOpt) {
        System.out.println("Set 0 for Automatic ship replacement or");
        System.out.println("set 1 for Manual ship replacement:");
        String answer = getUserInput(); // читаем строку //todo сделать проверку на ошибки ввода

        if (answer.equals("0")) {// строка это объект, поэтому сравниваем через equals
            gameOpt.setManualShipsReplacement(false);
            System.out.println("You choose Automatic ship replacement.");
        } else {
            gameOpt.setManualShipsReplacement(true);
            System.out.println("You choose Manual ship replacement.");
        }
    }


}
