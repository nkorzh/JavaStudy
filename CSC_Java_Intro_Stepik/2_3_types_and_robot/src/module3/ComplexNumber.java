package module3;

public final class ComplexNumber {
    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    /**
     * Terrible hash
     * @return hash of current complex value
     */
    @Override
    public int hashCode() {
        return java.lang.Integer.hashCode(java.lang.Double.hashCode(re) + java.lang.Double.hashCode(im));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof ComplexNumber) {
            ComplexNumber b = (ComplexNumber)obj;
            return java.lang.Double.compare(re, b.getRe()) == 0 && java.lang.Double.compare(im, b.getIm()) == 0;
        }
        return false;
    }
}
