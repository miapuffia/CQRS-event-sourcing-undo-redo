package rrioja.command;

public class IssueCmd extends AbstractCmd {
    private final Integer initial;

    public IssueCmd(String id, Integer initial) {
        super(id);
        this.initial = initial;
    }

    public Integer getInitial() {
        return initial;
    }
}