/**
 * Created by Dimon on 16.03.2016.
 * интерфейс для работы с отображением информации на экране
 */
public interface Viewable {
    void showPlayerFields(Player player); // показ игровых полей

    void askForAutoOrManualShipsReplacement(GameOptions gameOpt);   // получить значения опции АВто или ручное размещение кораблей

    String getUserInput();// для чтения ввода пользователя

    void printWelcome();//приветственный текст

    String inputPlayerName();//получить имя игрока

    void showField(SeaField seaField, int lastFieldSideMatrixIndex); //вывод на экран указанного поля с разным состоянием ячеек
}
