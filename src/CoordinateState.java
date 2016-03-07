/**
 * Created by Dimon on 22.02.2016.
 * Состояние координаты в игре
 */
public enum CoordinateState {
    COORD_STATE_EMPTY, // пусто
    COORD_STATE_SHIP, // есть корабль
    COORD_STATE_HIT,// попали
    COORD_STATE_MISSED, // мимо
    COORD_STATE_USELESS // бесполезная координата - тут в принципе уже не может быть корабля

}

