package code.node.ast;

import code.tokens.Token;

public class VarNode extends ExpressionNode {

    public final Token id;

    public VarNode(Token id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.text;
    }
}
