package model.statement;

import exceptions.InvalidTypeException;
import model.ProgramState;
import model.expressions.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.ISymTable;
import utils.IExecutionStack;

public class IfStatement implements Statement
{
    private Expression expression;
    private Statement thenStatement;
    private Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException
    {
        IExecutionStack<Statement> stack = state.getExecstack();
        ISymTable<String, Value> symTbl = state.getSymtbl();
        Value v = this.expression.eval(symTbl, state.getHeap());

        if(!(v instanceof BoolValue bv))
            throw new InvalidTypeException("Invalid expression in if statement");

        if(bv.getValue())
        {
            stack.push(thenStatement);
        }
        else
        {
            stack.push(elseStatement);
        }
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new BoolType()))
        {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
        {
            throw new InvalidTypeException("If condition does not evaluate to boolean");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString()
    {
        return "IF " + expression.toString() + " THEN " + thenStatement.toString() + " ELSE " + elseStatement.toString();
    }
}
