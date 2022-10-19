import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Start {

    public static double func(double x) {
        //return 0.75 * pow(x, 4) - 2 * pow(x, 3) + 2;
        //return 2 * pow(x, 2) - 12 * x;
        return 2 * pow(x, 3) + 5 * pow(x, 2) - 6 * x;
        //return pow(x, 4) - 2 * x + 4;
    }

    public static double funcDerivative(double x) {
        return 6 * pow(x, 2) + 10 * x - 12;
        //return 4 * pow(x, 3) - 2;
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

    public static double[] methodOne(int[] arr) {
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
        double minFunc = func(min);
        return new double[] {minFunc, min};
    }

    public static double[] methodTwo(int[] arr) {
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
        return new double[] {func((a + b)/2), (a + b)/2};
    }

    public static double[] methodThree(int[] arr) {
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
                return new double[] {func((a + b) / 2), ((a + b) / 2)};
            }
        }
    }

    public static double[] methodFour(int[] arr) {
        double[] fib = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233};
        int l = 1;
        double e = 0.01;
        int N = 0;
        while((arr[1] - arr[0]) / l >= fib[N]) {
            N++;
        }
        int k = 0;
        double a = arr[0];
        double b = arr[1];
        double y = a + (fib[N-2]/fib[N]) * (b - a);
        double z = a + (fib[N-1]/fib[N]) * (b - a);

        while(true) {
            if(func(y) <= func(z)) {
                b = z;
                z = y;
                y = a + (fib[N - k - 3]/fib[N - k - 1]) * (b - a);
            }
            else {
                a = y;
                y = z;
                z = a + (fib[N - k - 2]/fib[N - k - 1]) * (b - a);
            }
            if(k != N - 3) {
                k++;
            }
            else {
                break;
            }
        }
        z = (a + b) / 2;
        y = z;
        z = y + e;
        if(func(y) <= func(z)) {
            b = z;
        }
        else {
            a = y;
        }
        return new double[] {func((a + b) / 2), (a + b) / 2};
    }

    public static double[] methodFive() {
        double x0 = 1;
        double x1 = 1;
        double e1 = 0.0001;
        double e2 = 0.00015;
        int M = 1000;
        int k = 0;
        do {
            if(abs(funcDerivative(x1)) < e1) {
                return new double[] {func(x1), x1};
            }
            if(k >= M) {
                return new double[] {func(x1), x1};
            }
            double t0 = 1;
            do {
                t0 = t0 / 2;
                //x0 = x1;
                x1 = x0 - t0 * funcDerivative(x0);
            }
            while(func(x1) - func(x0) >= 0);
            if((abs(x1 - x0) >= e2) || (abs(func(x1) - func(x0)) >= e2)) {
                x0 = x1;
            }
            else {
                break;
            }
        }
        while(true);
        return new double[] {func(x1), x1};
    }

    public static void main(String[] args) {
        int[] arr = svennMethod(1, 2);
        System.out.println("Неопределенный интервал: " + Arrays.toString(arr));
        //double[] result = methodFive();
        System.out.println("===============================Метод золотого сечения==========================");
        double[] result = methodThree(arr);
        System.out.println("Минимальное значение функции = " + result[0] + " в точке " + result[1]);
        System.out.println("===============================Метод Фибоначчи==========================");
        double[] result1 = methodFour(arr);
        System.out.println("Минимальное значение функции = " + result1[0] + " в точке " + result1[1]);
        System.out.println("===============================Градиентный спуск с постоянным шагом==========================");
        double[] result2 = methodFive();
        System.out.println("Минимальное значение функции = " + result2[0] + " в точке " + result2[1]);
    }
}
