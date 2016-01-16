package algorithms.polyphasesort;

/**
 * Created by Witold on 2015-11-02.
 */
public class Fibonacci {
    private static final int[] GENERATED = {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181};

    //zwraca minimalna wyzsza od n liczbe fibonacciego, albo n jezli n nalezy do ciagu.
    public static int nextFibonacci(int n) {
        int prev = 1;
        int curr = 1;
        while (curr < n) {
            int temp = prev;
            prev = curr;
            curr = temp + curr;
        }
        return curr;
    }

    public static boolean isFibonacci(int n) {
        if (n < 4182) {
            for (int i = 0; i < GENERATED.length; i++)
                if (n == GENERATED[i])
                    return true;
        } else if (n == nextFibonacci(n))
            return true;
        return false;
    }

}
