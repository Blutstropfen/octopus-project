package octopus.app.common;

/** @author Dmitry Kozlov */
public interface Messages {
    String constraint_violation = "Нарушено ограчение уникальности имени";
    String concurrent_modification =
            "Сохранение невозможно, так как данные были изменены другим пользователем. " +
                    "Обратите внимание на все несохраненные изменения.";
    String detached_entity =
            "Сохранение невозможно, так как сущность или связанные сущности уже уданенны другим пользователем. " +
                    "Обратите внимание на все несохраненные изменения.";
}
