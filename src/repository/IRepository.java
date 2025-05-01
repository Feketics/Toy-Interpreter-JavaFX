package repository;

import model.ProgramState;

import java.util.List;

public interface IRepository
{
    void add(ProgramState prog);
    void logProgramStateExec(ProgramState prog);
    void resetLogFile();
    void setPrgList(List<ProgramState> prgList);
    List<ProgramState> getPrgList();
}
