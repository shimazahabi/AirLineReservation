package datamanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a generic class that includes all the methods that are needed to work with RandomAccessFile.
 * @param <T>
 */
public class DataHolder<T extends WritableReadable> {
    protected T t;
    protected String filePath;
    protected RandomAccessFile file;
    protected int recordBytesNum;
    protected int featuresNum;

    public DataHolder(T t, String filePath, int recordBytesNum, int featuresNum) {
        this.t = t;
        this.filePath = filePath;
        this.recordBytesNum = recordBytesNum;
        this.featuresNum = featuresNum;
        try {
            file = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        check();
    }

    /**
     * This method checks weather the file exists or not.
     */
    public void check() {
        File file1 = new File(filePath);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                System.err.println("File couldn't be created !");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method opens the file.
     */
    public void openFile() throws FileNotFoundException {
        file = new RandomAccessFile(filePath, "rw");
    }

    /**
     * This method closes the file.
     */
    public void closeFile() throws IOException {
        file.close();
    }

    /**
     * This method adds data to the file.
     */
    public void addToFile(T t) {
        try {
            openFile();
            file.seek(file.length());
            file.writeChars(t.generate());
            closeFile();
        } catch (IOException e) {
            System.err.println("File couldn't add the data !");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method finds the record in the file.
     * @param keyWord of the record
     * @return founded record
     */
    public T findInFile(String keyWord) {
        try {
            openFile();
            String[] str = new String[featuresNum];
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                str[0] = readFixString();
                if (keyWord.equals(str[0])) {
                    for (int j = 1; j < featuresNum; j++) {
                        str[j] = readFixString();
                    }
                    return (T) t.separateRecord(str);
                } else {
                    file.skipBytes(recordBytesNum - (t.STRING_FIXED_SIZE  * 2));
                }
            }
            closeFile();
        } catch (IOException e) {
            System.err.println("File couldn't find the data !");
        }
        return null;
    }

    /**
     * This method updates data in the file.
     * @param keyWord of the record
     * @param fieldNum the updated field number
     * @param replacement of the updated field
     */
    public void updateFile(String keyWord, int fieldNum, String replacement) {
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                String str = readFixString();
                if (keyWord.equals(str)) {
                    file.skipBytes((fieldNum - 2) * (t.STRING_FIXED_SIZE * 2));
                    file.writeChars(t.fixString(replacement));
                    break;
                } else {
                    file.skipBytes(recordBytesNum - (t.STRING_FIXED_SIZE  * 2));
                }
            }
            closeFile();
        } catch (IOException e) {
            System.err.println("File couldn't be updated !");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method removes data from the file.
     * @param keyWord of the record
     */
    public void removeFromFile(String keyWord) {
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                String str = readFixString();
                if (keyWord.equals(str)) {
                    long pointer = file.getFilePointer() - (t.STRING_FIXED_SIZE  * 2);

                    file.seek(file.length() - recordBytesNum);
                    str = readFixString();
                    str = wholeRecord(str);
                    file.seek(pointer);
                    file.writeChars(str);
                } else {
                    file.skipBytes(recordBytesNum - (t.STRING_FIXED_SIZE  * 2));
                }
            }

            long newLength = file.length() - recordBytesNum;
            file.setLength(newLength);
            closeFile();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * This method reads a fix string from the file.
     * @return the trimmed string
     */
    public String readFixString() {
        String tmp = "";
        try {
            for (int i = 0; i < t.STRING_FIXED_SIZE; i++) {
                tmp += file.readChar();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmp.trim();
    }

    /**
     * This method makes the whole record in one string.
     * @param str the first field
     * @return the whole record
     */
    public String wholeRecord(String str) {
        String tmp = t.fixString(str);
        try {
            for (int i = 0; i < (recordBytesNum / 2) - t.STRING_FIXED_SIZE; i++) {
                tmp += file.readChar();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmp;
    }
}