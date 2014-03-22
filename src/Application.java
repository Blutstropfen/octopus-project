import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author Dmitry Kozlov */
public class Application {

    public static void main(String[] args) throws Exception {
        WebAppContext app = new WebAppContext("web", "/");

        Server server = new Server(8080);
        server.setHandler(app);
        server.start();
        server.join();
    }
}
