import java.util.Arrays;

import static java.lang.Math.pow;

public class Start {

    public static double func(double x) {
        return 0.75 * pow(x, 4) - 2 * pow(x, 3) + 2;
        //return 2 * pow(x, 2) - 12 * x;
    }

    public static double func1(double x) {
        return 2 * pow(x, 2) - 12 * x;
    }
    public static int[] svennMethod(double x0, double t) {
        while(true) {
            if(func(x0 - t) >= func(x0) && func(x0) <= func(x0 + t)) {
                return new int[] {(int) (x0 - t), (int) (x0 + t)};
            }
            if(func(x0 - t) <= func(x0) && func(x0) >= func(x0 + t)) {
                throw new RuntimeException("Функция не является унимодальной!\nЗадайте другую начальную точку x0!");
            }
            if(func(x0 - t) >= func(x0) && func(x0) >= func(x0 + t)) {
                x0 = x0 + t;
            }
            if(func(x0 - t) <= func(x0) && func(x0) <= func(x0 + t)) {
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
            if(func(current) < func(min)) {
                min = current;
            }
        }
        return min;
    }

    public static double methodTwo(int[] arr) {
        double delt = 0.01, eps = 0.01;
        double a = arr[0];
        double b = arr[1];
        while(((b - a) / 2) >= eps) {
            double x1 = (a + b - delt) / 2;
            double x2 = (a + b + delt) / 2;
            if(func(x1) > func(x2)) {
                a = x1;
            }
            if(func(x1) < func(x2)) {
                b = x2;
            }
        }
        return (a + b)/2;
    }

    public static double methodThree(int[] arr) {
        double a = arr[0];
        double x = arr[0] + ((3 - Math.sqrt(5))/2) * (arr[1] - arr[0]);
        double y = arr[0] + arr[1] - x;
        double b = arr[1];

        while(true) {
            if(func(x) <= func(y)) {
                b = y;
                y = x;
                x = a + b - x;
            }
            else {
                a = x;
                x = y;
                y = a + b - y;
            }

            if(Math.abs(b - a) < 0.000001) {
                return (a + b) / 2;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = svennMethod(1, 2);
        System.out.println("Неопределенный интервал: " + Arrays.toString(arr));
        double result = methodThree(arr);
        System.out.println("min = " + result);
        //System.out.println(Arrays.toString(svennMethod(1, 0.2)));
    }
}
