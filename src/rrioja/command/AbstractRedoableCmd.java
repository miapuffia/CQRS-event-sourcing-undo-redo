package rrioja.command;

public abstract class AbstractRedoableCmd extends AbstractCmd {
	private final boolean isBeingRedone;
	
    public AbstractRedoableCmd(String id, boolean isBeingRedone) {
        super(id);
        this.isBeingRedone = isBeingRedone;
    }

	public boolean isBeingRedone() {
		return isBeingRedone;
	}
}