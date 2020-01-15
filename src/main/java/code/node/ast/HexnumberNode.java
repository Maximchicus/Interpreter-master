package code.node.ast;

import code.tokens.Token;

public class HexnumberNode extends ExpressionNode {

    public final Token number;

    public HexnumberNode(Token number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.text;
    }
}
