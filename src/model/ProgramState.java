package model;

import exceptions.ExecStackEmptyException;
import javafx.util.Pair;
import model.statement.Statement;
import model.value.StringValue;
import model.value.Value;
import utils.*;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState
{
    private static int freeID = 0;

    private static synchronized int getFreeID()
    {
        return freeID++;
    }

    private final int id;
    private ISymTable<String, Value> symtbl;
    private IExecutionStack<Statement> execstack;
    private IOutput<Value> output;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private Statement originalProgram;

    public ISymTable<String, Value> getSymtbl()
    {
        return symtbl;
    }

    public IExecutionStack<Statement> getExecstack()
    {
        return execstack;
    }

    public IOutput<Value> getOutput()
    {
        return output;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable()
    {
        return fileTable;
    }

    public ProgramState(IExecutionStack<Statement> execstack, ISymTable<String, Value> symtbl, IOutput<Value> output, IFileTable<StringValue, BufferedReader> fileTable, IHeap<Integer, Value> heap, ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable, Statement prg)
    {
        this.id = getFreeID();
        this.symtbl = symtbl;
        this.execstack = execstack;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphoreTable = semaphoreTable;
        this.originalProgram = prg.deepCopy();

        this.execstack.push(prg);
    }

    public ProgramState oneStep() throws ExecStackEmptyException
    {
        if(execstack.isEmpty())
            throw new ExecStackEmptyException("Execution stack is empty");

        Statement current = execstack.pop();
        return current.execute(this);
    }

    public boolean isNotCompleted()
    {
        return !this.execstack.isEmpty();
    }

    @Override
    public String toString()
    {
        return "Program ID: " + this.id + "\nExecstack: " + this.execstack.getReversed().toString() + "\nOutput: " + this.output.toString()
                + "\nSymtbl: " + this.symtbl.toString() + "\nFileTable: " + this.fileTable.toString() + "\nHeap: " + this.heap.toString() +
                "\nSemaphoreTable: " + this.semaphoreTable.toString() + "\n";
    }

    public Statement getOriginalProgram()
    {
        return originalProgram;
    }

    public void setSymtbl(ISymTable<String, Value> symtbl)
    {
        this.symtbl = symtbl;
    }

    public void setExecstack(IExecutionStack<Statement> execstack)
    {
        this.execstack = execstack;
    }

    public void setOutput(IOutput<Value> output)
    {
        this.output = output;
    }

    public void setOriginalProgram(Statement originalProgram)
    {
        this.originalProgram = originalProgram;
    }

    public void setFileTable(IFileTable<StringValue, BufferedReader> fileTable)
    {
        this.fileTable = fileTable;
    }

    public IHeap<Integer, Value> getHeap()
    {
        return heap;
    }

    public void setHeap(IHeap<Integer, Value> heap)
    {
        this.heap = heap;
    }

    public int getId()
    {
        return id;
    }

    public ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return semaphoreTable;
    }

    public void setSemaphoreTable(ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }
}
