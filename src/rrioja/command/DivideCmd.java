package rrioja.command;

public class DivideCmd extends AbstractRedoableCmd implements IReversableCmd<RDivideCmd> {
    private final Integer scalar;

    public DivideCmd(String id, Integer scalar, boolean isBeingRedone) {
    	super(id, isBeingRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RDivideCmd getReverseCmd() {
		return new RDivideCmd(getId(), scalar);
	}
}