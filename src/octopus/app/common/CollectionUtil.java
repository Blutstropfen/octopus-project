package octopus.app.common;

import octopus.app.model.BaseModel;

import java.util.*;

/** @author Dmitry Kozlov */
public class CollectionUtil {

    public static <T extends BaseModel<T>> List<T> shallowCopy(List<T> entities) {
        ArrayList<T> result = new ArrayList<>(entities.size());
        for (BaseModel<T> entity : entities) {
            result.add(entity.shallowCopy());
        }
        return result;
    }

    public static <T extends BaseModel<T>> Set<T> shallowCopy(Set<T> entities) {
        HashSet<T> result = new HashSet<>(2 * entities.size());
        for (BaseModel<T> entity : entities) {
            result.add(entity.shallowCopy());
        }
        return result;
    }

    public static <T extends BaseModel<T>> Map<String, T> toMap(Collection<T> entities) {
        HashMap<String, T> result = new HashMap<>(2 * entities.size());
        for (BaseModel<T> entity : entities) {
            result.put(entity.id, entity.self());
        }
        return result;
    }
}
