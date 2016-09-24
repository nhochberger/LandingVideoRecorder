package model;

import hochberger.utilities.text.i18n.DirectI18N;

public class TrajectoryCoordinate implements Comparable<TrajectoryCoordinate> {

    private final double time;
    private final double xCoordinate;
    private final double yCoordinate;
    private final double zCoordinate;
    private final double xThruster;
    private final double yThruster;
    private final double zThruster;

    public TrajectoryCoordinate(final double time, final double xCoordinate, final double yCoordinate, final double zCoordinate, final double xThruster, final double yThruster,
            final double zThruster) {
        super();
        this.time = time;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.xThruster = xThruster;
        this.yThruster = yThruster;
        this.zThruster = zThruster;
    }

    public double getTime() {
        return this.time;
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

    public double getxThruster() {
        return this.xThruster;
    }

    public double getyThruster() {
        return this.yThruster;
    }

    public double getzThruster() {
        return this.zThruster;
    }

    @Override
    public int compareTo(final TrajectoryCoordinate other) {
        if (null == other) {
            return 1;
        }
        return (int) Math.signum(this.time - other.time);
    }

    @Override
    public String toString() {
        return new DirectI18N("${0}s @ (${1}|${2}|${3}), thruster in direction (${4}|${5}|${6})", Double.toString(this.time), Double.toString(this.xCoordinate), Double.toString(this.yCoordinate),
                Double.toString(this.zCoordinate), Double.toString(this.xThruster), Double.toString(this.yThruster), Double.toString(this.zThruster)).toString();
    }

}
