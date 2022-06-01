package rrioja.command;

public abstract class AbstractReversableCmd<T> extends AbstractCmd implements IReversableCmd<T> {
    public AbstractReversableCmd(String id) {
        super(id);
    }
}