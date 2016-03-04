/**
 * Created by Dimon on 27.02.2016.
 * Используется для глобальных настроек в игре
 */
public class GameOptions {
    private boolean manualShipsReplacement; // ручная расстановка кораблей или автоматическая (случайная). 

    public boolean isManualShipsReplacement() {
        return manualShipsReplacement;
    }

    public void setManualShipsReplacement(boolean manualShipsReplacement) {
        this.manualShipsReplacement = manualShipsReplacement;
    }


}
