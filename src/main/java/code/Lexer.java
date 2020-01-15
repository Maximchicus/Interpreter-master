package code;

import code.tokens.Token;
import code.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Lexer {

    public int row = 1;
    public int col = 1;
    private int pos = 0;
    private final String src;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String src) {
        this.src = src;
    }

    private boolean nextToken() {
        if (pos >= src.length()) return false;

        for (TokenType tt : TokenType.values()) {
            Matcher m = tt.pattern.matcher(src);
            m.region(pos, src.length());

            if (m.lookingAt()) {
                Token t = new Token(tt, m.group(), pos, row, col);
                tokens.add(t);
                pos = m.end();

                if (tt == TokenType.ENDL) {
                    row++;
                    col = 1;
                } else col += m.group().length();
                return true;
            }
        }
        throw new RuntimeException("Неожиданный символ " + src.charAt(pos) + " " + row + ":" + col);
    }

    public List<Token> lex() {
        while (nextToken());
        return tokens;
    }

    public static void main(String[] args) {
        String text = "while x > 0.999999999994458E-4 do\n" +
                "  print x;\n" +
                "  x--;\n" +
                "done;";
        Lexer l = new Lexer(text);
        List<Token> tokens = l.lex();
        tokens.removeIf(t -> t.type == TokenType.SPACE);
        for (Token t : tokens) {
            System.out.println(t.type + " " + t.text + " - " + t.row + ":" + t.column);
        }
    }
}
