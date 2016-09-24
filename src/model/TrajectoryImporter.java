package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;

public class TrajectoryImporter extends SessionBasedObject {

    public TrajectoryImporter(final BasicSession session) {
        super(session);
    }

    public Set<TrajectoryCoordinate> importCoordinates(final String timeFilePath, final String coordinateFilePath, final String thrusterFilePath) throws IOException {
        logger().info("Importing data from: " + timeFilePath + ", " + coordinateFilePath + ", " + thrusterFilePath);
        final File timeFile = new File(timeFilePath);
        final File coordinateFile = new File(coordinateFilePath);
        final File thrusterFile = new File(thrusterFilePath);
        final List<String> times = Files.readAllLines(timeFile.toPath());
        final List<String> points = Files.readAllLines(coordinateFile.toPath());
        final List<String> thrusters = Files.readAllLines(thrusterFile.toPath());
        final SortedSet<TrajectoryCoordinate> coordinates = new TreeSet<>();
        for (int i = 0; i < times.size(); i++) {
            final double time = Double.parseDouble(times.get(i).trim().replace(',', '\0'));
            final String[] coordinatesSplit = points.get(i).replace(' ', '\0').split(",");
            final String[] thrusterSplit = thrusters.get(i).replace(' ', '\0').split(",");
            final TrajectoryCoordinate newCoordinate = new TrajectoryCoordinate(time, Double.parseDouble(coordinatesSplit[0]), Double.parseDouble(coordinatesSplit[1]),
                    Double.parseDouble(coordinatesSplit[2]), Double.parseDouble(thrusterSplit[0]), Double.parseDouble(thrusterSplit[1]), Double.parseDouble(thrusterSplit[2]));
            coordinates.add(newCoordinate);
        }
        logger().info("Imported " + times.size() + " datasets");
        return coordinates;
    }
}
