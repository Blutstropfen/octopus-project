import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/** @author Dmitry Kozlov */
public class Application {

    public static void main(String[] args) throws Exception {
        WebAppContext app = new WebAppContext("web", "/");
        app.setErrorHandler(null);

        Server server = new Server(8080);
        server.setHandler(app);
        server.start();
        server.join();
    }
}
