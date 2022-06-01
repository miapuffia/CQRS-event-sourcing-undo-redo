package rrioja.command;

public class RDivideCmd extends AbstractReversableCmd<DivideCmd> {
    private final Integer scalar;

    public RDivideCmd(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public DivideCmd getReverseCmd() {
		return new DivideCmd(getId(), scalar, true);
	}
}