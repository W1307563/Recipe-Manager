package com.example.cmis202project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RecipeManagerApp extends Application {
    private RecipeManager recipeManager;
    private TableView<RecipeTableEntry> tableView;
    private TextField nameTextField;
    private TextField ingredientsTextField;
    private TextField instructionsTextField;
    private Spinner<Integer> servingsSpinner;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        recipeManager = new RecipeManager();
        tableView = createTableView();
        nameTextField = new TextField();
        ingredientsTextField = new TextField();
        instructionsTextField = new TextField();
        servingsSpinner = createServingsSpinner();

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addRecipe());
        Button sortByNameButton = new Button("Sort by Name");
        sortByNameButton.setOnAction(e -> sortRecipesByName());

        Button ExampleBtn  = new Button("Example");

        VBox inputVBox = new VBox(10);
        inputVBox.setPadding(new Insets(10));
        inputVBox.getChildren().addAll(
                new Label("Name:"),
                nameTextField,
                new Label("Ingredients:"),
                ingredientsTextField,
                new Label("Instructions:"),
                instructionsTextField,
                new Label("Servings:"),
                servingsSpinner,
                addButton,
                sortByNameButton,
                ExampleBtn

        );

        ExampleBtn.setOnAction(action -> {
            Stack<String> Example = new Stack<>();
            Example.add("Tea");
            Example.add("Hot Water and Tea Bag");
            Example.add("Heat up water and put tea bag in cup");
            Example.add("Serving: 1");
            Label label = new Label(Example.get(0));
            Label label1 = new Label(Example.get(1));
            Label label2 = new Label(Example.get(2));
            Label label3 = new Label(Example.get(3));
            inputVBox.getChildren().addAll(label, label1, label2, label3);
            ExampleBtn.setDisable(true);
        });

        LinkedList<String> unfavs = new LinkedList<>();
        unfavs.add("Brussel Sprouts");
        unfavs.add("Olives");
        unfavs.add("Beets");
        unfavs.add("Mushrooms");
        unfavs.add("Mayo");

        Label label = new Label("Now that you have inputted your favorite recipes, \n why don't you give me a list of your LEAST favorite foods?!");
        Label label1 = new Label("\n Some common ones are " + String.join(", ", unfavs));
        TextField unfavsTextBox= new TextField("Enter your least favorite food");

        unfavsTextBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newUnfav = unfavsTextBox.getText();
                unfavs.add(newUnfav);
                label1.setText("\n" + String.join(", ", unfavs));
            }
        });

        class PrintList implements Runnable { // Concurrent processing/Multi-threading (Part 4)
            private LinkedList<String> linkedList;

            public PrintList(LinkedList<String> linkedList) {
                this.linkedList = linkedList;
            }

            @Override
            public void run() {
                String newUnfav = unfavsTextBox.getText();
                linkedList.add(newUnfav);
                label1.setText("Added " + newUnfav + " to the list of least favorite foods");
            }
        }

        Runnable printB = new com.example.cmis202project.PrintList(unfavs);
        Thread thread1 = new Thread(printB);
        thread1.start();

        VBox rightVBox = new VBox();
        rightVBox.getChildren().addAll(label, label1, unfavsTextBox);

        HBox root = new HBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(tableView, inputVBox, rightVBox);

        Scene scene1 = new Scene(root, 800, 400);
        primaryStage.setTitle("Recipe Manager");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private TableView<RecipeTableEntry> createTableView() {
        TableColumn<RecipeTableEntry, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<RecipeTableEntry, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        TableColumn<RecipeTableEntry, String> instructionsColumn = new TableColumn<>("Instructions");
        instructionsColumn.setCellValueFactory(new PropertyValueFactory<>("instructions"));

        TableColumn<RecipeTableEntry, Integer> servingsColumn = new TableColumn<>("Servings");
        servingsColumn.setCellValueFactory(new PropertyValueFactory<>("servings"));

        TableView<RecipeTableEntry> tableView = new TableView<>();
        tableView.getColumns().addAll(nameColumn, ingredientsColumn, instructionsColumn, servingsColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(FXCollections.observableArrayList());

        return tableView;
    }


    private Spinner<Integer> createServingsSpinner() {
        Spinner<Integer> spinner = new Spinner<>(1, 100, 1);
        spinner.setEditable(true);
        spinner.getEditor().setAlignment(Pos.CENTER_RIGHT);
        return spinner;
    }

    private void addRecipe() {
        String name = nameTextField.getText();
        String ingredients = ingredientsTextField.getText();
        String instructions = instructionsTextField.getText();
        int servings = servingsSpinner.getValue();

        if (!name.isEmpty() && !ingredients.isEmpty() && !instructions.isEmpty()) {
            Recipe recipe = new Recipe(name, ingredients, instructions, servings);
            recipeManager.addRecipe(recipe);
            tableView.getItems().add(new RecipeTableEntry(recipe));
            clearInputFields();
            tableView.setItems(FXCollections.observableArrayList(tableView.getItems()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
        }
    }

    private void sortRecipesByName() {
        ObservableList<RecipeTableEntry> data = tableView.getItems();

        ArrayList<RecipeTableEntry> dataArray = new ArrayList<>(data);

        quicksort(dataArray, 0, dataArray.size() - 1);

        tableView.setItems(FXCollections.observableArrayList(dataArray));
    }

    // Best and Average Case: O(nlogn)
    // Worst Case: O(n^2)
    private void quicksort(ArrayList<RecipeTableEntry> dataArray, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(dataArray, low, high);
            quicksort(dataArray, low, pivotIndex - 1);
            quicksort(dataArray, pivotIndex + 1, high);
        }
    }

    private int partition(ArrayList<RecipeTableEntry> dataArray, int low, int high) {
        String pivot = dataArray.get(high).getName();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (dataArray.get(j).getName().compareToIgnoreCase(pivot) <= 0) {
                i++;
                RecipeTableEntry temp = dataArray.get(i);
                dataArray.set(i, dataArray.get(j));
                dataArray.set(j, temp);
            }
        }
        RecipeTableEntry temp = dataArray.get(i + 1);
        dataArray.set(i + 1, dataArray.get(high));
        dataArray.set(high, temp);
        return i + 1;
    }

    private void clearInputFields() {
        nameTextField.clear();
        ingredientsTextField.clear();
        instructionsTextField.clear();
        servingsSpinner.getValueFactory().setValue(1);
    }

    public static class RecipeTableEntry {
        private String name;
        private String ingredients;
        private String instructions;
        private Integer servings;

        public RecipeTableEntry(Recipe recipe) {
            this.name = recipe.getName();
            this.ingredients = recipe.getInstructions();
            this.instructions = recipe.getIngredients();
            this.servings = recipe.getServings();
        }

        public String getName() {
            return name;
        }

        public String getIngredients() {
            return ingredients;
        }

        public String getInstructions() {
            return instructions;
        }

        public Integer getServings() {
            return servings;
        }


    }

}








