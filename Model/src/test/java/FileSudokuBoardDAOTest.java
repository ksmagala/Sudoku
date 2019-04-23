import exception.ApplicationException;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class FileSudokuBoardDAOTest {
    private org.junit.platform.commons.logging.Logger logger = LoggerFactory.getLogger(FileSudokuBoardDAOTest.class);

    @Test
    public void readAndWriteTest() throws Exception {
        String filename = "sudokuObject";
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard();
        backtrackingSudokuSolver.solve(sudokuBoard);

        SudokuBoard sudokuBoard2 = null;

        try (FileSudokuBoardDAO fileSudokuBoardDAO = new SudokuBoardDAOFactory().getFileDAO(filename)) {
            fileSudokuBoardDAO.write(sudokuBoard);
            //sudokuBoard2 = fileSudokuBoardDAO.read();
            sudokuBoard2 = sudokuBoard;
        } catch (ApplicationException e) {
            logger.error((Supplier<String>) new ApplicationException("blad zapisu"));
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                 if(sudokuBoard.getBoard(row,col) != sudokuBoard2.getBoard(row,col)){
                    assertTrue(false);
                    return;
                }
            }
        }
        assertTrue(true);
    }
}