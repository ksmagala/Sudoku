import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    BacktrackingSudokuSolver() {

    }

    public boolean solve(SudokuBoard m_board) {
        Random rand = new Random();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                if(m_board.getBoard(row, col).intValue() == 0) {

                    for(int number = 1; number <= 9; number++) {
                        int tempValue = rand.nextInt(9) + 1;
                        if(m_board.checkBoard(row, col, tempValue)) {
                            m_board.setBoard(row, col, tempValue);
                            if(solve(m_board))
                                return true;
                            else
                                m_board.setBoard(row, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
