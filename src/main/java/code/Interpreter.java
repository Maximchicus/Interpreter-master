package code;

import code.node.ast.*;
import code.node.stmt.PrintNode;
import code.node.stmt.StatementNode;
import code.node.stmt.VariableNode;
import code.node.stmt.WhileNode;
import java.util.List;
import java.util.Map;

public class Interpreter {

    private void error(String message){
            throw new RuntimeException(message);
    }

    public void evalProgram(List<StatementNode> program, Map<String, Integer> vars) {
        for ( StatementNode stmt: program) {
            evalStatement(stmt, vars);
        }
    }

    private void evalStatement( StatementNode stmt, Map<String, Integer> vars) {
        if (stmt instanceof WhileNode) {
            while(evalCompare(((WhileNode) stmt).cobdition, vars)) {
                for ( StatementNode s: ((WhileNode) stmt).body) {
                    evalStatement(s, vars);
                }
            }
        }
        if (stmt instanceof PrintNode) {
            System.out.println(evalExpression(((PrintNode) stmt).body, vars));
        }
        if (stmt instanceof VariableNode) {
            switch (((UnOpNode)(((VariableNode) stmt).expr)).token.type) {
                case INC:
                    vars.put(
                            ((VariableNode) stmt).token.text ,
                            (vars.get(((VariableNode) stmt).token.text) + 1)
                    );
                    break;
                case DEC:
                    vars.put(
                            ((VariableNode) stmt).token.text ,
                            (vars.get(((VariableNode) stmt).token.text) - 1)
                    );
                    break;
                default:
                    vars.put(
                            ((VariableNode) stmt).token.text ,
                            evalExpression(((VariableNode) stmt).expr, vars)
                    );
            }
        }
    }

    private boolean evalCompare( ExpressionNode comp, Map<String, Integer> vars) {
        int valueL = evalExpression(((BinOpNode)comp).left, vars);
        int valueR = evalExpression(((BinOpNode)comp).right, vars);

        switch (((BinOpNode)comp).op.type) {
            case EQUAL:
                return valueL == valueR;
            case MORE:
                return valueL > valueR;
            case LESS:
                return valueL < valueR;
            default:
                return false;
        }
    }

    private int evalExpression(ExpressionNode e, Map<String, Integer> vars) {
        if(e instanceof HexnumberNode) return Integer.parseInt(((HexnumberNode) e).number.text, 16);
        //if(e instanceof NumberNode) return Double.valueOf(((NumberNode) e).number.text);
        if(e instanceof VarNode) return vars.get(((VarNode) e).id.text);
        if(e instanceof UnOpNode) return -1 * evalExpression(((UnOpNode) e).expr ,vars);
        if(e instanceof BinOpNode) {
            int valueL = evalExpression(((BinOpNode)e).left, vars);
            int valueR = evalExpression(((BinOpNode)e).right, vars);

            switch (((BinOpNode) e).op.type) {
                case SUB:
                    return valueL - valueR;
                case ADD:
                    return valueL + valueR;
                case DIV:
                    if (valueR != 0) return valueL / valueR;
                    throw new ArithmeticException("/ by zero\n" +
                            "at " + ((BinOpNode)e).op.row + ":" + ((BinOpNode)e).op.column);
                case MUL:
                    return valueL * valueR;
            }
        }
        error("ошибка в evalExpression");
        return 0;
    }
}
