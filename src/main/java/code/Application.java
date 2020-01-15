package code;

import code.node.stmt.StatementNode;
import code.tokens.Token;
import code.tokens.TokenType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        String prog = "while x > 0f do\n" +
                "  print x;\n" +
                "  x++;\n" +
                "  while 0beef = 0dead do print x; done;\n" +
                "done;";
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 30);

        Lexer l = new Lexer(prog);
        List<Token> tokens = l.lex();
        tokens.removeIf(t -> t.type == TokenType.SPACE);
        tokens.removeIf(t -> t.type == TokenType.ENDL);

        Parser p = new Parser(tokens);
        List<StatementNode> stmts = p.parse();

        Interpreter i = new Interpreter();
        i.evalProgram(stmts, vars);
    }
}

/*
while x > 0 do
  print x;
  x--;
  while 1 = 0 do print x; done;
done;

*/
