import java.util.ArrayList;
import java.util.List;

public class SuperClass {

    protected List<SudokuField> list;

    public SuperClass(){
        this.list = new ArrayList<SudokuField>();
    }

    public static boolean verify(List<SudokuField> column, int number){
        //check columns
        for (int i = 0; i < 9; i++) {
            if (column.get(i).getValue() == number)
                return false;
        }
        return true;
    }

    public List<SudokuField> getList() {
        return list;
    }

    public void setList(List<SudokuField> list) {
        this.list = list;
    }
}
