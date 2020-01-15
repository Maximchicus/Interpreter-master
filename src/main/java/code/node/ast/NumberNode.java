package code.node.ast;

import code.tokens.Token;

public class NumberNode extends ExpressionNode {

    public final Token number;

    public NumberNode(Token number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.text;
    }
}
