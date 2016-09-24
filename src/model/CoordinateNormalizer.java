package model;

public class CoordinateNormalizer {

    private final double ratio;

    public CoordinateNormalizer(final double ratio) {
        super();
        this.ratio = ratio;
    }

    public double normalize(final double value) {
        return Math.abs(this.ratio * value);
    }
}
