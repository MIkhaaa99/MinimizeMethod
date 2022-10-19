import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Start {

    public static double func(double x) {
        //return 0.75 * pow(x, 4) - 2 * pow(x, 3) + 2;
        //return 2 * pow(x, 2) - 12 * x;
        //return 2 * pow(x, 3) + 5 * pow(x, 2) - 6 * x;
        return pow(x, 4) - 2 * x + 4;
    }

    public static double funcDerivative(double x) {
        //return 6 * pow(x, 2) + 10 * x - 12;
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
        int N = 1000;
        int i = 1;
        double min = arr[0] + (((arr[1] - arr[0])*1.0)/(N+1));
        while(i<=N) {
            i++;
            double current = arr[0] + i*(((arr[1] - arr[0]) * 1.0)/(N+1));
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

    public static double[] methodSix() {
        List<Double> x = new ArrayList<>();
        x.add(1.0);
        double e1 = 0.0001;
        double e2 = 0.00015;
        int k = 0;
        int M = 100;
        do {
            if(abs(funcDerivative(x.get(k))) < e1) {
                return new double[] {func(x.get(k)), x.get(k)};
            }
            if(k >= M) {
                return new double[] {func(x.get(k)), x.get(k)};
            }
            double t = 0.25;
            t = func(x.get(k) - t * funcDerivative(x.get(k)));
            x.add(x.get(k) - t * funcDerivative(x.get(k)));
            if((abs(x.get(k+1) - x.get(k)) < e2) && ((abs(func(x.get(k+1) - x.get(k)))) < e2)) {
                k--;
                return new double[] {func(x.get(k)), x.get(k)};
            }
            else {
                k++;
            }
        }
        while(true);

    }

    public static void main(String[] args) {
        int[] arr = svennMethod(1, 2);
        System.out.println("Неопределенный интервал: " + Arrays.toString(arr));
        System.out.println("===============================Метод равномерного поиска==========================");
        double[] result1 = methodOne(arr);
        System.out.println("Минимальное значение функции = " + result1[0] + " в точке " + result1[1]);
        System.out.println("===============================Метод половинного деления (дитохомии)==========================");
        double[] result2 = methodTwo(arr);
        System.out.println("Минимальное значение функции = " + result2[0] + " в точке " + result2[1]);
        System.out.println("===============================Метод золотого сечения==========================");
        double[] result3 = methodThree(arr);
        System.out.println("Минимальное значение функции = " + result3[0] + " в точке " + result3[1]);
        System.out.println("===============================Метод Фибоначчи==========================");
        double[] result4 = methodFour(arr);
        System.out.println("Минимальное значение функции = " + result4[0] + " в точке " + result4[1]);
        System.out.println("===============================Градиентный спуск с постоянным шагом==========================");
        double[] result5 = methodFive();
        System.out.println("Минимальное значение функции = " + result5[0] + " в точке " + result5[1]);
        System.out.println("===============================Метод наискорейшего градиентного спуска==========================");
        double[] result6 = methodSix();
        System.out.println("Минимальное значение функции = " + result6[0] + " в точке " + result6[1]);
    }
}
