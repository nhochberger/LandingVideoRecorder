package controller;

import java.io.IOException;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.application.BasicLoggedApplication;
import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.eventbus.SimpleEventBus;
import model.TrajectoryImporter;

public class LandingVideoRecorderApplication extends BasicLoggedApplication {

    private final BasicSession session;
    private final TrajectoryImporter importer;

    public LandingVideoRecorderApplication(final ApplicationProperties applicationProperties) {
        super();
        this.session = new BasicSession(applicationProperties, new SimpleEventBus(), getLogger());
        this.importer = new TrajectoryImporter(this.session);
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
        try {
            this.importer.importCoordinates("F:/workspace2/time.txt", "F:/workspace2/r_lander_LS.txt", "F:/workspace2/v_lander.txt");
        } catch (final IOException e) {
            e.printStackTrace();
        }
        super.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        super.stop();
    }

}
