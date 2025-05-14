package controller;

import model.ProgramState;
import model.statement.Statement;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import exceptions.ExecutionException;
import utils.Heap;
import utils.IHeap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller
{
    private IRepository repository;
    private ExecutorService executor;
    private boolean displayPrgStateFlag;
    private Statement originalProgram;
    private ProgramState finalState;

    public Controller(IRepository repository, Statement originalProgram)
    {
        this.repository = repository;
        this.displayPrgStateFlag = false;
        this.originalProgram = originalProgram;
        this.finalState = repository.getPrgList().getFirst();
    }

    public List<ProgramState> getData()
    {
        return this.repository.getPrgList();
    }

    public String display()
    {
        return this.originalProgram.toString();
    }

    public void setDisplayPrgStateFlagTrue()
    {
        this.displayPrgStateFlag = true;
    }

    public void setDisplayPrgStateFlagFalse()
    {
        this.displayPrgStateFlag = false;
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap)
    {
        Map<Integer, Value> newHeap = new HashMap<>();

        for(Integer i: symTableAddr)
        {
            if(heap.containsKey(i))
                newHeap.put(i, heap.get(i));

            Value v = heap.get(i);
            if(v == null)
                continue;

            Value v1 = v.deepCopy();

            while(v1 instanceof RefValue v2)
            {
                if(heap.containsKey(v2.getAddress()))
                {
                    newHeap.put(v2.getAddress(), heap.get(v2.getAddress()));
                    v1 = heap.get(v2.getAddress());
                }
                else
                    break;
            }
        }

        return newHeap;
    }

    List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues)
    {
        return symTableValues.stream().filter(v-> v instanceof RefValue).map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();}).collect(Collectors.toList());
    }

    List<Integer> getAllAddresses(List<ProgramState> inPrgList)
    {
        Set<Integer> listMerger = new HashSet<>();

        for(ProgramState p: inPrgList)
            listMerger.addAll(getAddrFromSymTable(p.getSymtbl().getContent().values()));

        return listMerger.stream().toList();
    }

    public void oneStepForAllPrg(List<ProgramState> programList) throws InterruptedException
    {
        programList.forEach(prg->repository.logProgramStateExec(prg));

        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState p)->(Callable<ProgramState>)(p::oneStep))
                .toList();

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future->{try{return future.get();}catch(Exception e){throw new ExecutionException("An error occurred during program execution.");}})
                .filter(Objects::nonNull)
                .toList();

        programList.addAll(newProgramList);

        programList.forEach(prg->repository.logProgramStateExec(prg));

        repository.setPrgList(programList);
    }

    public void allStep() throws InterruptedException
    {
        executor= Executors.newFixedThreadPool(2);

        List<ProgramState> programList = removeCompletedPrg(repository.getPrgList());
        while(!programList.isEmpty())
        {
            programList.getFirst().getHeap().setContent(safeGarbageCollector(getAllAddresses(programList), programList.getFirst().getHeap().getContent()));
            oneStepForAllPrg(programList);
            programList.getFirst().getHeap().setContent(safeGarbageCollector(getAllAddresses(programList), programList.getFirst().getHeap().getContent()));
            programList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();
        repository.setPrgList(programList);
    }

    public void oneStep() throws InterruptedException
    {
        executor= Executors.newFixedThreadPool(2);

        List<ProgramState> programList = removeCompletedPrg(repository.getPrgList());

        programList.getFirst().getHeap().setContent(safeGarbageCollector(getAllAddresses(programList), programList.getFirst().getHeap().getContent()));
        oneStepForAllPrg(programList);
        programList.getFirst().getHeap().setContent(safeGarbageCollector(getAllAddresses(programList), programList.getFirst().getHeap().getContent()));
        this.finalState = programList.getFirst();
        programList = removeCompletedPrg(repository.getPrgList());

        executor.shutdownNow();
        repository.setPrgList(programList);
    }

    public ProgramState getFinalState()
    {
        return finalState;
    }
}
