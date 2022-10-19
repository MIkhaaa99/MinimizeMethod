import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;

public class Start {

    public static double func(double x) {
        return pow(x, 4) - 2 * x + 4;
        //return 0.75 * pow(x, 4) - 2 * pow(x, 3) + 2;
        //return 2 * pow(x, 2) - 12 * x;
    }

    public static double funcDerivative(double x) {
        return 4 * pow(x, 3) - 2;
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
        double[] fib = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765};
        double l = 0.01;
        double e = 0.01;
        int N = 0;
        while((arr[1] - arr[0]) / l >= fib[N]) {
            N++;
        }
        int k = 0;
        List<Double> a = new ArrayList<>();
        a.add((double) arr[0]);
        List<Double> b = new ArrayList<>();
        b.add((double) arr[1]);
        List<Double> y = new ArrayList<>();
        double yc = a.get(0) + (fib[N-2]/fib[N]) * (b.get(0) - a.get(0));
        y.add(yc);
        List<Double> z = new ArrayList<>();
        double zc = a.get(0) + (fib[N-1]/fib[N]) * (b.get(0) - a.get(0));
        z.add(zc);
        do {
            if(func(y.get(k)) <= func(z.get(k))) {
                a.add(a.get(k));
                b.add(z.get(k));
                z.add(y.get(k));
                yc = a.get(k+1) + (fib[N-k-3]/fib[N-k-1]) * (b.get(k+1) - a.get(k+1));
                y.add(yc);
            }
            else {
                a.add(y.get(k));
                b.add(b.get(k));
                y.add(z.get(k));
                zc = a.get(k+1) + (fib[N-k-2]/fib[N-k-1]) * (b.get(k+1) - a.get(k+1));
                z.add(zc);
            }
            if(k != N-3) {
                k++;
                continue;
            }
            else {
                break;
            }
        }
        while(true);
//        z.add((a.get(N-2)+b.get(N-2)) / 2);
//        y.add((a.get(N-2)+b.get(N-2)) / 2);
        y.add((a.get(N-2)+b.get(N-2)) / 2);
        z.add(y.get(N-1) + e);
        if(y.get(N-1) <= z.get(N-1)) {
            a.add(a.get(N-2));
            b.add(z.get(N-1));
        }
        else {
            a.add(y.get(N-1));
            b.add(b.get(N-2));
        }
        return new double[] {func((a.get(N-1) + b.get(N-1))/2), (a.get(N-1) + b.get(N-1))/2};
    }

    public static double[] methodFourOld(int[] arr) {
        double[] fib = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233};
        int l = 1;
        double e = 0.01;
        int N = 0;

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

    public static void main(String[] args) {
        int[] arr = svennMethod(1, 2);
        System.out.println("Неопределенный интервал: " + Arrays.toString(arr));
        double[] result = methodFour(arr);
        //double[] result = methodFour(new int[] {0, 10});
        System.out.println("Минимальное значение функции = " + result[0] + " в точке " + result[1]);
        //System.out.println(Arrays.toString(svennMethod(1, 0.2)));
    }
}
