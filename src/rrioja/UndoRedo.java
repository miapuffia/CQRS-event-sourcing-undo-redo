package rrioja;

import javafx.application.Application;
import rrioja.event.AbstractEvt;
import rrioja.event.AbstractReversedEvt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UndoRedo {
	@Bean
	public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
	    return EmbeddedEventStore.builder()
	            .storageEngine(storageEngine)
	            .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
	            .build();
	}

	// The `InMemoryEventStorageEngine` stores each event in memory
	@Bean
	public EventStorageEngine storageEngine() {
	    return new InMemoryEventStorageEngine();
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList<AbstractEvt> getApparentEventStoreEvents(EventStore eventStore, String id) {
		@SuppressWarnings("unchecked")
		ArrayList<AbstractEvt> events = new ArrayList<AbstractEvt>((Collection<AbstractEvt>) eventStore.readEvents(id).asStream().map(e -> { return e.getPayload(); }).collect(Collectors.toList()));
    	
    	boolean madeChange = true;
    	
    	while(madeChange) {
    		madeChange = false;
    		
	    	for(int i = 0; i < events.size(); i++) {
	    		if(events.get(i) instanceof AbstractReversedEvt) {
	    			if(((AbstractReversedEvt) events.get(i)).getReversedEvtClass().isInstance(events.get(i - 1))) {
	    				events.remove(i);
	    				events.remove(i - 1);
	    				
	    				madeChange = true;
	    				break;
	    			}
	    		}
	    	}
    	}
    	
    	return events;
	}

    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }
}