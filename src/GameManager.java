import java.util.Random;

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
    GUIConsole gameGUIConsole = new GUIConsole();
    AI gameAI;

    public GameManager() {

        gameGUIConsole.printWelcome();
        gameGUIConsole.askForAutoOrManualShipsReplacement(gameOptions);

        gamePlayer1 = createPlayer(); // получим первого игрока
        gamePlayer2 = createPlayer(); // получим второго игрока

        // генерируем всё игровое пространство для каждого игрока
        generateGameForPlayer(gamePlayer1);
        generateGameForPlayer(gamePlayer2);

        gameAI = new AI(gameOptions, gameGUIConsole);//закинем начальные значение в ИИ игры

        gameAI.perform30RandomShoots(gamePlayer1, gamePlayer2);

        Random randWinner = new Random();
        int winner = randWinner.nextInt(2);

        //todo тут заглушка для выбора победителя. нужно сделать нормальную отработку выигрыша
        if (winner == 0) {
            gameGUIConsole.showMessage("Congratulations " + gamePlayer1.getUserName() + "! You are the Winner!");
            //System.out.println("Congratulations " + gamePlayer1.getUserName() + "! You are the Winner!")
        } else {
            gameGUIConsole.showMessage("Congratulations " + gamePlayer2.getUserName() + "! You are the Winner!");
            //System.out.println("Congratulations " + gamePlayer2.getUserName() + "! You are the Winner!")
        }

    }


    private void generateGameForPlayer(Player player) {
// если автоматом, то будем генерить
        if (!gameOptions.isManualShipsReplacement()) {
            generateShips(player);// генерим корабли на поле
            gameGUIConsole.showMessage("\nShips generated!");
            //System.out.println("\nShips generated!");
        } else { // попросим расставить вручную

        }
        gameGUIConsole.showPlayerField(player, false);
    }


    private void generateShips(Player forPlayer) { // создадим случайным образом корабли для указанного игрока
        forPlayer.getShipsList().generateRandom(DECK_1_SHIPS_COUNT, DECK_2_SHIPS_COUNT, DECK_3_SHIPS_COUNT, DECK_4_SHIPS_COUNT, forPlayer.getOwnField());
    }

    private Player createPlayer()// создадим игрока
    {

        String playerName = gameGUIConsole.inputPlayerName();

        Player player = new Player(playerName, FIELD_SIDE_SIZE);// создаём игрока с указанным именем и создаём его поля с указанным размером
        return player;
    }


}
