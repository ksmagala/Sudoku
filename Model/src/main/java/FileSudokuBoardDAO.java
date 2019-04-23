import exception.NotFoundException;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class FileSudokuBoardDAO implements DAO<SudokuBoard>, AutoCloseable {

    private String fileName;

    BufferedWriter writer = null;
    Scanner scan = null;


    public FileSudokuBoardDAO() {

    }

    public FileSudokuBoardDAO(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public SudokuBoard read() throws NotFoundException {
        SudokuBoard tmp = new SudokuBoard();
        File file = new File(fileName);

        try (Scanner scan = new Scanner(file)) {

            List<List<SudokuField>> tmpList = tmp.getBoard();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    tmpList.get(i).get(j).setValue(scan.nextInt());
            }
            scan.close();
            tmp.setBoard(tmpList);

        } catch (IOException e) {
            throw new NotFoundException("Filed read sudokuBoard from file", e);
        }

        return tmp;
    }


    @Override
    public void write(SudokuBoard sudokuBoard) throws NotFoundException {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(
                    fileName + ".txt"));


            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    writer.write(sudokuBoard.getBoard().get(i).get(j).getValue() + " ");
                }
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            throw new NotFoundException("Filed to save sudoku board", e);
        }
    }


    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }

    @Override
    public void close() throws Exception {

    }
}
