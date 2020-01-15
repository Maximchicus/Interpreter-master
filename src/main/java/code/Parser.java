package code;

import java.util.*;
import java.util.List;
import code.node.ast.*;
import code.node.stmt.*;
import java.util.Arrays;
import code.tokens.Token;
import code.tokens.TokenType;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private void error(String message) {
        if (pos < tokens.size()) {
            Token t = tokens.get(pos);
            throw new RuntimeException(message + " в строке: " + t.row + ", позиции: " + t.column + ".");
        } else {
            throw new RuntimeException(message + " в конце файла");
        }
    }

    private Token match(TokenType... expected) {
        if (pos < tokens.size()) {
            Token curr = tokens.get(pos);
            if (Arrays.asList(expected).contains(curr.type)) {
                pos++;
                return curr;
            }
        }
        return null;
    }

    private Token require(TokenType... expected) {
        Token t = match(expected);
        if (t == null)
            error("Ожидалось " + Arrays.toString(expected));
        return t;
    }

    public List<StatementNode> parse() {
        List<StatementNode> program = new ArrayList<>();
        while (pos < tokens.size())
            program.add(parseStatement());
        return program;
    }

    public StatementNode parseStatement() {
        Token op = require(TokenType.WHILE, TokenType.ID, TokenType.PRINT);
        switch (op.type) {
            case ID:
                Token token = require(TokenType.DEC, TokenType.INC);
                ExpressionNode e = new UnOpNode(token, new VarNode(op));
                require(TokenType.END);
                return new VariableNode(op, e);
            case PRINT:
                Token id = require(TokenType.ID);
                require(TokenType.END);
                return new PrintNode(op, new VarNode(id));
            case WHILE:
                ExpressionNode comp = parseCompare();
                require(TokenType.DO);
                List<StatementNode> body = new ArrayList<>();
                while (match(TokenType.DONE) == null) {
                    if ( pos < tokens.size()) {
                        StatementNode stmt = parseStatement();
                        body.add(stmt);
                    } else error("Ожидался DONE");
                }
                require(TokenType.END);
                return new WhileNode(op, comp, body);
        }
        return null;
    }

    public ExpressionNode parseCompare() {
        ExpressionNode e1 = parseExpression();
        Token op;
        while ((op = match(TokenType.MORE, TokenType.LESS, TokenType.EQUAL)) != null) {
            ExpressionNode e2 = parseExpression();
            e1 = new BinOpNode(op, e1, e2);
        }
        return e1;
    }

    public ExpressionNode parseExpression() {
        ExpressionNode e1 = parseSlag();
        Token op;
        while ((op = match(TokenType.ADD, TokenType.SUB)) != null) {
            ExpressionNode e2 = parseSlag();
            e1 = new BinOpNode(op, e1, e2);
        }
        return e1;
    }

    public ExpressionNode parseSlag() {
        ExpressionNode e1 = parsePar();
        Token op;
        while ((op = match(TokenType.MUL, TokenType.DIV)) != null) {
            ExpressionNode e2 = parsePar();
            e1 = new BinOpNode(op, e1, e2);
        }
        return e1;
    }

    private ExpressionNode parsePar() {
        if (match(TokenType.LPAR) != null) {
            ExpressionNode e = parseExpression();
            require(TokenType.RPAR);
            return e;
        } else {
            return parseElem();
        }
    }

    private ExpressionNode parseElem() {
        Token hexnum = match(TokenType.HEXNUMBER);
        if (hexnum != null)
            return new HexnumberNode(hexnum);

        /*Token num = match(TokenType.NUMBER);
        if (num != null)
            return new NumberNode(num);*/

        Token id = match(TokenType.ID);
        if (id != null)
            return new VarNode(id);

        Token unOp = match(TokenType.SUB);
        if (unOp != null)
            return new UnOpNode(unOp, parseExpression());

        error("Ожидается число или переменная");
        return null;
    }

}