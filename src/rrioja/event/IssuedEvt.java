package rrioja.event;

public class IssuedEvt extends AbstractEvt {
    private final Integer initial;

    public IssuedEvt(String id, Integer initial) {
        super(id);
        this.initial = initial;
    }

    public Integer getInitial() {
        return initial;
    }

    @Override
	public String toString() {
		return initial + "";
	}
}