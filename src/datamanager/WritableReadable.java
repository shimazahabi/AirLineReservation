package datamanager;

public interface WritableReadable<E> {
    int STRING_FIXED_SIZE = 30;

    String generate();

    default String intToString(int i) {
        return Integer.toString(i);
    }

    default String booleanToString(boolean b) {
        return String.valueOf(b);
    }

    default int stringToInt(String str) {
        return Integer.parseInt(str);
    }

    default boolean stringToBoolean(String str) {
        return Boolean.parseBoolean(str);
    }

    default String fixString(String str) {
        while (str.length() < STRING_FIXED_SIZE)
            str += " ";
        return str.substring(0, STRING_FIXED_SIZE);
    }

    E separateRecord(String[] str);
}