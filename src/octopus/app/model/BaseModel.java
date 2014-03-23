package octopus.app.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/** @author Dmitry Kozlov */
@MappedSuperclass
public abstract class BaseModel<Self extends BaseModel<Self>> {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Id public String id;

    @Version public Integer version;

    protected BaseModel() {}

    protected BaseModel(BaseModel source) {
        this.id = source.id;
        this.version = source.version;
    }

    public abstract Self shallowCopy();
    public abstract Self self();
}
