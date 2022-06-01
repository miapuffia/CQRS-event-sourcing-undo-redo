package rrioja.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class AbstractCmd {
	@TargetAggregateIdentifier
    private final String id;

    public AbstractCmd(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}