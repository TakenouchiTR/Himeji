//https://stackoverflow.com/questions/23531704/which-is-my-best-option-to-process-big-2d-arrays-in-a-java-app

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileIntMatrix implements Closeable {

    private final int rows;
    private final int cols;
    private final long rowSize;
    public final int totalEntries;
    public int position;
    private final RandomAccessFile raf;

    public FileIntMatrix(File f, int rows, int cols) throws IOException {
        if (rows < 0 || cols < 0)
            throw new IllegalArgumentException(
                "Rows and cols cannot be negative!");
        this.rows = rows;
        this.cols = cols;
        totalEntries = rows * cols;
        rowSize = cols * 4;
        raf = new RandomAccessFile(f, "rw");
        raf.setLength(rowSize * cols);
    }

    /**
     * Absolute get method.
     */
    public int get(int row, int col) throws IOException {
        pos(row, col);
        return get();
    }

    /**
     * Absolute set method.
     */
    public void set(int row, int col, int value) throws IOException {
        pos(row, col);
        set(value);
    }

    public void pos(int row, int col) throws IOException {
        if (row < 0 || col < 0 || row >= rows || col >= cols)
            throw new IllegalArgumentException("Invalid row or col!");
        position = row * rows + col;
        raf.seek(row * rowSize + col * 4);
    }

    /**
     * Relative get method. Useful if you want to go though the whole array or
     * though a continuous part, use {@link #pos(int, int)} to position.
     */
    public int get() throws IOException {
        position++;
    	return raf.readInt();
    }

    /**
     * Relative set method. Useful if you want to go though the whole array or
     * though a continuous part, use {@link #pos(int, int)} to position.
     */
    public void set(int value) throws IOException {
        raf.writeInt(value);
    }

    public int getRows() { return rows; }

    public int getCols() { return cols; }

    @Override
    public void close() throws IOException {
        raf.close();
    }

}