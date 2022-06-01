package rrioja.command;

public class RAddCmd extends AbstractReversableCmd<AddCmd> {
    private final Integer scalar;

    public RAddCmd(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public AddCmd getReverseCmd() {
		return new AddCmd(getId(), scalar, true);
	}
}