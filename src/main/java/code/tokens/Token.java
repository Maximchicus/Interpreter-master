package code.tokens;

public class Token {

    public final TokenType type;
    public final String text;
    public final int index;
    public final int row;
    public final int column;

    public Token(TokenType type, String text, int index, int row, int column) {
        this.type = type;
        this.text = text;
        this.index = index;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return text;
    }
}