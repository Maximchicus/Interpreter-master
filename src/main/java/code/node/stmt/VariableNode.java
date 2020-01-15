package code.node.stmt;

import code.tokens.Token;
import code.node.ast.ExpressionNode;

public class VariableNode extends StatementNode {

    public final Token token;
    public final ExpressionNode expr;

    public VariableNode(Token token, ExpressionNode expr) {
        this.token = token;
        this.expr = expr;
    }
}
