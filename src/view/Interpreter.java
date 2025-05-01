package view;

import controller.Controller;
import model.ProgramState;
import model.expressions.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;
import utils.*;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedReader;

public class Interpreter
{
    public static void main(String[] args)
    {
        try
        {
            Statement ex1= new CompStatement(new VarDeclStatement("v",new IntType()),
                    new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VariableExpression("v"))));

            ex1.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack1 = new ExecutionStack<>();
            ISymTable<String, Value> symT1 = new SymTable<>();
            IOutput<Value> output1 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable1 = new FileTable<>();
            IHeap<Integer, Value> heap1 = new Heap<>();
            ProgramState prgState1 = new ProgramState(stack1, symT1, output1, fileTable1, heap1, ex1);

            IRepository repo1 = new Repository("ex1.log");
            repo1.add(prgState1);
            Controller serv1 = new Controller(repo1);
            serv1.setDisplayPrgStateFlagTrue();


            Statement ex2 = new CompStatement(new VarDeclStatement("a",new IntType()),
                    new CompStatement(new VarDeclStatement("b",new IntType()),
                            new CompStatement(new AssignStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntValue(2)),
                                    new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                            new ValueExpression(new IntValue(5))))),
                                    new CompStatement(new AssignStatement("b",new ArithmeticExpression("+", new VariableExpression("a"),
                                            new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

            ex2.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack2 = new ExecutionStack<>();
            ISymTable<String, Value> symT2 = new SymTable<>();
            IOutput<Value> output2 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable2 = new FileTable<>();
            IHeap<Integer, Value> heap2 = new Heap<>();
            ProgramState prgState2 = new ProgramState(stack2, symT2, output2, fileTable2, heap2, ex2);

            IRepository repo2 = new Repository("ex2.log");
            repo2.add(prgState2);
            Controller serv2 = new Controller(repo2);
            serv2.setDisplayPrgStateFlagTrue();


            Statement ex3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
                    new CompStatement(new VarDeclStatement("v", new IntType()),
                            new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                    new CompStatement(new IfStatement(new VariableExpression("a"),
                                            new AssignStatement("v",new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));

            ex3.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack3 = new ExecutionStack<>();
            ISymTable<String, Value> symT3 = new SymTable<>();
            IOutput<Value> output3 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable3 = new FileTable<>();
            IHeap<Integer, Value> heap3 = new Heap<>();
            ProgramState prgState3 = new ProgramState(stack3, symT3, output3, fileTable3, heap3, ex3);

            IRepository repo3 = new Repository("ex3.log");
            repo3.add(prgState3);
            Controller serv3 = new Controller(repo3);
            serv3.setDisplayPrgStateFlagTrue();


            Statement ex4 = new CompStatement(new VarDeclStatement("varf", new StringType()),
                    new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompStatement(new VarDeclStatement("varc", new IntType()), new CompStatement(
                                    new OpenRFileStatement(new VariableExpression("varf")), new CompStatement(
                                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                    new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                            new CompStatement(new ReadFileStatement(new VariableExpression("varf"),
                                                    "varc"), new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                    new CloseRFileStatement(new VariableExpression("varf"))))))))));

            ex4.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack4 = new ExecutionStack<>();
            ISymTable<String, Value> symT4 = new SymTable<>();
            IOutput<Value> output4 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable4 = new FileTable<>();
            IHeap<Integer, Value> heap4 = new Heap<>();
            ProgramState prgState4 = new ProgramState(stack4, symT4, output4, fileTable4, heap4, ex4);

            IRepository repo4 = new Repository("ex4.log");
            repo4.add(prgState4);
            Controller serv4 = new Controller(repo4);
            serv4.setDisplayPrgStateFlagTrue();


            Statement ex5 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                            new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                            new PrintStatement(new VariableExpression("v")))));

            ex5.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack5 = new ExecutionStack<>();
            ISymTable<String, Value> symT5 = new SymTable<>();
            IOutput<Value> output5 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable5 = new FileTable<>();
            IHeap<Integer, Value> heap5 = new Heap<>();
            ProgramState prgState5 = new ProgramState(stack5, symT5, output5, fileTable5, heap5, ex5);

            IRepository repo5 = new Repository("ex5.log");
            repo5.add(prgState5);
            Controller serv5 = new Controller(repo5);
            serv5.setDisplayPrgStateFlagTrue();


            Statement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(new NewStatement("a", new VariableExpression("v")),
                            new CompStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

            ex6.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack6 = new ExecutionStack<>();
            ISymTable<String, Value> symT6 = new SymTable<>();
            IOutput<Value> output6 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable6 = new FileTable<>();
            IHeap<Integer, Value> heap6 = new Heap<>();
            ProgramState prgState6 = new ProgramState(stack6, symT6, output6, fileTable6, heap6, ex6);

            IRepository repo6 = new Repository("ex6.log");
            repo6.add(prgState6);
            Controller serv6 = new Controller(repo6);
            serv6.setDisplayPrgStateFlagTrue();


            Statement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(new NewStatement("a", new VariableExpression("v")),
                            new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression("+",
                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

            ex7.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack7 = new ExecutionStack<>();
            ISymTable<String, Value> symT7 = new SymTable<>();
            IOutput<Value> output7 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable7 = new FileTable<>();
            IHeap<Integer, Value> heap7 = new Heap<>();
            ProgramState prgState7 = new ProgramState(stack7, symT7, output7, fileTable7, heap7, ex7);

            IRepository repo7 = new Repository("ex7.log");
            repo7.add(prgState7);
            Controller serv7 = new Controller(repo7);
            serv7.setDisplayPrgStateFlagTrue();


            Statement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                            new PrintStatement(new ArithmeticExpression("+", new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

            ex8.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack8 = new ExecutionStack<>();
            ISymTable<String, Value> symT8 = new SymTable<>();
            IOutput<Value> output8 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable8 = new FileTable<>();
            IHeap<Integer, Value> heap8 = new Heap<>();
            ProgramState prgState8 = new ProgramState(stack8, symT8, output8, fileTable8, heap8, ex8);

            IRepository repo8 = new Repository("ex8.log");
            repo8.add(prgState8);
            Controller serv8 = new Controller(repo8);
            serv8.setDisplayPrgStateFlagTrue();


            Statement ex9 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                            new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                            new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(40))),
                                                            new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))))))));

            ex9.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack9 = new ExecutionStack<>();
            ISymTable<String, Value> symT9 = new SymTable<>();
            IOutput<Value> output9 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable9 = new FileTable<>();
            IHeap<Integer, Value> heap9 = new Heap<>();
            ProgramState prgState9 = new ProgramState(stack9, symT9, output9, fileTable9, heap9, ex9);

            IRepository repo9 = new Repository("ex9.log");
            repo9.add(prgState9);
            Controller serv9 = new Controller(repo9);
            serv9.setDisplayPrgStateFlagTrue();


            Statement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                    new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                            new CompStatement(new ForkStatement(new CompStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                    new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))), new CompStatement(new PrintStatement(new VariableExpression("v")),
                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

            ex10.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack10 = new ExecutionStack<>();
            ISymTable<String, Value> symT10 = new SymTable<>();
            IOutput<Value> output10 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable10 = new FileTable<>();
            IHeap<Integer, Value> heap10 = new Heap<>();
            ProgramState prgState10 = new ProgramState(stack10, symT10, output10, fileTable10, heap10, ex10);

            IRepository repo10 = new Repository("ex10.log");
            repo10.add(prgState10);
            Controller serv10 = new Controller(repo10);
            serv10.setDisplayPrgStateFlagTrue();


            Statement ex11 = new CompStatement(new VarDeclStatement("a1", new RefType(new IntType())), new CompStatement(new VarDeclStatement("a2", new RefType(new RefType(new IntType()))),
                    new CompStatement(new NewStatement("a1", new ValueExpression(new IntValue(10))), new CompStatement(new NewStatement("a2", new VariableExpression("a1")),
                            new CompStatement(new VarDeclStatement("a3", new RefType(new IntType())), new CompStatement(new NewStatement("a3", new ValueExpression(new IntValue(15))),
                                    new CompStatement(new ForkStatement(new CompStatement(new VarDeclStatement("b", new RefType(new IntType())),
                                            new CompStatement(new NewStatement("a3", new ValueExpression(new IntValue(20))), new CompStatement(new NewStatement("b", new ValueExpression(new IntValue(30))),
                                                    new CompStatement(new NewStatement("b", new ValueExpression(new IntValue(31))), new NopStatement()))))),
                                            new CompStatement(new ForkStatement(new CompStatement(new VarDeclStatement("b", new RefType(new IntType())),
                                            new CompStatement(new NewStatement("a1", new ValueExpression(new IntValue(134))), new CompStatement(new NewStatement("b", new ValueExpression(new IntValue(111))),
                                                    new CompStatement(new NewStatement("b", new ValueExpression(new IntValue(2))), new NopStatement()))))), new NewStatement("a1", new ValueExpression(new IntValue(99)))))))))));

            ex11.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack11 = new ExecutionStack<>();
            ISymTable<String, Value> symT11 = new SymTable<>();
            IOutput<Value> output11 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable11 = new FileTable<>();
            IHeap<Integer, Value> heap11 = new Heap<>();
            ProgramState prgState11 = new ProgramState(stack11, symT11, output11, fileTable11, heap11, ex11);

            IRepository repo11 = new Repository("ex11.log");
            repo11.add(prgState11);
            Controller serv11 = new Controller(repo11);
            serv11.setDisplayPrgStateFlagTrue();


            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("ex1", ex1.toString(), serv1));
            menu.addCommand(new RunExample("ex2", ex2.toString(), serv2));
            menu.addCommand(new RunExample("ex3", ex3.toString(), serv3));
            menu.addCommand(new RunExample("ex4", ex4.toString(), serv4));
            menu.addCommand(new RunExample("ex5", ex5.toString(), serv5));
            menu.addCommand(new RunExample("ex6", ex6.toString(), serv6));
            menu.addCommand(new RunExample("ex7", ex7.toString(), serv7));
            menu.addCommand(new RunExample("ex8", ex8.toString(), serv8));
            menu.addCommand(new RunExample("ex9", ex9.toString(), serv9));
            menu.addCommand(new RunExample("ex10", ex10.toString(), serv10));
            menu.addCommand(new RunExample("ex11", ex11.toString(), serv11));
            menu.show();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

