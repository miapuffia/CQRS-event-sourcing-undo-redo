package rrioja.query.calculatorHistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import rrioja.UndoRedo;
import rrioja.event.AbstractEvt;
import rrioja.event.AbstractReversedEvt;

public class CalculatorHistory {
    private String id;
    private ArrayList<String> history;
    
    public CalculatorHistory(String id, Integer initial) {
        this.id = id;
        this.history = new ArrayList<String>();
        this.history.add(initial + "");
    }

    @SuppressWarnings("unchecked")
	public CalculatorHistory(String id, ArrayList<String> history) {
        this.id = id;
        this.history = (ArrayList<String>) history.clone();
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getHistory() {
        return history;
    }
    
    public String getFormattedHistory() {
    	StringBuilder sb = new StringBuilder();
    	
    	for(String line : history) {
    		sb.append(line + "\n");
    	}
    	
    	return sb.toString().trim();
    }
    
    public String getFormattedApparentHistory(EventStore eventStore) {
    	StringBuilder sb = new StringBuilder();
    	
    	for(AbstractEvt event : UndoRedo.getApparentEventStoreEvents(eventStore, id)) {
    		sb.append(event.toString() + "\n");
    	}
    	
    	return sb.toString().trim();
    }

    public CalculatorHistory addScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("+" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    public CalculatorHistory reverseAddScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("(R) +" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    public CalculatorHistory multiplyScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("*" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    public CalculatorHistory reverseMultiplyScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("(R) *" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    public CalculatorHistory divideScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("/" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    public CalculatorHistory reverseDivideScalar(Integer scalar) {
    	@SuppressWarnings("unchecked")
		ArrayList<String> newHistory = (ArrayList<String>) history.clone();
    	newHistory.add("(R) /" + scalar);
    	
        return new CalculatorHistory(id, newHistory);
    }

    @Override
    public String toString() {
        return "CardSummary{" +
                "id = '" + id + '\'' +
                ", history = " + history +
                '}';
    }
}