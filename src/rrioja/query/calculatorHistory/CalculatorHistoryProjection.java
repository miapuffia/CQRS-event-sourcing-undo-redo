package rrioja.query.calculatorHistory;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rrioja.event.*;

@Service
public class CalculatorHistoryProjection {
	private final QueryUpdateEmitter queryUpdateEmitter;
	
    private CalculatorHistory calculatorHistory;
    
    public CalculatorHistoryProjection(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") QueryUpdateEmitter queryUpdateEmitter
    ) {
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(IssuedEvt evt) {
    	calculatorHistory = new CalculatorHistory(evt.getId(), evt.getInitial());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(AddedEvt evt) {
    	calculatorHistory = calculatorHistory.addScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(RAddedEvt evt) {
    	calculatorHistory = calculatorHistory.reverseAddScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(MultipliedEvt evt) {
    	calculatorHistory = calculatorHistory.multiplyScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(RMultipliedEvt evt) {
    	calculatorHistory = calculatorHistory.reverseMultiplyScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(DividedEvt evt) {
    	calculatorHistory = calculatorHistory.divideScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @EventHandler
    public void on(RDividedEvt evt) {
    	calculatorHistory = calculatorHistory.reverseDivideScalar(evt.getScalar());
    	
    	queryUpdateEmitter.emit(FetchCalculatorHistoryQuery.class,
			                	query -> true,
			                	calculatorHistory);
    }

    @QueryHandler
    public CalculatorHistory fetch(FetchCalculatorHistoryQuery query) {
        return calculatorHistory;
    }
}