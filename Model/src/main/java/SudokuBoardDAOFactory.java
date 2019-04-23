import exception.ApplicationException;

public class SudokuBoardDAOFactory {

    public FileSudokuBoardDAO getFileDAO(String filename) {
        return new FileSudokuBoardDAO(filename);
    }

    public DBSudokuBoardDAO getDBDAO(String boardSudoku) throws ApplicationException{
        return new DBSudokuBoardDAO(boardSudoku);
    }
}
