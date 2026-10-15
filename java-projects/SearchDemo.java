public class SearchDemo {

    public static int linearSearch(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] array1 = {8, 2, 5};
        int[] array2 = {4, 9};
        int[] array3 = {3, 3, 7};

        System.out.println(linearSearch(array1, 2));
        System.out.println(linearSearch(array2, 7));
        System.out.println(linearSearch(array3, 3));
    }
}