package rrioja.event;

public interface IReversableEvt<T> {
    public T getReverseEvt();
    
    public Class<T> getReversedEvtClass();
}