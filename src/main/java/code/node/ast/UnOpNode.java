package code.node.ast;

import code.tokens.Token;

public class UnOpNode extends ExpressionNode {
    public final Token token;
    public final ExpressionNode expr;

    public UnOpNode(Token token, ExpressionNode expr) {
        this.token = token;
        this.expr = expr;
    }
}
