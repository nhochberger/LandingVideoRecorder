package model;

public class LandingCoordinates {
    private final double xCoordinate;
    private final double yCoordinate;
    private final double zCoordinate;

    public LandingCoordinates(final double xCoordinate, final double yCoordinate, final double zCoordinate) {
        super();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
    }

    public double getxCoordinate() {
        return this.xCoordinate;
    }

    public double getyCoordinate() {
        return this.yCoordinate;
    }

    public double getzCoordinate() {
        return this.zCoordinate;
    }
}
