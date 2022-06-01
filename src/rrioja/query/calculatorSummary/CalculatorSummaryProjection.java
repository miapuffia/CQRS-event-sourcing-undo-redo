package rrioja.query.calculatorSummary;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rrioja.event.*;

@Service
public class CalculatorSummaryProjection {
	private final QueryUpdateEmitter queryUpdateEmitter;
	
    private CalculatorSummary calculatorSummary;
    
    public CalculatorSummaryProjection(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") QueryUpdateEmitter queryUpdateEmitter
    ) {
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(IssuedEvt evt) {
    	calculatorSummary = new CalculatorSummary(evt.getId(), evt.getInitial());
    }

    @EventHandler
    public void on(AddedEvt evt) {
    	calculatorSummary = calculatorSummary.addScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @EventHandler
    public void on(RAddedEvt evt) {
    	calculatorSummary = calculatorSummary.reverseAddScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @EventHandler
    public void on(MultipliedEvt evt) {
    	calculatorSummary = calculatorSummary.multiplyScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @EventHandler
    public void on(RMultipliedEvt evt) {
    	calculatorSummary = calculatorSummary.reverseMultiplyScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @EventHandler
    public void on(DividedEvt evt) {
    	calculatorSummary = calculatorSummary.divideScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @EventHandler
    public void on(RDividedEvt evt) {
    	calculatorSummary = calculatorSummary.reverseDivideScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorSummaryQuery.class,
			                	query -> true,
			                	calculatorSummary);
    }

    @QueryHandler
    public CalculatorSummary fetch(FetchCalculatorSummaryQuery query) {
        return calculatorSummary;
    }
}