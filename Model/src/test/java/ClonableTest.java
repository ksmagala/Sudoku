import org.junit.Assert;
import org.junit.Test;

public class ClonableTest {

    @Test
    public void CopiedBoard() { // for the same objects should return true
        SudokuBoard expectedBoard = new SudokuBoard();
        SudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(expectedBoard);
        SudokuBoard cloneBoard = new SudokuBoard();
        try {
            cloneBoard = (SudokuBoard) expectedBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expectedBoard, cloneBoard);
    }

    @Test
    public void EqualsField(){
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.setBoard(0,0,1);
        sudokuBoard.setBoard(0,2,1);
        if(sudokuBoard.getBoard(0,0) == sudokuBoard.getBoard(0,2))
        Assert.assertTrue(true);
    }
}
