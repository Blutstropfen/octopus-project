package octopus.app.common;

/** @author Dmitry Kozlov */
public class ServiceResponse {
    public String status;
    public String message;

    public ServiceResponse(String status) {
        this.status = status;
    }

    public static ServiceResponse ok() {
        return new ServiceResponse("ok");
    }

    public static ServiceResponse exception(String message) {
        ServiceResponse response = new ServiceResponse("exception");
        response.message = message;
        return response;
    }
}
