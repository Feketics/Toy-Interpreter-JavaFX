package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI extends Application
{

    private List<Controller> controllers;

    private void populateExamples()
    {
        this.controllers = new ArrayList<>();
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
            Controller serv1 = new Controller(repo1, ex1);
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
            Controller serv2 = new Controller(repo2, ex2);
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
            Controller serv3 = new Controller(repo3, ex3);
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
            Controller serv4 = new Controller(repo4, ex4);
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
            Controller serv5 = new Controller(repo5, ex5);
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
            Controller serv6 = new Controller(repo6, ex6);
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
            Controller serv7 = new Controller(repo7, ex7);
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
            Controller serv8 = new Controller(repo8, ex8);
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
            Controller serv9 = new Controller(repo9, ex9);
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
            Controller serv10 = new Controller(repo10, ex10);
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
            Controller serv11 = new Controller(repo11, ex11);
            serv11.setDisplayPrgStateFlagTrue();

            Statement ex12 = new CompStatement( new VarDeclStatement("a", new IntType()) , new CompStatement( new VarDeclStatement("b", new IntType()) , new CompStatement( new VarDeclStatement("c", new IntType()), new CompStatement( new AssignStatement("a", new ValueExpression(new IntValue(1))) , new CompStatement( new AssignStatement("b", new ValueExpression(new IntValue(2))) , new CompStatement( new AssignStatement("c", new ValueExpression(new IntValue(5))) , new CompStatement( new SwitchStatement(new ArithmeticExpression("*", new VariableExpression("a"), new ValueExpression(new IntValue(10))), new ArithmeticExpression("*", new VariableExpression("b"), new VariableExpression("c")), new ValueExpression(new IntValue(10)), new CompStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))), new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300)))) , new PrintStatement(new ValueExpression(new IntValue(300))))))))));

            ex12.typeCheck(new TypeEnv<>());

            IExecutionStack<Statement> stack12 = new ExecutionStack<>();
            ISymTable<String, Value> symT12 = new SymTable<>();
            IOutput<Value> output12 = new Output<>();
            IFileTable<StringValue, BufferedReader> fileTable12 = new FileTable<>();
            IHeap<Integer, Value> heap12 = new Heap<>();
            ProgramState prgState12 = new ProgramState(stack12, symT12, output12, fileTable12, heap12, ex12);

            IRepository repo12 = new Repository("ex12.log");
            repo12.add(prgState12);
            Controller serv12 = new Controller(repo12, ex12);
            serv12.setDisplayPrgStateFlagTrue();

            this.controllers.add(serv1);
            this.controllers.add(serv2);
            this.controllers.add(serv3);
            this.controllers.add(serv4);
            this.controllers.add(serv5);
            this.controllers.add(serv6);
            this.controllers.add(serv7);
            this.controllers.add(serv8);
            this.controllers.add(serv9);
            this.controllers.add(serv10);
            this.controllers.add(serv11);
            this.controllers.add(serv12);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private List<String> execStackParser(IExecutionStack<Statement> execStack)
    {
        IExecutionStack<Statement> tempStack = new ExecutionStack<>();
        List<Statement> tempList;
        List<String> finalList = new ArrayList<>();

        tempList = execStack.getReversed().reversed();
        for(Statement statement : tempList)
        {
            tempStack.push(statement.deepCopy());
        }

        while(!tempStack.isEmpty())
        {
            Statement tempStatement = tempStack.pop();
            if(tempStatement instanceof CompStatement comp)
            {
                Statement s1 = comp.getFirst();
                Statement s2 = comp.getSecond();
                tempStack.push(s2);
                tempStack.push(s1);
            }
            else
            {
                finalList.add(tempStatement.toString());
            }
        }
        return finalList;
    }

    @Override
    public void start(Stage primaryStage)
    {
        populateExamples(); // Populate controllers

        // First window: ListView for Controllers
        ListView<String> controllerListView = new ListView<>();
        ObservableList<String> controllerNames = FXCollections.observableArrayList();

        controllers.forEach(controller -> controllerNames.add(controller.display()));
        controllerListView.setItems(controllerNames);

        // Open main window on selection
        controllerListView.getSelectionModel().selectedIndexProperty().addListener((_, _, newValue) ->
        {
            int selectedIndex = newValue.intValue();
            if (selectedIndex >= 0 && selectedIndex < controllers.size())
            {
                Controller selectedController = controllers.get(selectedIndex);
                showMainWindow(selectedController);
            }
        });

        VBox firstLayout = new VBox(10, new Label("Select a Controller:"), controllerListView);
        firstLayout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(firstLayout, 300, 400));
        primaryStage.setTitle("Controller Selector");
        primaryStage.show();
    }

    private void showMainWindow(Controller controller)
    {
        Stage mainStage = new Stage();

        // Shared components

        TextField numberOfStatesField = new TextField(String.valueOf(controller.getData().size()));
        numberOfStatesField.setEditable(false);

        // fileTable - ListView
        ListView<String> fileTableView = new ListView<>(FXCollections.observableArrayList(
                controller.getData().getFirst().getFileTable().keys().stream().map(Object::toString).toList()));

        // heapTable - TableView
        TableView<Map.Entry<Integer, Value>> heapTableView = createHeapTableView(controller.getData().getFirst().getHeap().getContent());

        // outputList - ListView
        ListView<String> outputListView = new ListView<>(FXCollections.observableArrayList(
                controller.getData().getFirst().getOutput().getList().stream().map(Value::toString).toList()));

        // ProgramState IDs - ListView
        ListView<Integer> idListView = new ListView<>(FXCollections.observableArrayList(
                controller.getData().stream().map(ProgramState::getId).toList()));

        // Unique components (update on selection of ID)

        TableView<Map.Entry<String, Value>> symTableView = new TableView<>();
        ListView<String> execStackView = new ListView<>();

        // ID ListView event handler
        idListView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                ProgramState selectedState = controller.getData().stream().filter(ps -> ps.getId() == newValue).findFirst().orElse(null);
                if (selectedState != null)
                {
                    symTableView.setItems(FXCollections.observableArrayList(selectedState.getSymtbl().getContent().entrySet()));
                    execStackView.setItems(FXCollections.observableArrayList(
                            execStackParser(selectedState.getExecstack()).stream().map(Object::toString).toList()));
                }
            }
        });

        // TableView setup for symTable
        symTableView.getColumns().addAll(createStringColumn("Variable Name", Map.Entry::getKey),
                createStringColumn("Value", entry -> entry.getValue().toString()));

        // Button
        Button oneStepButton = new Button("Run one step");
        oneStepButton.setOnAction(event ->
        {
            try
            {
                controller.oneStep();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }


            List<ProgramState> updatedStates = controller.getData();

            if (!updatedStates.isEmpty())
            {
                // Fetch the first program state
                ProgramState firstState = updatedStates.getFirst();

                // Clear and repopulate data

                heapTableView.getItems().clear();
                heapTableView.getItems().addAll(firstState.getHeap().getContent().entrySet());

                outputListView.getItems().clear();
                outputListView.getItems().addAll(
                        firstState.getOutput().getList().stream().map(Object::toString).toList());

                fileTableView.getItems().clear();
                fileTableView.getItems().addAll(
                        firstState.getFileTable().keys().stream().map(Object::toString).toList());

                idListView.getItems().clear();
                idListView.getItems().addAll(
                        updatedStates.stream().map(ProgramState::getId).toList()
                );

                // Set the number of remaining program states
                numberOfStatesField.setText(String.valueOf(updatedStates.size()));

                // Ensure the first ID is selected and refresh unique views
                idListView.getSelectionModel().selectFirst();
                int selectedId = idListView.getSelectionModel().getSelectedItem();
                ProgramState selectedState = updatedStates.stream()
                        .filter(state -> state.getId() == selectedId)
                        .findFirst().orElse(null);

                if (selectedState != null)
                {
                    // Clear and repopulate shared components

                    symTableView.getItems().clear();
                    symTableView.getItems().addAll(selectedState.getSymtbl().getContent().entrySet());

                    execStackView.getItems().clear();
                    execStackView.getItems().addAll(
                            this.execStackParser(selectedState.getExecstack()).stream().map(Object::toString).toList());
                }
            }
            else
            {
                // Clear and repopulate shared components

                heapTableView.getItems().clear();
                heapTableView.getItems().addAll(controller.getFinalState().getHeap().getContent().entrySet());

                outputListView.getItems().clear();
                outputListView.getItems().addAll(
                        controller.getFinalState().getOutput().getList().stream().map(Object::toString).toList());

                fileTableView.getItems().clear();
                fileTableView.getItems().addAll(
                        controller.getFinalState().getFileTable().keys().stream().map(Object::toString).toList());

                // clear unique components

                symTableView.getItems().clear();
                execStackView.getItems().clear();
                idListView.getItems().clear();


                numberOfStatesField.setText("0");

                oneStepButton.setDisable(true); // Disable button if no program states remain
            }

        });

        // Layout

        VBox layout1 = new VBox(10,
                new Label("Number of Program States:"), numberOfStatesField,
                new Label("File Table:"), fileTableView,
                new Label("Heap Table:"), heapTableView,
                new Label("Output List:"), outputListView
        );
        layout1.setPadding(new Insets(10));


        VBox layout2 = new VBox(10,
                new Label("Program State IDs:"), idListView,
                new Label("Symbol Table:"), symTableView,
                new Label("Execution Stack:"), execStackView,
                oneStepButton
        );
        layout2.setPadding(new Insets(10));

        HBox mainLayout = new HBox(10, layout1, layout2);
        mainLayout.setPadding(new Insets(10));

        mainStage.setScene(new Scene(mainLayout, 600, 800));
        mainStage.setTitle("Controller Details");
        mainStage.show();
    }

    private TableView<Map.Entry<Integer, Value>> createHeapTableView(Map<Integer, Value> heapTable)
    {
        TableView<Map.Entry<Integer, Value>> tableView = new TableView<>();
        TableColumn<Map.Entry<Integer, Value>, String> addressColumn = createStringColumn("Address", entry -> entry.getKey().toString());
        TableColumn<Map.Entry<Integer, Value>, String> valueColumn = createStringColumn("Value", entry -> entry.getValue().toString());
        tableView.getColumns().addAll(addressColumn, valueColumn);
        tableView.setItems(FXCollections.observableArrayList(heapTable.entrySet()));
        return tableView;
    }

    private <T> TableColumn<T, String> createStringColumn(String title, java.util.function.Function<T, String> mapper)
    {
        TableColumn<T, String> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> new SimpleStringProperty(mapper.apply(cellData.getValue())));
        return column;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
