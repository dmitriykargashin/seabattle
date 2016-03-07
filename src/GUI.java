import java.util.Scanner;

/**
 * Created by Dimon on 07.03.2016.
 * класс для работы с графическим интерфейсом(на данный момент (07.03.16) с консолью)
 */
public class GUI {

    public void showPlayerFields(Player player) {
        System.out.println(player.getUserName() + " Sea field:"); // Собственное поле
        player.getOwnField().showField();// выведем сгенерированное поле на экран

        System.out.println("\nAlien Sea field:"); // Чужое поле
        player.getAlienSeaField().showField();// выведем сгенерированное поле на экран
    }

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

    public String getUserInput() {
        Scanner scanner = new Scanner(System.in); // создали для чтения ввода пользователя
        return scanner.nextLine(); // читаем строку

    }

    public void printWelcome() {
        System.out.println("Welcome to SeaBattle!\n"); // Поприветствуем
    }

    public String inputPlayerName() {
        System.out.println("\nEnter Player Name/NickName:"); // Поприветствуем

        String userName = getUserInput();
        System.out.println("Hello " + userName); // печатаем строку
        return userName;

    }
}
