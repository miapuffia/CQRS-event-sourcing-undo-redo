package rrioja.query.calculatorSummary;

public class CalculatorSummary {
    private final String id;
    private final Integer initial;
    private final Integer result;

    public CalculatorSummary(String id, Integer initial) {
        this.id = id;
        this.initial = initial;
        this.result = initial;
    }
    
    public CalculatorSummary(String id, Integer initial, Integer result) {
        this.id = id;
        this.initial = initial;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public Integer getInitial() {
        return initial;
    }

    public Integer getResult() {
        return result;
    }

    public CalculatorSummary addScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result + scalar);
    }

    public CalculatorSummary reverseAddScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result - scalar);
    }

    public CalculatorSummary multiplyScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result * scalar);
    }

    public CalculatorSummary reverseMultiplyScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result / scalar);
    }

    public CalculatorSummary divideScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result / scalar);
    }

    public CalculatorSummary reverseDivideScalar(Integer scalar) {
        return new CalculatorSummary(id, initial, result * scalar);
    }

    @Override
    public String toString() {
        return "CardSummary{" +
                "id = '" + id + '\'' +
                ", initial = " + initial +
                ", result = " + result +
                '}';
    }
}