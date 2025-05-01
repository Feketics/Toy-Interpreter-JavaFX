package repository;

import exceptions.LogFileException;
import model.ProgramState;
import model.statement.CompStatement;
import model.statement.Statement;
import model.value.StringValue;
import model.value.Value;
import utils.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository
{
    private List<ProgramState> repo;
    private String logFilePath;

    public Repository(String logFilePath) throws LogFileException
    {
        this.repo = new ArrayList<ProgramState>();
        this.logFilePath = "src/files/" + logFilePath;

        this.resetLogFile();
    }

    @Override
    public void add(ProgramState prog)
    {
        this.repo.add(prog);
    }

    @Override
    public void logProgramStateExec(ProgramState prog) throws LogFileException
    {
        try
        {
            PrintWriter logfile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            logfile.println("Program ID: " + prog.getId());

            logfile.println("\nExecution Stack:\n");
            IExecutionStack<Statement> execStack = prog.getExecstack();
            IExecutionStack<Statement> tempStack = new ExecutionStack<>();
            List<Statement> tempList;

            tempList = execStack.getReversed().reversed();
            for(Statement statement : tempList)
            {
                tempStack.push(statement.deepCopy());
            }

            boolean first = true; //c
            while(!tempStack.isEmpty())
            {
                Statement tempStatement = tempStack.pop();
                if(tempStatement instanceof CompStatement comp)
                {
                    if(first) //c
                        return;//c

                    Statement s1 = comp.getFirst();
                    Statement s2 = comp.getSecond();
                    tempStack.push(s2);
                    tempStack.push(s1);
                }
                else
                {
                    logfile.println(tempStatement.toString());
                }
                first = false;//c
            }

            logfile.println("\nSymbol Table:\n");
            ISymTable<String, Value> symTbl = prog.getSymtbl();
            for(String s: symTbl.keys())
            {
                logfile.println(s + " --> " + symTbl.lookUp(s).toString());
            }

            logfile.println("\nOutput:\n");
            IOutput<Value> output = prog.getOutput();
            for(Value v: output.getList())
            {
                logfile.println(v.toString());
            }

            logfile.println("\nFile Table:\n");
            IFileTable<StringValue, BufferedReader> fileTable = prog.getFileTable();
            for(StringValue s: fileTable.keys())
            {
                logfile.println(s.getValue());
            }

            logfile.println("\nHeap:\n");
            IHeap<Integer, Value> heap = prog.getHeap();
            for(Integer i : heap.keys())
            {
                logfile.println(i.toString() + " --> " + heap.LookUp(i).toString());
            }

            logfile.println("\n_____________________________\n");

            logfile.close();

        }
        catch(IOException e)
        {
            throw new LogFileException(e.getMessage());
        }
    }

    @Override
    public void resetLogFile() throws LogFileException
    {
        File logFile = new File(this.logFilePath);
        try
        {
            if (logFile.exists())
            {
                new PrintWriter(new BufferedWriter(new FileWriter(logFile, false))).close();
            }
            else
            {
                logFile.createNewFile();
            }
        }
        catch (IOException e)
        {
            throw new LogFileException("Error initializing log file: " + logFilePath + " " + e.getMessage());
        }
    }

    @Override
    public void setPrgList(List<ProgramState> prgList)
    {
        this.repo = prgList;
    }

    @Override
    public List<ProgramState> getPrgList()
    {
        return this.repo;
    }
}
