package rrioja.event;

public abstract class AbstractRedoneEvt extends AbstractEvt {
	private final boolean isRedone;
	
    public AbstractRedoneEvt(String id, boolean isRedone) {
        super(id);
        this.isRedone = isRedone;
    }

	public boolean isRedone() {
		return isRedone;
	}
}