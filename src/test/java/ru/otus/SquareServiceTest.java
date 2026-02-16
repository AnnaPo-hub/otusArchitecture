package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareServiceTest {
    SquareService service = new SquareService();

    private static final double DELTA = 1e-9;

    @Test
    public void shouldReturnEmptyArrayForNegativeDiscriminant() {
        double[] roots = service.findSquareRoot(1, 0, 1);
        Assertions.assertEquals(0, roots.length);
    }

    @Test
    public void shouldReturnArrayForPositiveDiscriminant() {
        double[] roots = service.findSquareRoot(1, 0, -1);
        Assertions.assertEquals(2, roots.length);
        Assertions.assertEquals(-1.0, roots[0], DELTA);
        Assertions.assertEquals(1.0, roots[1], DELTA);
    }

    @Test
    public void shouldReturnSingleRootWhenDiscriminantIsZero() {
        double[] roots = service.findSquareRoot(1, 2, 1);
        Assertions.assertEquals(1, roots.length);
        Assertions.assertEquals(-1.0, roots[0], DELTA);
    }

    @Test
    public void shouldThrowExceptionWhenACoefficientIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(0, 1, 1));
    }

    @Test
    public void shouldThrowExceptionWhenACoefficientIsAlmostZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.findSquareRoot(1e-12, 1, 1)
        );
    }

    @Test
    public void shouldReturnSingleRootWhenDiscriminantIsAlmostZero() {
        double[] roots = service.findSquareRoot(1, 2, 1 + 1e-10);

        Assertions.assertEquals(1, roots.length);
        Assertions.assertEquals(-1.0, roots[0], DELTA);
    }

    @Test
    void shouldThrowWhenAnyCoefficientIsNaN() {
        double nan = Double.NaN;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(nan, 2, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, nan, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, 2, nan));
    }

    @Test
    void shouldThrowWhenAnyCoefficientIsInfinite() {
        double posInf = Double.POSITIVE_INFINITY;
        double negInf = Double.NEGATIVE_INFINITY;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(posInf, 2, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(negInf, 2, 1));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, posInf, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, negInf, 1));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, 2, posInf));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findSquareRoot(1, 2, negInf));
    }

}
