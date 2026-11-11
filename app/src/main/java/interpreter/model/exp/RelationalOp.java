package interpreter.model.exp;

public enum RelationalOp {
    LESS("<"), LESS_EQUAL("<="), EQUAL("=="), NOT_EQUAL("!="), GREATER(">"), GREATER_EQUAL(">=");

    private final String symbol;

    RelationalOp(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
