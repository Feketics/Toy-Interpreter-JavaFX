package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDeclaredException;
import model.ProgramState;
import model.expressions.Expression;
import model.value.Value;
import model.type.Type;
import utils.ITypeEnv;
import utils.ISymTable;

public class AssignStatement implements Statement
{
    private String id;
    private Expression expression;

    public AssignStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException, VariableNotDeclaredException
    {
        ISymTable<String, Value> symTbl= state.getSymtbl();
        if(symTbl.isDefined(id))
        {
            Value val = expression.eval(symTbl, state.getHeap());
            Type typeId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typeId))
            {
                symTbl.update(id, val);
            }
            else
            {
                throw new InvalidTypeException("declared type of variable '" + id + "' and type of the assigned expression do not match");
            }
        }
        else
        {
            throw new VariableNotDeclaredException("the used variable '" + id + "' was not declared before");
        }
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        Type varType, expType;
        varType = typeEnv.lookUp(id);
        expType = this.expression.typeCheck(typeEnv);
        if(varType.equals(expType))
        {
            return typeEnv;
        }
        else
        {
            throw new InvalidTypeException("Right hand side and left hand side have different types in assignment operation.");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new AssignStatement(id, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return id + " = " + expression.toString();
    }
}
