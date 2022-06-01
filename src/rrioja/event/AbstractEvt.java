package rrioja.event;

public abstract class AbstractEvt {
    private final String id;

    public AbstractEvt(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}