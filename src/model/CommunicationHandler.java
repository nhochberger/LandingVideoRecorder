package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.timing.Sleeper;
import hochberger.utilities.timing.ToMilis;

public class CommunicationHandler extends SessionBasedObject {

    private final double landingX;
    private final double landingY;
    private final double landingZ;

    public CommunicationHandler(final BasicSession session) {
        super(session);

        this.landingX = 170d;
        this.landingY = 290d;
        this.landingZ = 11d;
    }

    public void performCommunication(final Set<TrajectoryCoordinate> coordinates, final CoordinateNormalizer normalizer) {
        int i = 0;
        for (final TrajectoryCoordinate coordinate : coordinates) {
            logger().info("Step " + ++i + " of " + coordinates.size());
            final double cameraX = this.landingX - (this.landingX - normalizer.normalize(coordinate.getxCoordinate()));
            final double cameraY = this.landingY - (this.landingY - normalizer.normalize(coordinate.getyCoordinate()));
            final double cameraZ = this.landingZ - (this.landingZ + normalizer.normalize(coordinate.getzCoordinate()));
            System.err.println(cameraX + ", " + cameraY + ", " + cameraZ);
            try {
                final Socket socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 50001));
                logger().info("starting communication with " + socket);
                writeToSocket((this.landingX - normalizer.normalize(coordinate.getxCoordinate())) + "," + (this.landingY - normalizer.normalize(coordinate.getyCoordinate())) + ","
                        + (this.landingZ + normalizer.normalize(coordinate.getzCoordinate())) + ";" + cameraX + "," + cameraY + "," + cameraZ + "?", socket);
                final String message = readFromSocket(socket);
                logger().info("Received :" + message);
                Sleeper.sleep(ToMilis.seconds(0.5));
            } catch (final UnknownHostException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static String readFromSocket(final Socket socket) throws IOException {
        final StringBuffer message = new StringBuffer();
        final InputStream inputStream = socket.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int read;
        while ('!' != (read = reader.read())) {
            message.append((char) read);
        }
        return message.toString();
    }

    protected static void writeToSocket(final String message, final Socket socket) throws IOException {
        final OutputStream outputStream = socket.getOutputStream();
        final OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write(message);
        writer.flush();
    }
}
