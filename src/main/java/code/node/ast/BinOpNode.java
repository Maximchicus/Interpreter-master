package code.node.ast;

import code.tokens.Token;

public class BinOpNode extends ExpressionNode {

    public final Token op;
    public final ExpressionNode left;
    public final ExpressionNode right;

    public BinOpNode(Token op, ExpressionNode left, ExpressionNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + op.text + right.toString() + ")";
    }
}
