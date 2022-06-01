package rrioja.event;

public class AddedEvt extends AbstractRedoneEvt implements IReversableEvt<RAddedEvt> {
	private final Integer scalar;

    public AddedEvt(String id, Integer scalar, boolean isRedone) {
        super(id, isRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RAddedEvt getReverseEvt() {
		return new RAddedEvt(getId(), scalar);
	}

	@Override
	public Class<RAddedEvt> getReversedEvtClass() {
		return RAddedEvt.class;
	}

    @Override
	public String toString() {
		return "+" + scalar;
	}
}