import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SuperClass implements Cloneable, Serializable {

//    private List<SudokuField> column ;


    public SudokuColumn(){
       super();
    }

    public boolean checkColumn(int number, SudokuColumn col){
        for(int i = 0; i<9;i++){
            if (col.getList().get(i).getField().intValue() == number)
                return false;
        }
        return true;
    }
//    public static boolean verify(List<SudokuField> column, int number){
//        //check columns
//        for (int i = 0; i < 9; i++) {
//            if (column.get(i).getValue() == number)
//                return false;
//        }
//        return true;
//    }
//
//    public List<SudokuField> getColumn() {
//        return column;
//    }
//
//    public void setColumn(List<SudokuField> column) {
//        this.column = column;
//    }
}
