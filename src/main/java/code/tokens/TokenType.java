package code.tokens;

import java.util.regex.Pattern;

public enum TokenType {
    //NUMBER("[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"),
    HEXNUMBER("[0-9][a-f0-9]*"),

    WHILE("while"),
    DONE("done"),
    DO("do"),
    PRINT("print"),
    END(";"),

    ID("[a-zA-Z_][a-zA-Z_0-9]*"),

    INC("\\+\\+"),
    DEC("--"),

    EQUAL("="),
    MORE(">"),
    LESS("<"),

    ADD("\\+"),
    SUB("-"),
    MUL("\\*"),
    DIV("/"),

    LPAR("\\("),
    RPAR("\\)"),
    SPACE("[ \t\r]+"),
    ENDL("\n");

    public final Pattern pattern;

    TokenType(String regexp) {
        pattern = Pattern.compile(regexp);
    }
}
