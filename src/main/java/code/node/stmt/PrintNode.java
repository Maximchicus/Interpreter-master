package code.node.stmt;

import code.tokens.Token;
import code.node.ast.ExpressionNode;

public class PrintNode extends StatementNode {
    public final Token token;
    public final ExpressionNode body;

    public PrintNode(Token token, ExpressionNode body) {
        this.token = token;
        this.body = body;
    }
}
