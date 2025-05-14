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

public class WhileStatement implements Statement
{
    private Expression expression;
    private Statement statement;

    public WhileStatement(Expression expression, Statement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException
    {
        IExecutionStack<Statement> execstack = state.getExecstack();
        ISymTable<String, Value> symTbl = state.getSymtbl();

        Value v = this.expression.eval(symTbl, state.getHeap());

        if(!(v instanceof BoolValue bv))
        {
            throw new InvalidTypeException("Expression in while statement should result in a boolValue");
        }

        if(bv.getValue())
        {
            execstack.push(this);
            execstack.push(statement);
        }

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new BoolType()))
        {
            this.statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
        {
            throw new InvalidTypeException("Expression in while statement should result in a boolValue");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString()
    {
        return "while(" + expression.toString() + ") " + statement.toString();
    }
}
