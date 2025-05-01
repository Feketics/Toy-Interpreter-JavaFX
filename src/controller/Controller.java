package controller;

import model.ProgramState;
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

    public Controller(IRepository repository)
    {
        this.repository = repository;
        this.displayPrgStateFlag = false;
    }

    public void setDisplayPrgStateFlagTrue()
    {
        this.displayPrgStateFlag = true;
    }

    public void setDisplayPrgStateFlagFalse()
    {
        this.displayPrgStateFlag = false;
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap)
    {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    void conservativeGarbageCollector(List<ProgramState> programList)
    {
        Map<Integer, Value> newHeap = new HashMap<>();

        List<Integer> symTableAddr = getAllAddresses(programList);
        Map<Integer, Value> heap = programList.getFirst().getHeap().getContent();

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

        programList.getFirst().getHeap().setContent(newHeap);
    }


    Map<Integer, Value> safeGarbageCollector2(List<Integer> symTableAddr, Map<Integer, Value> heap)
    {
        Set<Integer> reachableAddresses = new HashSet<>(symTableAddr);

        boolean added;
        do {
            added = reachableAddresses.addAll(
                    heap.entrySet().stream()
                            .filter(entry -> reachableAddresses.contains(entry.getKey()))
                            .map(Map.Entry::getValue)
                            .filter(value -> value instanceof RefValue)
                            .map(value -> ((RefValue) value).getAddress())
                            .filter(heap::containsKey)
                            .toList()
            );
        } while (added);

        return heap.entrySet().stream()
                .filter(entry -> reachableAddresses.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
            //conservativeGarbageCollector(programList);
            programList.getFirst().getHeap().setContent(safeGarbageCollector(getAllAddresses(programList), programList.getFirst().getHeap().getContent()));
            oneStepForAllPrg(programList);
            programList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();
        repository.setPrgList(programList);
    }
}
