package rrioja.event;

public abstract class AbstractReversedEvt<T> extends AbstractEvt implements IReversableEvt<T> {
	public AbstractReversedEvt(String id) {
		super(id);
	}
}