import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class SudokuField implements Cloneable, Comparable<SudokuField>, Serializable {
    SimpleIntegerProperty value;

    public SudokuField() {
        this.value = new SimpleIntegerProperty();
    }

    public SudokuField(int value){
        this.value = new SimpleIntegerProperty(value);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty getField() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    @Override
    public int compareTo(SudokuField o) {
        if (value.get() > o.value.get())
            return 1;
        else if (value.get() < o.value.get())
            return -1;
        else
            return 0;    }
}

