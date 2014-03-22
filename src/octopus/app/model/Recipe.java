package octopus.app.model;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
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
public class Recipe extends BaseModel<Recipe> {

    @Field(index = YES)
    @Column(unique = true)
    public String name;

    @Field(index= YES)
    public @Lob @Type(type = "text") String contents;

    public Date published;

    @OneToMany(mappedBy = "owner", fetch = LAZY, cascade = ALL)
    public List<Note> notes;

    @ManyToMany(fetch = LAZY, cascade = {PERSIST, MERGE})
    public Set<Ingredient> ingredients;

    public Recipe() {}

    public Recipe(Recipe source) {
        super(source);
        this.name = source.name;
        this.contents = source.contents;
        this.published = source.published;
    }

    @Override
    public Recipe shallowCopy() {
        return new Recipe(this);
    }
}
