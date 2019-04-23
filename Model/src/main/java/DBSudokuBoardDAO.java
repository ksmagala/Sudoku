import exception.ApplicationException;
import exception.DBException;
import org.apache.logging.log4j.core.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.sql.*;

public class DBSudokuBoardDAO implements DAO<SudokuBoard>, AutoCloseable {
    static final String WRITE_OBJECT_SQL = "INSERT INTO sudoku_boards(name, fieldListSudoku) VALUES (?, ?)";
    static final String READ_OBJECT_SQL = "SELECT fieldListSudoku FROM sudoku_boards WHERE name = ?";
    static final String DELETE_OBJECT_SQL = "DELETE FROM sudoku_boards WHERE name = ?";

    org.junit.platform.commons.logging.Logger logger = LoggerFactory.getLogger(DBSudokuBoardDAO.class);

    Connection connection = null;
    private String boardSudoku;

    DBSudokuBoardDAO(String boardSudoku) throws ApplicationException {
        this.boardSudoku = boardSudoku;
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://db4free.net/sudokukompo";
        String username = "usersudoku";
        String password = "qwerty123";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SudokuBoard read() throws ApplicationException {
        String fieldListSudoku = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_OBJECT_SQL);
            preparedStatement.setString(1, boardSudoku);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fieldListSudoku = resultSet.getString(1);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DBException();
        }
        SudokuBoard sudokuBoard = new SudokuBoard();
        String[] fieldListTable = fieldListSudoku.split(" ");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuBoard.setBoard(row, col, Integer.parseInt(fieldListTable[row * 9 + col]));
            }
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws ApplicationException{
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OBJECT_SQL);
            preparedStatement.setString(1, boardSudoku);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(WRITE_OBJECT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, boardSudoku);
            String fieldListSudoku = "";
            for(int row = 0; row<9;row++){
                for(int col = 0;col<9;col++){
                    fieldListSudoku += sudokuBoard.getBoard(row,col) + " ";
                }
            }
            preparedStatement.setObject(2, fieldListSudoku);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = -1;
            if(resultSet.next())
                id = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();
            connection.commit();

        }catch (SQLException e){
            throw new DBException();
        }
    }


    @Override
    public void close() throws ApplicationException {
        try {
            connection.close();
        }catch (SQLException e){
            throw new DBException();
        }
    }
}
