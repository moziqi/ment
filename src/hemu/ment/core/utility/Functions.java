package hemu.ment.core.utility;

/**
 * Created by muu on 2015/5/28.
 */
public final class Functions {

    private Functions() {}

    public static int[] intArray(int begin, int size) {
        int[] array = new int[size];
        for (int i = begin, j = 0; j < size; i++, j++) {
            array[j] = i;
        }
        return array;
    }

}
