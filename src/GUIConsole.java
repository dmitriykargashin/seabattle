import java.util.Scanner;

/**
 * Created by Dimon on 07.03.2016.
 * класс для работы с графическим интерфейсом(на данный момент (07.03.16) с консолью)
 */
public class GUIConsole implements Viewable {

    @Override
    public void showPlayerField(Player player, boolean isAlienField) {


        showField(player.getOwnField(), GameManager.FIELD_SIDE_SIZE - 1, isAlienField);// выведем сгенерированное поле на экран
        //.showField();

        //System.out.println("\nAlien Sea field:"); // Чужое поле
        //showField(player.getAlienSeaField(), GameManager.FIELD_SIDE_SIZE - 1);// выведем сгенерированное поле на экран
        //player.getAlienSeaField().showField();// выведем сгенерированное поле на экран
    }

    @Override
    public void askForAutoOrManualShipsReplacement(GameOptions gameOpt) {
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

    @Override
    public String getUserInput() {
        Scanner scanner = new Scanner(System.in); // создали для чтения ввода пользователя
        return scanner.nextLine(); // читаем строку

    }

    @Override
    public void printWelcome() {
        System.out.println("Welcome to SeaBattle!\n"); // Поприветствуем
    }

    @Override
    public String inputPlayerName() {
        System.out.println("\nEnter Player Name/NickName:"); // Поприветствуем

        String userName = getUserInput();
        System.out.println("Hello " + userName); // печатаем строку
        return userName;

    }

    @Override
    public void showField(SeaField seaField, int lastFieldSideMatrixIndex, boolean isAlienField) { //вывод на экран указанного поля с разным состоянием ячеек

        System.out.print("  ");
        for (int i = 0; i <= lastFieldSideMatrixIndex; i++) {
            System.out.print(" " + (char) ('A' + i) + " ");
        }
        System.out.print("\n");

        for (int i = 0; i <= lastFieldSideMatrixIndex; i++) {
            System.out.print(i + " ");
            for (int j = 0; j <= lastFieldSideMatrixIndex; j++) {
                switch (seaField.getFieldMatrix()[i][j].getCoordState()) {
                    case COORD_STATE_EMPTY:
                        System.out.print("\033[36m . \033[0m");
                        break;
                    case COORD_STATE_HIT:
                        if (seaField.getFieldMatrix()[i][j].getShip().isSunken()) {
                            System.out.print("\033[37m[X]\033[0m");
                        } else {
                            System.out.print("\033[31m[X]\033[0m");
                        }
                        break;
                    case COORD_STATE_MISSED:
                        System.out.print("\033[33m о \033[0m");
                        break;
                    case COORD_STATE_SHIP:
                        if (isAlienField) {
                            System.out.print("\033[36m . \033[0m");
                        } else {
                            System.out.print("\033[34m[+]\033[0m");
                        }
                        break;
                }

            }
            System.out.print("\n");

        }
    }

    @Override
    public void showMessage(String s) {
        System.out.println(s);
    }
}
