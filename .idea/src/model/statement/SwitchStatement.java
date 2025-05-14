package model.statement;

import exceptions.InvalidTypeException;
import model.ProgramState;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.type.IntType;
import model.type.Type;
import utils.ITypeEnv;

public class SwitchStatement implements Statement
{

    Expression expression, exp1, exp2;
    Statement statement1, statement2, statement3;

    public SwitchStatement(Expression expression, Expression exp1, Expression exp2, Statement statement1, Statement statement2, Statement statement3)
    {
        this.expression = expression;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {

        Statement newStatement = new IfStatement(new RelationalExpression("==", this.expression, this.exp1), this.statement1,
                new IfStatement(new RelationalExpression("==", this.expression, this.exp2), this.statement2, this.statement3));

        state.getExecstack().push(newStatement);

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        Type t1, t2, t3;
        t1 = expression.typeCheck(typeEnv);
        t2 = exp1.typeCheck(typeEnv);
        t3 = exp2.typeCheck(typeEnv);
        if(t1.equals(new IntType()))
        {
            if(t2.equals(new IntType()))
            {
                if(t3.equals(new IntType()))
                {
                    statement1.typeCheck(typeEnv.deepCopy());
                    statement2.typeCheck(typeEnv.deepCopy());
                    statement3.typeCheck(typeEnv.deepCopy());
                    return typeEnv;
                }
                else
                {
                    throw new InvalidTypeException("Expression 3 is not an integer type");
                }
            }
            else
            {
                throw new InvalidTypeException("Expression 2 is not an integer type");
            }
        }
        else
        {
            throw new InvalidTypeException("Expression 1 is not an integer type");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new SwitchStatement(expression.deepCopy(), exp1.deepCopy(), exp2.deepCopy(), statement1.deepCopy(), statement2.deepCopy(), statement3.deepCopy());
    }

    @Override
    public String toString()
    {
        return "Switch(" + expression.toString() + ")" + " case(" + exp1.toString() + "){" + statement1.toString() +"} case(" + exp2.toString() + "){" + statement2.toString() +"} default{" + statement3.toString() +"}";
    }
}
