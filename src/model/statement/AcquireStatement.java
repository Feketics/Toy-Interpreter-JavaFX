package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDeclaredException;
import javafx.util.Pair;
import model.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;
import utils.ITypeEnv;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements Statement
{
    private String variable;
    private static Lock lock = new ReentrantLock();

    public AcquireStatement(String variable)
    {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        lock.lock();
        try
        {
            if(!state.getSymtbl().isDefined(variable))
                throw new VariableNotDeclaredException("Variable" + variable + " is not defined");

            Value v = state.getSymtbl().lookUp(variable);
            if(!(v instanceof IntValue iv))
                throw new InvalidTypeException("Variable " + variable + " is not an integer value");

            Integer index = iv.getValue();

            if(!state.getSemaphoreTable().isDefined(index))
                throw new VariableNotDeclaredException("Semaphore with index " + index + " is not defined");

            Pair<Integer, List<Integer>> entry = state.getSemaphoreTable().LookUp(index);

            if(entry.getKey() > entry.getValue().size())
            {
                if(!entry.getValue().contains(state.getId()))
                {
                    entry.getValue().add(state.getId());
                    state.getSemaphoreTable().update(index, entry);
                }
            }
            else
            {
                state.getExecstack().push(this);
            }
        }
        catch (Exception e)
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
        Type varType = typeEnv.lookUp(variable);
        if(!varType.equals(new IntType()))
            throw new InvalidTypeException("Variable " + variable + " is not an integer value");
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new AcquireStatement(variable);
    }

    @Override
    public String toString()
    {
        return "Acquire(" + variable + ")";
    }
}
