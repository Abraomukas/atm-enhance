package main;

public enum BankNote {
    FIVE(5), TEN(10), TWENTY(20), FIFTY(50), HUNDRED(100);

    public final int value;

    private BankNote(int value) {
        this.value = value;
    }
}
