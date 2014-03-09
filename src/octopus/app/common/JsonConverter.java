package octopus.app.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;

/** @author Dmitry Kozlov */
public class JsonConverter extends AbstractHttpMessageConverter<Object> {

    private static final Charset charset = Charset.forName("UTF-8");

    private Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(HibernateTypeAdapter.factory).create();

    public JsonConverter() {
        super(new MediaType("application", "json", charset));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> cls, HttpInputMessage input) throws IOException, HttpMessageNotReadableException {
        Reader json = new InputStreamReader(input.getBody(), charset);

        try {
            return this.gson.fromJson(json, cls);
        } catch (JsonParseException ex) {
            throw new HttpMessageNotReadableException("Invalid Json received: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage output) throws IOException, HttpMessageNotWritableException {
        OutputStreamWriter writer = new OutputStreamWriter(output.getBody(), charset);

        try {
            this.gson.toJson(obj, writer);
            writer.close();
        } catch (JsonIOException ex) {
            throw new HttpMessageNotWritableException("Cannot serialize to Json: " + ex.getMessage(), ex);
        }
    }
}
