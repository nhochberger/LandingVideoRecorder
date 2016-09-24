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

    public CommunicationHandler(final BasicSession session) {
        super(session);
    }

    public void performCommunication(final Set<TrajectoryCoordinate> coordinates, final CoordinateNormalizer normalizer) {
        int i = 0;
        for (final TrajectoryCoordinate coordinate : coordinates) {
            logger().info("Step " + ++i + " of " + coordinates.size());
            try {
                final Socket socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 50001));
                logger().info("starting communication with " + socket);
                System.err.println(coordinate);
                writeToSocket((170d - normalizer.normalize(coordinate.getxCoordinate())) + "," + (290d - normalizer.normalize(coordinate.getyCoordinate())) + ","
                        + (11d + normalizer.normalize(coordinate.getzCoordinate())) + ";" + 0d + "," + 0d + "," + -1d + "?", socket);
                // + coordinate.getxThruster() + "," + coordinate.getyThruster() + "," + coordinate.getzThruster() + "?",socket);
                final String message = readFromSocket(socket);
                System.err.println(message);
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
