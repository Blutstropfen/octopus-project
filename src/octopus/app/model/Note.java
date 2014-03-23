package octopus.app.model;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

/** @author Dmitry Kozlov */
@Entity
public class Note extends BaseModel<Note> {

    public Date published;
    public @Lob @Type(type = "text") String contents;

    @ManyToOne
    public Recipe owner;

    public Note() {}

    public Note(Note source) {
        super(source);
        this.published = source.published;
        this.contents = source.contents;
    }

    @Override
    public Note shallowCopy() {
        return new Note(this);
    }

    @Override
    public Note self() {
        return this;
    }
}
