package rrioja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.GenericTrackedDomainEventMessage;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import rrioja.command.*;
import rrioja.event.*;

@Aggregate
public class Calculator {
	@AggregateIdentifier
    private String id;
    private int currentResult;
    
    @Autowired
    private EventStore eventStore;
    
    public Calculator() {}

    @CommandHandler
    public Calculator(IssueCmd cmd) {
        AggregateLifecycle.apply(new IssuedEvt(cmd.getId(), cmd.getInitial()));
    }

    @EventSourcingHandler
    public void on(IssuedEvt evt) {
        id = evt.getId();
        currentResult = evt.getInitial();
    }

    @CommandHandler
    public void handle(AddCmd cmd) {
        AggregateLifecycle.apply(new AddedEvt(id, cmd.getScalar(), false));
    }

    @EventSourcingHandler
    public void on(AddedEvt evt) {
    	currentResult += evt.getScalar();
    }

    @CommandHandler
    public void handle(RAddCmd cmd) {
        AggregateLifecycle.apply(new RAddedEvt(id, cmd.getScalar()));
    }

    @EventSourcingHandler
    public void on(RAddedEvt evt) {
    	currentResult -= evt.getScalar();
    }

    @CommandHandler
    public void handle(MultiplyCmd cmd) {
        AggregateLifecycle.apply(new MultipliedEvt(id, cmd.getScalar(), false));
    }

    @EventSourcingHandler
    public void on(MultipliedEvt evt) {
    	currentResult *= evt.getScalar();
    }

    @CommandHandler
    public void handle(RMultiplyCmd cmd) {
        AggregateLifecycle.apply(new RMultipliedEvt(id, cmd.getScalar()));
    }

    @EventSourcingHandler
    public void on(RMultipliedEvt evt) {
    	currentResult /= evt.getScalar();
    }

    @CommandHandler
    public void handle(DivideCmd cmd) {
        AggregateLifecycle.apply(new DividedEvt(id, cmd.getScalar(), false));
    }

    @EventSourcingHandler
    public void on(DividedEvt evt) {
    	currentResult /= evt.getScalar();
    }

    @CommandHandler
    public void handle(RDivideCmd cmd) {
        AggregateLifecycle.apply(new RDividedEvt(id, cmd.getScalar()));
    }

    @EventSourcingHandler
    public void on(RDividedEvt evt) {
    	currentResult *= evt.getScalar();
    }

    @SuppressWarnings("rawtypes")
	@CommandHandler
    public void handle(UndoCmd cmd) {
    	ArrayList<AbstractEvt> events = UndoRedo.getApparentEventStoreEvents(eventStore, id);
    	
    	AbstractEvt lastEvent = events.get(events.size() - 1);
    	
    	if(lastEvent instanceof IReversableEvt) {
    		AggregateLifecycle.apply(((IReversableEvt) lastEvent).getReverseEvt());
    	}
    }
    
    @SuppressWarnings("rawtypes")
	@CommandHandler
    public void handle(RedoCmd cmd) {
    	@SuppressWarnings("unchecked")
		ArrayList<AbstractEvt> events = new ArrayList<AbstractEvt>((Collection<AbstractEvt>) eventStore.readEvents(id).asStream().map(e -> { return e.getPayload(); }).collect(Collectors.toList()));
    	
    	boolean madeChange = true;
    	
    	while(madeChange) {
    		madeChange = false;
    		
	    	for(int i = events.size() - 1; i >= 0; i--) {
    			System.out.println(i);
	    		if(events.get(i) instanceof AbstractRedoneEvt && ((AbstractRedoneEvt) events.get(i)).isRedone()) {
	    			if(events.get(i - 1) instanceof AbstractReversedEvt && events.get(i).getClass() == ((AbstractReversedEvt) events.get(i - 1)).getReversedEvtClass()) {
		    			events.remove(i);
	    				events.remove(i - 1);
		    			System.out.println("test");
		    			madeChange = true;
		    			break;
	    			}
	    			
	    			continue;
	    		}
	    		
	    		break;
	    	}
    	}
    	
    	AbstractEvt lastEvent = events.get(events.size() - 1);
    	
    	if(lastEvent instanceof AbstractReversedEvt) {
    		AggregateLifecycle.apply(((AbstractReversedEvt) lastEvent).getReverseEvt());
    	}
    }
}