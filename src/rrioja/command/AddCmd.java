package rrioja.command;

public class AddCmd extends AbstractRedoableCmd implements IReversableCmd<RAddCmd> {
    private final Integer scalar;

    public AddCmd(String id, Integer scalar, boolean isBeingRedone) {
        super(id, isBeingRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RAddCmd getReverseCmd() {
		return new RAddCmd(getId(), scalar);
	}
}