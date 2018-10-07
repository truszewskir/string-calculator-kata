package pl.rafaltruszewski.stringcalculator;

public class NegativesNotAllowed extends RuntimeException {

    public NegativesNotAllowed(String message) {
        super(message);
    }
}
