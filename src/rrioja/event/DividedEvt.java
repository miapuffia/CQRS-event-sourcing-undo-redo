package rrioja.event;

public class DividedEvt extends AbstractRedoneEvt implements IReversableEvt<RDividedEvt> {
    private final Integer scalar;

    public DividedEvt(String id, Integer scalar, boolean isRedone) {
        super(id, isRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RDividedEvt getReverseEvt() {
		return new RDividedEvt(getId(), scalar);
	}

	@Override
	public Class<RDividedEvt> getReversedEvtClass() {
		return RDividedEvt.class;
	}

    @Override
	public String toString() {
		return "/" + scalar;
	}
}