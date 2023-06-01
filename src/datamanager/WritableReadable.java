package datamanager;

/**
 * This interface includes the writable-readable actions.
 * @param <E>
 */
public interface WritableReadable<E> {
    int STRING_FIXED_SIZE = 30;
    int INDEX_SIZE = 68;

    /**
     * This method generates a writable record.
     * @return string of the record
     */
    String generate();

    /**
     * This method is for separating the keyWord.
     * @return the keyWord.
     */
    String keyWord();

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

    /**
     * This method fixes a string.
     * @param str
     * @return fixed string
     */
    default String fixString(String str) {
        while (str.length() < STRING_FIXED_SIZE)
            str += " ";
        return str.substring(0, STRING_FIXED_SIZE);
    }

    /**
     * This method separates a record (in string format) into different fields.
     * @param str the string of the record
     * @return the record
     */
    E separateRecord(String[] str);
}