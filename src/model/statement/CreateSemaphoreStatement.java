package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDeclaredException;
import javafx.util.Pair;
import model.ProgramState;
import model.expressions.Expression;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;
import utils.ISymTable;
import utils.ITypeEnv;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStatement implements Statement
{

    String variable;
    Expression expression;
    private static Lock lock = new ReentrantLock();

    public CreateSemaphoreStatement(String variable, Expression expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        lock.lock();
        try
        {
            Value v = expression.eval(state.getSymtbl(), state.getHeap());

            if (!(v instanceof IntValue iv)) {
                throw new InvalidTypeException("Expression given in CreateSemaphoreStatement is not an int");
            }

            if (!(state.getSymtbl().isDefined(variable)))
                throw new VariableNotDeclaredException("Variable " + variable + " is not defined");

            Value vv = state.getSymtbl().lookUp(variable);
            Type vtype = vv.getType();

            if (!(vtype.equals(new IntType())))
                throw new InvalidTypeException("Variable " + variable + " is not an int");

            state.getSymtbl().update(variable, new IntValue(state.getSemaphoreTable().put(new Pair<>(iv.getValue(), new ArrayList<>()))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        Type varType, expType;
        varType = typeEnv.lookUp(variable);
        expType = expression.typeCheck(typeEnv);
        if(!varType.equals(new IntType()))
            throw new InvalidTypeException("Variable " + variable + " is not an int");

        if(!expType.equals(new IntType()))
            throw new InvalidTypeException("Expression given in CreateSemaphoreStatement is not an int");

        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new CreateSemaphoreStatement(variable, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "CreateSemaphore(" + variable + ", " + expression.toString() + ")";
    }
}
