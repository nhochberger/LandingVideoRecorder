package controller;

import java.io.IOException;
import java.util.Set;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.application.BasicLoggedApplication;
import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.eventbus.SimpleEventBus;
import model.CommunicationHandler;
import model.CoordinateNormalizer;
import model.TrajectoryCoordinate;
import model.TrajectoryImporter;

public class LandingVideoRecorderApplication extends BasicLoggedApplication {

    private final BasicSession session;
    private final TrajectoryImporter importer;
    private final CommunicationHandler communicationHandler;

    public LandingVideoRecorderApplication(final ApplicationProperties applicationProperties) {
        super();
        this.session = new BasicSession(applicationProperties, new SimpleEventBus(), getLogger());
        this.importer = new TrajectoryImporter(this.session);
        this.communicationHandler = new CommunicationHandler(this.session);
    }

    public static void main(final String[] args) {
        setUpLoggingServices(LandingVideoRecorderApplication.class);
        try {
            final ApplicationProperties applicationProperties = new ApplicationProperties();
            final LandingVideoRecorderApplication application = new LandingVideoRecorderApplication(applicationProperties);
            application.start();
        } catch (final Exception e) {
            getLogger().fatal("Error while starting application. Shutting down.", e);
            System.exit(0);
        }
    }

    @Override
    public void start() {
        super.start();
        try {
            final Set<TrajectoryCoordinate> coordinates = this.importer.importCoordinates("F:/workspace2/time.txt", "F:/workspace2/r_lander_LS.txt", "F:/workspace2/v_lander.txt");
            this.communicationHandler.performCommunication(coordinates, new CoordinateNormalizer(155d / 1000d));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        super.stop();
    }

}
