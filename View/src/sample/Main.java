import exception.FileOperationsException;
import exception.NotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;


public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ksrt.messages", new Locale("pl", "PL"));
    private SudokuBoard sudokuBoard;
    GridPane gridPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws ApplicationException, FileOperationsException {

        VBox vBox = new VBox();

        vBox.setPrefWidth(300);
        vBox.setMinHeight(500);
        vBox.setMinWidth(500);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        primaryStage.setTitle(resourceBundle.getString("app.title"));

        Button btn1 = new Button(resourceBundle.getString("button.easy"));
        Button btn2 = new Button(resourceBundle.getString("button.medium"));
        Button btn3 = new Button(resourceBundle.getString("button.hard"));

        Button btn4 = new Button(resourceBundle.getString("button.loadfrom.file"));
        Button btn5 = new Button(resourceBundle.getString("button.loadfrom.database"));

        vBox.getChildren().add(btn1);
        vBox.getChildren().add(btn2);
        vBox.getChildren().add(btn3);
        vBox.getChildren().add(btn4);
        vBox.getChildren().add(btn5);

        btn1.setMinWidth(vBox.getPrefWidth());
        btn2.setMinWidth(vBox.getPrefWidth());
        btn3.setMinWidth(vBox.getPrefWidth());
        btn4.setMinWidth(vBox.getPrefWidth());
        btn5.setMinWidth(vBox.getPrefWidth());

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Wybrano poziom latwy");
                createGridSudoku(primaryStage, 1);
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Wybrano poziom sredni");
                createGridSudoku(primaryStage, 2);
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Wybrano poziom trudny");
                createGridSudoku(primaryStage, 3);
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Wybrano wczytanie stanu gry z pliku");
                createGridSudoku(primaryStage, -1);
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("wybrano wczytanie stanu gdy z bazy");
                createGridSudoku(primaryStage,-2);
            }
        });

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }

    private void createGridSudoku(final Stage stage, int difficulty) {
        try {
            gridPane = FXMLLoader.load(getClass().getResource("sudokuGrid.fxml"));
        } catch (IOException e) {
            logger.info("blad wczytywania widoku planszy");
            new FileOperationsException("Bledny plik do widoku sudoku", e);
        }

        if (difficulty > 0)
            createSudokuBoard(difficulty);
        else
            loadSudokuBoard(difficulty, stage);

        int v = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                final TextField textField = (TextField) gridPane.lookup("#" + v);
                textField.setText(Integer.toString(v++));
                textField.textProperty().bindBidirectional(sudokuBoard.getBoard(row, col), new NumberStringConverter());

                if (!textField.getText().equals("0")) {
                    textField.setEditable(false);
                    textField.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                /*textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        logger.info("wybrano pole do wpisania");
                        editTextField();
                    }
                });*/

                textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        //logger.info("wpisano nowa wartosc w pole");
                        textField.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY)));
                        editTextField();
                    }
                });

            }
        }

        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menuNew = new Menu("Sudoku");
        Menu menuSave = new Menu(resourceBundle.getString("menu.save"));
        MenuItem menuItem = new MenuItem(resourceBundle.getString("menu.new"));
        MenuItem menuExitItem = new MenuItem(resourceBundle.getString("menu.exit"));
        MenuItem menuSaveToFile = new MenuItem(resourceBundle.getString("menu.save.file"));
        MenuItem menuSaveToDB = new MenuItem(resourceBundle.getString("menu.save.db"));


        menuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    start(stage);
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
            }
        });

        menuExitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Zamknieto aplikacje");
                Platform.exit();
            }
        });

        menuSaveToFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Przegladanie plików");
                File selectFile = fileChooser.showSaveDialog(stage);
                SudokuBoardDAOFactory sudokuBoardDAOFactory = new SudokuBoardDAOFactory();
                FileSudokuBoardDAO fileSudokuBoardDao = sudokuBoardDAOFactory.getFileDAO(selectFile.getPath());
                logger.info("Zapisanie stanu gry");

                try {
                    fileSudokuBoardDao.write(sudokuBoard);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        menuSaveToDB.setOnAction(event -> {
            SudokuBoardDAOFactory sudokuBoardDAOFactory = new SudokuBoardDAOFactory();
            final DBSudokuBoardDAO[] dbSudokuBoardDAO = {null};
            TextInputDialog textInputDialog = new TextInputDialog("nazwa zapisu");
            textInputDialog.setTitle("Baza danych");
            textInputDialog.setHeaderText("Zapis do bazy danych");
            textInputDialog.setContentText("Nazwa");
            Optional<String> stringOptional = textInputDialog.showAndWait();
            stringOptional.ifPresent(name -> {
                try{
                    dbSudokuBoardDAO[0] = sudokuBoardDAOFactory.getDBDAO(name);
                    dbSudokuBoardDAO[0].write(sudokuBoard);
                }catch (ApplicationException e){
                    e.printStackTrace();
                }
            });
        });

        menuSave.getItems().addAll(menuSaveToFile, menuSaveToDB);
        menuBar.getMenus().addAll(menuNew, menuSave);
        menuNew.getItems().addAll(menuItem, menuExitItem);
        borderPane.setTop(menuBar);
        borderPane.setCenter(gridPane);

        stage.setScene(new Scene(borderPane));


    }

    private void createSudokuBoard(int difficulty) {
        sudokuBoard = new SudokuBoard();
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        backtrackingSudokuSolver.solve(sudokuBoard);
        logger.info("Tablica przed usunieciem\n" + sudokuBoard.viewBoard());
        earseFromBoard(20 * difficulty);
        logger.info("Tablica po usunieciem\n" + sudokuBoard.viewBoard());
    }

    private void loadSudokuBoard(int difficulty, Stage stage) {
        if (difficulty == -1) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Odczyt stanu gry");
            File selectedFile = fileChooser.showOpenDialog(stage);
            SudokuBoardDAOFactory sudokuBoardDAOFactory = new SudokuBoardDAOFactory();
            FileSudokuBoardDAO fileSudokuBoardDao = sudokuBoardDAOFactory.getFileDAO(selectedFile.getPath());
            logger.info("wczytano stan gry z pliku");
            try {
                sudokuBoard = fileSudokuBoardDao.read();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }else{
            SudokuBoardDAOFactory sudokuBoardDAOFactory = new SudokuBoardDAOFactory();
            final DBSudokuBoardDAO[] dbSudokuBoardDAOS = {null};
            TextInputDialog textInputDialog = new TextInputDialog("Nazwa zapisu");
            textInputDialog.setTitle("Baza danych");
            textInputDialog.setHeaderText("Odczyt z bazy danych");
            textInputDialog.setContentText("Nazwa");
            Optional<String> stringOptional = textInputDialog.showAndWait();
            stringOptional.ifPresent(name ->{
                try {
                    dbSudokuBoardDAOS[0] = sudokuBoardDAOFactory.getDBDAO(name);
                    sudokuBoard = dbSudokuBoardDAOS[0].read();
                }catch (ApplicationException e){
                    e.printStackTrace();
                }
            });
        }
    }

    private void earseFromBoard(int amount) {
        Random rand = new Random();
        int row, col;
        for (int i = 0; i < amount; ) {
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            if (sudokuBoard.getBoard(row, col).intValue() != 0) {
                sudokuBoard.setBoard(row, col, 0);
                i++;
            }
        }
        logger.info("Usnieto " + amount + " pol z SudokuBoard");
    }

    private void editTextField() {
        boolean completeField = true;
        boolean correctField = true;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.getBoard(row, col).intValue() == 0)
                    completeField = false;

            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!sudokuBoard.checkBoard(row, col, sudokuBoard.getBoard(row, col).intValue()))
                    correctField = false;
            }
        }
        logger.info("complete = " + completeField + " correct = " + correctField);
        if (completeField && correctField) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText("!! GRATULACJE !!");
            alert.setContentText("Rozwiazales sudoku");

            alert.showAndWait();
            exit();
        }
    }

    public void exit() {
        Platform.exit();
    }
}
