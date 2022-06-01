package rrioja.event;

public class RDividedEvt extends AbstractReversedEvt<DividedEvt> {
    private final Integer scalar;

    public RDividedEvt(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public DividedEvt getReverseEvt() {
		return new DividedEvt(getId(), scalar, true);
	}

	@Override
	public Class<DividedEvt> getReversedEvtClass() {
		return DividedEvt.class;
	}

    @Override
	public String toString() {
		return "(R) /" + scalar;
	}
}