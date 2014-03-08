package octopus.app.model;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.search.annotations.Index.YES;

/** @author Dmitry Kozlov */
@Indexed
@Entity
public class Recipe extends BaseModel {

    @Field(index = YES)
    public String name;

    @Field(index= YES)
    public @Lob @Type(type = "text") String contents;

    public Date published;

    @OneToMany(mappedBy = "owner", fetch = LAZY, cascade = ALL)
    public List<Note> notes;

    @ManyToMany(fetch = LAZY, cascade = {PERSIST, MERGE})
    public Set<Ingredient> ingredients;
}
