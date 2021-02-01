package lessonsix.taskthree;

public class Main {

    /*
    3. Написать метод, который проверяет состав массива из чисел 1 и 4.
    Если в нем нет хоть одной четверки или единицы, то метод вернет false;
    Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     */
    public static void main(String[] args) {
        System.out.println(containsOneOrFour(null));
        System.out.println(containsOneOrFour(new int[] {}));
        System.out.println(containsOneOrFour(new int[] {2, 3}));
        System.out.println(containsOneOrFour(new int[] {1, 2, 3}));
        System.out.println(containsOneOrFour(new int[] {1, 2, 3, 4}));
        System.out.println(containsOneOrFour(new int[] {4, 2, 3, 1}));
    }

    private static boolean containsOneOrFour(int[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        boolean containsOne = false;
        boolean containsFour = false;

        for (int value : arr) {
            containsOne |= (value == 1);
            containsFour |= (value == 4);
        }

        return containsOne && containsFour;
    }
}
