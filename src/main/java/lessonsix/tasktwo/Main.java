package lessonsix.tasktwo;

import java.util.Arrays;

public class Main {

    /*
    Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки.
    Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
    Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
     */
    public static void main(String[] args) {
        // Exception in thread "main" java.lang.RuntimeException: Not contains digit four
        // System.out.println(Arrays.toString(takeAfterLastFour(new int[] {})));

        // Exception in thread "main" java.lang.RuntimeException: Not contains digit four
        // System.out.println(Arrays.toString(takeAfterLastFour(new int[] {1, 2, 3, 5})));

        // Empty array - []
        System.out.println(Arrays.toString(takeAfterLastFour(new int[] {1, 2, 3, 4})));

        // [5, 6, 7, 8]
        System.out.println(Arrays.toString(takeAfterLastFour(new int[] {1, 2, 3, 4, 5, 6, 7, 8})));

        // [7, 8]
        System.out.println(Arrays.toString(takeAfterLastFour(new int[] {1, 2, 3, 4, 5, 6, 4, 7, 8})));
    }

    private static int[] takeAfterLastFour(int[] arr) {
        int lastFourIndex = -1;
        for (int i = arr.length - 1; i >= 0 ; i--) {
            if (arr[i] == 4) {
                lastFourIndex = i;
                break;
            }
        }
        if (lastFourIndex == -1) {
            throw new RuntimeException("Not contains digit four");
        }
        int[] result = new int[arr.length - (lastFourIndex + 1)];
        int newIndex = 0;
        for (int i = lastFourIndex + 1; i < arr.length ; i++) {
            result[newIndex++] = arr[i];
        }
        return result;
    }
}
