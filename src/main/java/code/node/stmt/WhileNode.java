package code.node.stmt;

import code.tokens.Token;
import java.util.List;
import code.node.ast.ExpressionNode;

public class WhileNode extends StatementNode{

    public final Token token;
    public final ExpressionNode cobdition;
    public final List<StatementNode> body;

    public WhileNode(Token token, ExpressionNode cobdition, List<StatementNode> body) {
        this.token = token;
        this.cobdition = cobdition;
        this.body = body;
    }
}
