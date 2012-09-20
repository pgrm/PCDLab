package session0;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 20.09.12
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class RealNumber implements Comparable<RealNumber> {
    private int numerator;
    private int denominator;

    public RealNumber(int numerator, int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("Denominator cannot be 0 (Zero).");

        this.numerator = numerator;
        this.denominator = denominator;

        simplifyFraction();
    }

    private void simplifyFraction() {
        int gdc = calculateGreatestCommonDivisor(numerator, denominator);

        if (gdc > 1) {
            numerator /= gdc;
            denominator /= gdc;
        }

        if (denominator < 0) { // possible version 1/-2 => -1/2; -1/-2 => 1/2
            denominator *= (-1);
            numerator *= (-1);
        }
    }

    private int calculateGreatestCommonDivisor(int num1, int num2) {
        if (num1 == 0)
            return num2;
        else if (num2 == 0)
            return num1;

        if (num1 < 0)
            num1 *= (-1);
        if (num2 < 0)
            num2 *= (-1);

        while (num1 != num2) {
            if (num1 > num2)
                num1 -= num2;
            else
                num2 -= num1;
        }

        return num1;
    }

    @Override
    public int compareTo(RealNumber another) {
        if (this.equals(another))
            return 0;

        double thisValue = this.getComparableValue();
        double anotherValue = another.getComparableValue();

        if (thisValue < anotherValue)
            return -1;
        else
            return 1;
    }

    private double getComparableValue() {
        return (double)numerator / (double)denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealNumber)) return false;

        RealNumber that = (RealNumber) o;

        if (denominator != that.denominator) return false;
        if (numerator != that.numerator) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
