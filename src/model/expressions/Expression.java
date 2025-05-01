package model.expressions;

import model.type.Type;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

public interface Expression
{
    Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap);

    Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap);

    Type typeCheck(ITypeEnv<String, Type> typeEnv);

    Expression deepCopy();
}
