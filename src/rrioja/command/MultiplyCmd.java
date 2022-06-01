package rrioja.command;

public class MultiplyCmd extends AbstractRedoableCmd implements IReversableCmd<RMultiplyCmd> {
    private final Integer scalar;

    public MultiplyCmd(String id, Integer scalar, boolean isBeingRedone) {
    	super(id, isBeingRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RMultiplyCmd getReverseCmd() {
		return new RMultiplyCmd(getId(), scalar);
	}
}