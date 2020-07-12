package dev.code_n_roll.jcon.example;

public final class MyResponseJava {

    private final String stringField;
    private Double doubleField;

    public MyResponseJava(String stringField, Double doubleField) {
        this.stringField = stringField;
        this.doubleField = doubleField;
    }

    public String getStringField() {
        return stringField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }
}
