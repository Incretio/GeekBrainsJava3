package lessonone.tasktwo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // 2. Написать метод, который преобразует массив в ArrayList;
    public static void main(String[] args) {
        String[] stringArr = {"One", "Two", "Three", "Four"};
        System.out.println(toList(stringArr));

        Integer[] intArr = {0, 1, 2, 3};
        System.out.println(toList(intArr));
    }

    private static <T> List<T> toList(T[] arr) {
        List<T> result = new ArrayList<>();
        for (T element : arr) {
            result.add(element);
        }
        return result;
    }
}
