import javafx.beans.property.IntegerProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.mustache.Value;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class SudokuBoardTest {

    public static long startTime;
    SudokuBoard b1 = new SudokuBoard();
    SudokuBoard b2 = new SudokuBoard();
    BacktrackingSudokuSolver sut = new BacktrackingSudokuSolver();
    SudokuBoard sut3 = new SudokuBoard();
    private SudokuBoard  sudokuBoard = new SudokuBoard();

    @Test
    public void NotTheSameBoards(){
        Assert.assertNotEquals(b1.getBoard(),b2.getBoard());
    }


    @BeforeClass
    public static void beforeClass() {
        startTime = System.currentTimeMillis();
    }

    @Before
    public void setUp() {
//        ArrayList<ArrayList<Integer>> tempBoard = {{8, 4, 6, 9, 3, 2, 7, 1, 5},
//                {9, 2, 5, 1, 7, 6, 4, 8, 3},
//                {5, 6, 2, 3, 1, 8, 9, 4, 7},
//                {4, 7, 9, 2, 6, 5, 1, 3, 8},
//                {3, 8, 1, 4, 9, 7, 5, 2, 6},
//                {2, 9, 7, 6, 4, 3, 8, 5, 1},
//                {1, 3, 4, 8, 5, 9, 6, 7, 2},
//                {6, 5, 8, 7, 2, 1, 3, 9, 4}};
//        sut3 = new SudokuBoard(tempBoard);
    }

    @Test
    public void shouldFillBoardCorrect(){

        assertTrue(sut.solve(sut3));
    }

    @Test
    public void shouldFillBoardCorrectFromNullArray(){
        SudokuBoard sut2 = new SudokuBoard();
        assertTrue(sut.solve(sut2));
    }




    @Test
    public void setBoard() {
        SudokuBoard b = new SudokuBoard();
        b.setBoard(1,1,5);

        Assert.assertEquals(b.getBoard(1,1).get(),5);
        }


    @Test
    public void checkBoard() {
    }

    @Test
    public void viewBoard() {
    }

    @Test
    public void getBox() {
    }

}