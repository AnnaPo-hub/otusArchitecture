package ru.otus;

public class SquareService {
    private static final double EPSILON = 1e-9;

    public double[] findSquareRoot(double a, double b, double c) {
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c)) {
            throw new IllegalArgumentException("Коэффициенты должны быть конечными числами");
        }
        // защита: не квадратное уравнение
        if (Math.abs(a) < EPSILON) {
            throw new IllegalArgumentException("Коэффициент  'a' не должен быть равен нулю");
        }
        double discriminant = b * b - 4 * a * c;
        if (discriminant < -EPSILON) {
            return new double[0];
        } else if (discriminant > EPSILON) {
            double sqrtD = Math.sqrt(discriminant);

            double x1 = (-b - sqrtD) / (2 * a);
            double x2 = (-b + sqrtD) / (2 * a);

            // порядок вывода : от меньшего к большему
            return (x1 < x2) ? new double[]{x1, x2} : new double[]{x2, x1};
        } else {
            double x = -b / (2 * a);
            return new double[]{x};
        }
    }
}
