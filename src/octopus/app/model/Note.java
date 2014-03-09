package octopus.app.model;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

/** @author Dmitry Kozlov */
@Entity
public class Note extends BaseModel {

    public Date published;
    public @Lob @Type(type = "text") String contents;

    @ManyToOne
    public Recipe owner;
}
