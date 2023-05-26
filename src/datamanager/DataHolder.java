package datamanager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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

    public void openFile() throws FileNotFoundException {
        file = new RandomAccessFile(filePath, "rw");
    }

    public void closeFile() throws IOException {
        file.close();
    }

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

    public void removeFromFile(String keyWord) {
        try {
            openFile();
            RandomAccessFile tempFile = new RandomAccessFile( "data.dat", "rw");
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                String str = readFixString();
                if (!keyWord.equals(str)) {
                    str = wholeRecord(str);
                    tempFile.writeChars(str);
                } else {
                    file.skipBytes(recordBytesNum - (t.STRING_FIXED_SIZE  * 2));
                }
            }
            closeFile();
            tempFile.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        Path tmp = Paths.get("data.dat");
        Path previous = Paths.get(filePath);
        try {
            Files.move(tmp, previous, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        File tmp = new File("data.dat");
//        System.out.println(tmp.delete());
//////        System.out.println(tmp.exists());
////        File previous = new File(filePath);
//////        System.out.println(previous.exists());
////        System.out.println(tmp.getPath());
////        System.out.println(tmp.renameTo(previous));
    }

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