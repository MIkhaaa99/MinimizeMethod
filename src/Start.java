import java.util.Arrays;

import static java.lang.Math.pow;

public class Start {

    public static double func1(double x) {
        return 0.75 * pow(x, 4) - 2 * pow(x, 3) + 2;
    }

    public static int[] svennMethod(double x0, double t) {
        while(true) {
            if(func1(x0 - t) >= func1(x0) && func1(x0) <= func1(x0 + t)) {
                return new int[] {(int) (x0 - t), (int) (x0 + t)};
            }
            if(func1(x0 - t) <= func1(x0) && func1(x0) >= func1(x0 + t)) {
                throw new RuntimeException("Функция не является унимодальной!\nЗадайте другую начальную точку x0!");
            }
            if(func1(x0 - t) >= func1(x0) && func1(x0) >= func1(x0 + t)) {
                x0 = x0 + t;
            }
            if(func1(x0 - t) <= func1(x0) && func1(x0) <= func1(x0 + t)) {
                x0 = x0 - t;
            }
        }
    }

    public static double methodOne(int[] arr) {
        int N = arr[1] - arr[0] - 1;
        int i = 1;
        double min = arr[0] + ((arr[1] - arr[0])/(N+1));
        while(i<=N) {
            i++;
            double current = arr[0] + i*((arr[1] - arr[0])/(N+1));
            if(func1(current) < func1(min)) {
                min = current;
            }
        }
        return min;
    }

    public static double methodTwo(int[] arr) {
        int x = (arr[0] + arr[1])/2;
        int L = arr[1] - arr[0];
        int y = arr[0] + (L/4);
        int z = arr[1] - (L/4);
        while(true) {
            if(func1(y) < func1(x)) {
                arr[1] = x;
                x = y;

            }
        }
    }

    public static void main(String[] args) {
        int[] arr = svennMethod(1, 2);
        System.out.println(Arrays.toString(arr));
        double result = methodOne(arr);
        System.out.println(result);
        //System.out.println(Arrays.toString(svennMethod(1, 0.2)));
    }
}
