package datamanager;

import java.io.*;
import java.util.HashMap;

/**
 * This is a generic class that includes all the methods that are needed to work with RandomAccessFile.
 * @param <T>
 */
public class DataHolder<T extends WritableReadable> {
    protected T t;
    protected String filePath;
    protected String fileIndexPath;
    protected RandomAccessFile file;
    protected RandomAccessFile fileIndex;
    protected int recordBytesNum;
    protected int featuresNum;
    protected HashMap<String, Long> index;

    public DataHolder(T t, String filePath, String fileIndexPath, int recordBytesNum, int featuresNum) {
        this.t = t;
        this.filePath = filePath;
        this.fileIndexPath = fileIndexPath;
        this.recordBytesNum = recordBytesNum;
        this.featuresNum = featuresNum;
        this.index = new HashMap<>();
        try {
            file = new RandomAccessFile(filePath, "rw");
            fileIndex = new RandomAccessFile(fileIndexPath, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        check(filePath);
        check(fileIndexPath);
        fileIndexToHashMap();
    }

    /**
     * This method checks weather the file exists or not.
     */
    public void check(String path) {
        File file1 = new File(path);
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
     * This method loads the indexFile in a HashMap.
     */
    public void fileIndexToHashMap() {
        try {
            fileIndex = new RandomAccessFile(fileIndexPath, "rw");
            for (int i = 0; i < (fileIndex.length() / t.INDEX_SIZE); i++) {
                index.put(readFixString(fileIndex), fileIndex.readLong());
            }
            fileIndex.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method adds data to the file.
     */
    public void addToFile(T t) {
        try {
            openFile();
            fileIndex = new RandomAccessFile(fileIndexPath, "rw");

            long pointer = file.length();
            file.seek(pointer);
            file.writeChars(t.generate());

            fileIndex.seek(fileIndex.length());
            fileIndex.writeChars(t.fixString(t.keyWord()));
            fileIndex.writeLong(pointer);

            index.put(t.keyWord(), pointer);

            closeFile();
            fileIndex.close();
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
            if (index.containsKey(keyWord)) {
                openFile();
                file.seek(index.get(keyWord));

                String[] str = new String[featuresNum];
                for (int i = 0; i < featuresNum; i++) {
                    str[i] = readFixString(file);
                }
                closeFile();
                return (T) t.separateRecord(str);
            }
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
            if (index.containsKey(keyWord)) {
                file.seek(index.get(keyWord));
                file.skipBytes((fieldNum - 1) * (t.STRING_FIXED_SIZE * 2));
                file.writeChars(t.fixString(replacement));
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
            if (index.containsKey(keyWord)) {
                file.seek(file.length() - recordBytesNum);
                String str = wholeRecord();
                file.seek(index.get(keyWord));
                file.writeChars(str);
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
    public String readFixString(RandomAccessFile file) {
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
     * @return the whole record
     */
    public String wholeRecord() {
        String tmp = "";
        try {
            for (int i = 0; i < (recordBytesNum / 2); i++) {
                tmp += file.readChar();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmp;
    }
}