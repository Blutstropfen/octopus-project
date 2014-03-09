package octopus.app.common;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

/** @author Dmitry Kozlov */
public class HibernateTypeAdapter extends TypeAdapter<HibernateProxy> {

    public static final TypeAdapterFactory factory = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (HibernateProxy.class.isAssignableFrom(type.getRawType())) {
                return (TypeAdapter<T>) new HibernateTypeAdapter(gson);
            }
            return null;
        }
    };

    private final Gson gson;

    private HibernateTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @SuppressWarnings("unchecked")
    @Override public void write(JsonWriter out, HibernateProxy value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        Class<?> baseType = Hibernate.getClass(value);
        TypeAdapter delegate = gson.getAdapter(TypeToken.get(baseType));
        Object unproxiedValue = value.getHibernateLazyInitializer().getImplementation();
        delegate.write(out, unproxiedValue);
    }

    @Override public HibernateProxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException();
    }
}
