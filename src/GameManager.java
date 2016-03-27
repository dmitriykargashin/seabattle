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
    UIConsole gameUIConsole = new UIConsole();
    AI gameAI;

    public GameManager() {

        gameUIConsole.printWelcome();
        gameUIConsole.askForAutoOrManualShipsReplacement(gameOptions);

        gamePlayer1 = createPlayer(); // получим первого игрока
        gamePlayer2 = createPlayer(); // получим второго игрока

        // генерируем всё игровое пространство для каждого игрока
        generateGameForPlayer(gamePlayer1);
        generateGameForPlayer(gamePlayer2);

        gameAI = new AI(gameOptions, gameUIConsole, this);//закинем начальные значение в ИИ игры

        performRandomShoots(gamePlayer1, gamePlayer2);
    }

    public void gameEnd(Player player) {// тут завершим игру
        gameUIConsole.showMessage("Congratulations " + player.getUserName() + "! You are the Winner!"); // Чужое поле
        System.exit(0); //выйдем из игры

    }

    private void generateGameForPlayer(Player player) {
// если автоматом, то будем генерить
        if (!gameOptions.isManualShipsReplacement()) {
            generateShips(player);// генерим корабли на поле
            gameUIConsole.showMessage("\nShips generated!");
            //System.out.println("\nShips generated!");
        } else { // попросим расставить вручную

        }
        gameUIConsole.showPlayerField(player, false);
    }


    public void performRandomShoots(Player player1, Player player2) {
        for (int i = 0; i < 100; i++) {
            gameUIConsole.showMessage("\n" + i + " turn");
            gameAI.performRandomShoot(player1, player2); // первый игрок ходит к второму
            gameAI.performRandomShoot(player2, player1); // второй игрок ходит к первому
        }
    }


    private void generateShips(Player forPlayer) { // создадим случайным образом корабли для указанного игрока
        forPlayer.getShipsList().generateRandom(DECK_1_SHIPS_COUNT, DECK_2_SHIPS_COUNT, DECK_3_SHIPS_COUNT, DECK_4_SHIPS_COUNT, forPlayer.getOwnField());
    }

    private Player createPlayer()// создадим игрока
    {

        String playerName = gameUIConsole.inputPlayerName();

        Player player = new Player(playerName, FIELD_SIDE_SIZE);// создаём игрока с указанным именем и создаём его поля с указанным размером
        return player;
    }


}
