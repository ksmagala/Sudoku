import java.io.Serializable;

public class SudokuRow extends SuperClass implements Cloneable, Serializable {



    public SudokuRow(){
        super();
    }

    public boolean checkRow(int number, SudokuRow row){
        for(int i = 0; i<9;i++){
            if (row.getList().get(i).getField().intValue() == number)
                return false;
        }
        return true;
    }
//    public boolean verify(List<SudokuField> row, int number){
//        //check rows
//
//        for (int i = 0; i < 9; i++) {
//            if (row.get(i).getValue() == number)
//                return false;
//        }
//        return true;
//    }
//
//    public List<SudokuField> getRow() {
//        return row;
//    }
//
//    public void setRow(List<SudokuField> row) {
//        this.row = row;
//    }
}
