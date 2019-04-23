import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuBoard implements Cloneable, Serializable {
    private static final Logger logger = LogManager.getLogger(SudokuBoard.class);

    private List<List<SudokuField>> board;
    //List<MyType> fixed = Arrays.asList(new MyType[100]);


    private SudokuRow sudokuRow;
    private SudokuColumn sudokuColumn;
    private SudokuBox sudokuBox;

    public SudokuBoard() {
        this.board = new ArrayList<List<SudokuField>>(9);

        this.board = new ArrayList<List<SudokuField>>();
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<SudokuField>());
            for (int j = 0; j < 9; j++)
                board.get(i).add(new SudokuField(0));
        }


        this.sudokuRow = new SudokuRow();
        this.sudokuColumn = new SudokuColumn();
        this.sudokuBox = new SudokuBox();

    }

    public SudokuBoard(List<List<SudokuField>> m_board) {
        board = new ArrayList<List<SudokuField>>(81);
        this.board = m_board;
    }


    public SimpleIntegerProperty getBoard(int row, int col) {

        return board.get(row).get(col).getField();
    }


    public void setBoard(int row, int col, int value) {

        this.board.get(row).get(col).setValue(value);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean checkBoard(int row, int col, int number) {
        if (getRow(row).checkRow(number, getRow(row)) && getColumn(col).checkColumn(number, getColumn(col)) && getBox(row, col).checkBoxSudoku(number))

            return true;


        return false;
    }

    public String viewBoard() {
        String resultString = "";
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0)
                resultString += ("---------------------------------\n");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0)
                    resultString += (" | ");
                resultString += (" " + this.board.get(i).get(j).getValue() + " ");
            }
            resultString += "\n";
        }
        return resultString;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuBox box = new SudokuBox();
        for (int i = (x / 3) * 3; i < 3 + (x / 3) * 3; i++) {
            for (int j = (y / 3) * 3; j < 3 + (y / 3) * 3; j++) {
                box.addFieldToBox(board.get(i).get(j), i % 3, j % 3);
            }
        }

        return box;

    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn row = new SudokuColumn();
        List<SudokuField> list = new ArrayList<SudokuField>(9);
        for (int i = 0; i < 9; i++) {
            list.add(new SudokuField(board.get(i).get(x).getValue()));
        }
        row.setList(list);
        return row;
    }

    public SudokuRow getRow(int y) {
        SudokuRow row = new SudokuRow();
        List<SudokuField> list = new ArrayList<SudokuField>(9);
        for (int i = 0; i < 9; i++) {
            list.add(new SudokuField(board.get(y).get(i).getValue()));
        }
        row.setList(list);
        return row;
    }


    @Override
    public String toString() {
        return "SudokuBoard{" +
                "board=" + board +
                ", sudokuRow=" + sudokuRow +
                ", sudokuColumn=" + sudokuColumn +
                ", sudokuBox=" + sudokuBox +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuBoard that = (SudokuBoard) o;

        if (board != null ? !board.equals(that.board) : that.board != null) return false;
        if (sudokuRow != null ? !sudokuRow.equals(that.sudokuRow) : that.sudokuRow != null) return false;
        if (sudokuColumn != null ? !sudokuColumn.equals(that.sudokuColumn) : that.sudokuColumn != null) return false;
        return sudokuBox != null ? sudokuBox.equals(that.sudokuBox) : that.sudokuBox == null;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (sudokuRow != null ? sudokuRow.hashCode() : 0);
        result = 31 * result + (sudokuColumn != null ? sudokuColumn.hashCode() : 0);
        result = 31 * result + (sudokuBox != null ? sudokuBox.hashCode() : 0);
        return result;
    }

    public List<List<SudokuField>> getBoard() {
        return board;
    }

    public void setBoard(List<List<SudokuField>> board) {
        this.board = board;
    }
}


