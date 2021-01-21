package lessonone.taskone;

import java.util.Arrays;

public class Main {

    // 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public static void main(String[] args) {
        String[] stringArr = {"One", "Two", "Three", "Four"};
        System.out.printf("Source array: %s%n", Arrays.toString(stringArr));
        swap(stringArr, 1, 3);
        System.out.printf("After swap array: %s%n", Arrays.toString(stringArr));

        Integer[] intArr = {0, 1, 2, 3};
        System.out.printf("Source array: %s%n", Arrays.toString(intArr));
        swap(intArr, 0, 1);
        System.out.printf("After swap array: %s%n", Arrays.toString(intArr));
    }

    private static <T> void swap(T[] arr, int indexOne, int indexTwo) {
        T temp = arr[indexOne];
        arr[indexOne] = arr[indexTwo];
        arr[indexTwo] = temp;
    }
}
