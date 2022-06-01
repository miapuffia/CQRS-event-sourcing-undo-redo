package rrioja.event;

public class MultipliedEvt extends AbstractRedoneEvt implements IReversableEvt<RMultipliedEvt> {
    private final Integer scalar;

    public MultipliedEvt(String id, Integer scalar, boolean isRedone) {
        super(id, isRedone);
        this.scalar = scalar;
    }

    public Integer getScalar() {
        return scalar;
    }

	@Override
	public RMultipliedEvt getReverseEvt() {
		return new RMultipliedEvt(getId(), scalar);
	}

	@Override
	public Class<RMultipliedEvt> getReversedEvtClass() {
		return RMultipliedEvt.class;
	}

    @Override
	public String toString() {
		return "*" + scalar;
	}
}