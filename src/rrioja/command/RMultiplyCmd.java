package rrioja.command;

public class RMultiplyCmd extends AbstractReversableCmd<MultiplyCmd> {
    private final Integer scalar;

    public RMultiplyCmd(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public MultiplyCmd getReverseCmd() {
		return new MultiplyCmd(getId(), scalar, true);
	}
}