package rrioja.event;

public class RAddedEvt extends AbstractReversedEvt<AddedEvt> {
    private final Integer scalar;

    public RAddedEvt(String id, Integer scalar) {
        super(id);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public AddedEvt getReverseEvt() {
		return new AddedEvt(getId(), scalar, true);
	}

	@Override
	public Class<AddedEvt> getReversedEvtClass() {
		return AddedEvt.class;
	}

    @Override
	public String toString() {
		return "(R) +" + scalar;
	}
}