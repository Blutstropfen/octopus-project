package octopus.app.common;

import octopus.app.model.BaseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author Dmitry Kozlov */
public class CollectionCopyHelper {

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
}
