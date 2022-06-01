package rrioja.event;

public class RMultipliedEvt extends AbstractReversedEvt<MultipliedEvt> {
    private final Integer scalar;

    public RMultipliedEvt(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public MultipliedEvt getReverseEvt() {
		return new MultipliedEvt(getId(), scalar, true);
	}

	@Override
	public Class<MultipliedEvt> getReversedEvtClass() {
		return MultipliedEvt.class;
	}

    @Override
	public String toString() {
		return "(R) *" + scalar;
	}
}