package pe.nanamochi.osu.banchojar.entities;

public enum Gamemode {

    OSU(0),
    TAIKO(1),
    CTB(2),
    MANIA(3);

    public int id;

    Gamemode(int id) {
        this.id = id;
    }
}
