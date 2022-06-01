package rrioja;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.AggregateConfigurer;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.queryhandling.SimpleQueryUpdateEmitter;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import rrioja.command.*;
import rrioja.query.calculatorHistory.*;
import rrioja.query.calculatorSummary.*;

public class GUI extends Application {
	private ConfigurableApplicationContext applicationContext;
	
	boolean skipped = false;
	
	@Override
	public void init() {
		String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(UndoRedo.class)
                .run(args);
	}
	
	@Override
	public void stop() {
	    this.applicationContext.close();
	    Platform.exit();
	}
    
    @Override
    public void start(Stage primaryStage) {
    	Label result = new Label("0");
    	Label checkpoint = new Label("0");
    	Label history = new Label(checkpoint.getText());
    	Label apparentHistory = new Label(checkpoint.getText());
    	Region spacer = new Region();

    	CommandGateway commandGateway = applicationContext.getBean(CommandGateway.class);
    	QueryGateway queryGateway = applicationContext.getBean(QueryGateway.class);
    	EventStore eventStore = applicationContext.getBean(EventStore.class);
    	
    	commandGateway.sendAndWait(new IssueCmd("calculator1", 0));
    	
    	SubscriptionQueryResult<CalculatorSummary, CalculatorSummary> fetchCalculatorSummaryQueryResult = queryGateway.subscriptionQuery(new FetchCalculatorSummaryQuery(), CalculatorSummary.class, CalculatorSummary.class);
    	
    	fetchCalculatorSummaryQueryResult.handle(cs -> Platform.runLater(() -> result.setText(cs.getResult() + "")), cs -> Platform.runLater(() -> result.setText(cs.getResult() + "")));
    	
    	SubscriptionQueryResult<CalculatorHistory, CalculatorHistory> fetchCalculatorHistoryQueryResult = queryGateway.subscriptionQuery(new FetchCalculatorHistoryQuery(), CalculatorHistory.class, CalculatorHistory.class);
    	
    	fetchCalculatorHistoryQueryResult.handle(cs -> Platform.runLater(() -> {
    		history.setText(cs.getFormattedHistory() + "");
    		apparentHistory.setText(cs.getFormattedApparentHistory(eventStore) + "");
    	}), cs -> Platform.runLater(() -> {
    		history.setText(cs.getFormattedHistory() + "");
    		apparentHistory.setText(cs.getFormattedApparentHistory(eventStore) + "");
    	}));
    	
    	history.setStyle("-fx-font-size: 20px;");
    	apparentHistory.setStyle("-fx-font-size: 20px;");
    	result.setStyle("-fx-font-size: 20px;");
        
        Button btnPlus1 = new Button();
        btnPlus1.setText("+ 1");
        btnPlus1.setStyle("-fx-font-size: 20px;");
        btnPlus1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	commandGateway.sendAndWait(new AddCmd("calculator1", 1, false));
            }
        });
    	
        Button btnMultiply2 = new Button();
        btnMultiply2.setText("x 2");
        btnMultiply2.setStyle("-fx-font-size: 20px;");
        btnMultiply2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	commandGateway.sendAndWait(new MultiplyCmd("calculator1", 2, false));
            }
        });
        
        Button btnDivide3 = new Button();
        btnDivide3.setText("/ 3");
        btnDivide3.setStyle("-fx-font-size: 20px;");
        btnDivide3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	commandGateway.sendAndWait(new DivideCmd("calculator1", 3, false));
            }
        });
        
        Button btnUndo = new Button();
        btnUndo.setText("Undo");
        btnUndo.setStyle("-fx-font-size: 20px;");
        btnUndo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	commandGateway.sendAndWait(new UndoCmd("calculator1"));
            }
        });
        
        Button btnRedo = new Button();
        btnRedo.setText("Redo");
        btnRedo.setStyle("-fx-font-size: 20px;");
        btnRedo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	commandGateway.sendAndWait(new RedoCmd("calculator1"));
            }
        });
        
        Button btnCheckpoint = new Button();
        btnCheckpoint.setText("Checkpoint");
        btnCheckpoint.setStyle("-fx-font-size: 20px;");
        btnCheckpoint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//replay.exec(new CheckpointCommand());
            }
        });
        
        Region spacer2 = new Region();
        Region spacer4 = new Region();
        
        VBox buttons = new VBox(10, btnPlus1, btnMultiply2, btnDivide3, spacer2, btnUndo, btnRedo/*, spacer4, btnCheckpoint*/);
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        
        Label title = new Label("Event store:");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        VBox eventStoreVBox = new VBox(title, history, spacer);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        Label title3 = new Label("Apparent event store:");
        title3.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label title4 = new Label("Result:");
        title4.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    	Region spacer6 = new Region();
        
        VBox apparentEventStore = new VBox(title3, apparentHistory, spacer6, title4, result);
        VBox.setVgrow(spacer6, Priority.ALWAYS);
        
        Region spacer3 = new Region();
        Region spacer5 = new Region();
        
        HBox root = new HBox(buttons, spacer3, eventStoreVBox, spacer5, apparentEventStore);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox.setHgrow(spacer5, Priority.ALWAYS);
        
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 600);

        primaryStage.setTitle("Undo Redo Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}