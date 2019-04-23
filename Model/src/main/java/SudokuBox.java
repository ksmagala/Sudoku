import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SuperClass implements Cloneable, Serializable{

    private SudokuField[][] sudokuFields= new SudokuField[3][3];

    public boolean checkBoxSudoku (int number){
        for(int row = 0;row<3;row++){
            for(int col=0;col<3;col++){
                if ( sudokuFields[row][col].getValue() == number)
                    return false;
            }
        }
        return true;
    }

    public void addFieldToBox(SudokuField sudokuField, int row, int col){
        sudokuFields[row][col]= sudokuField;
    }
//    public boolean verify(List<SudokuField> box,int number){

        //check in box
//        int rowStart = (row / 3) * 3;
//        int colStart = (col / 3) * 3;
//        for (int i = rowStart; i < rowStart + 3; i++) {
//            for (int j = colStart; j < colStart + 3; j++) {
//                if (board.get(i).get(j).getValue() == number)
//                    return false;
//            }
//        }

//        for (int i = 0; i < 9; i++) {
//            if (box.get(i).getValue() == number)
//                return false;
//        }
//        return true;
//    }

//    public List<SudokuField> getBox() {
//        return box;
//    }
//
//    public void setBox(List<SudokuField> box) {
//        this.box = box;
//    }
}
