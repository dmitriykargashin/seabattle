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
    private PlayerInfo player1;//  первый игрок
    private PlayerInfo player2;//  второй игрок


    public GameManager() {
        System.out.println("Welcome to SeaBattle!\n"); // Поприветствуем
        askForAutoOrManualShipsReplacement(gameOptions);

        player1 = createPlayer(); // получим первого игрока
        player2 = createPlayer(); // получим второго игрока

        // генерируем всё игровое пространство для каждого игрока
        generateGameForPlayer(player1);
        generateGameForPlayer(player2);

        perform30RandomShoots(player1, player2);

        Random randWinner = new Random();
        int winner = randWinner.nextInt(2);

        //todo тут заглушка для выбора победителя. нужно сделать нормальную отработку выигрыша
        if (winner == 0) System.out.println("Congratulations " + player1.getUserName() + "! You are the Winner!");
        else System.out.println("Congratulations " + player2.getUserName() + "! You are the Winner!");

    }

    private void perform30RandomShoots(PlayerInfo player1, PlayerInfo player2) {
        for (int i = 0; i < 100; i++) {
            System.out.println("\n" + i + " turn");
            performRandomShoot(player1, player2); // первый игрок ходит к второму
            performRandomShoot(player2, player1); // второй игрок ходит к первому

        }

    }

    private void performRandomShoot(PlayerInfo playerInfo1, PlayerInfo playerInfo2) {
        // тут нужно сгенерить случайную координату
        Random randNumber = new Random();
        int x = randNumber.nextInt(SeaField.fieldSideSizeMatrix);
        int y = randNumber.nextInt(SeaField.fieldSideSizeMatrix);

        CoordinateState coordinateState;// состояние координаты
        // стреляем в собственное поле другого игрока с указанными координатами и вовращаем состояние после выстрела
        coordinateState = playerInfo2.getOwnField().shoot(x, y);

        // устанавливаем состояние координаты поля чужого игрока первому
        playerInfo1.getAlienSeaField().getCoordinates(x, y).setCoordState(coordinateState);

        System.out.println(" \n" + playerInfo1.getUserName() + " perform Shot to " + playerInfo2.getUserName() + " by the coordinates (" + (char) ('A' + y) + "," + x+")");

        showPlayerFields(playerInfo1);
    }

    private void generateGameForPlayer(PlayerInfo player) {
// если автоматом, то будем генерить
        if (!gameOptions.isManualShipsReplacement()) {
            generateShips(player);// генерим корабли на поле
            System.out.println("\nShips generated!"); // Чужое поле
        } else { // попросим расставить вручную

        }
        showPlayerFields(player);
    }

    private void showPlayerFields(PlayerInfo player) {
        System.out.println(player.getUserName() + " Sea field:"); // Собственное поле
        player.getOwnField().showField();// выведем сгенерированное поле на экран

        System.out.println("\nAlien Sea field:"); // Чужое поле
        player.getAlienSeaField().showField();// выведем сгенерированное поле на экран
    }

    private void generateShips(PlayerInfo forPlayer) { // создадим случайным образом корабли для указанного игрока
        forPlayer.getShipsList().generateRandom(DECK_1_SHIPS_COUNT, DECK_2_SHIPS_COUNT, DECK_3_SHIPS_COUNT, DECK_4_SHIPS_COUNT, forPlayer.getOwnField());
    }

    private PlayerInfo createPlayer()// создадим игрока
    {
        System.out.println("\nEnter Player Name/NickName:"); // Поприветствуем

        String userName = getUserInput();

        PlayerInfo playerInfo = new PlayerInfo(userName, FIELD_SIDE_SIZE);// создаём игрока с указанным именем и создаём его поля с указанным размером

        System.out.println("Hello " + playerInfo.getUserName()); // печатаем строку
        return playerInfo;
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
