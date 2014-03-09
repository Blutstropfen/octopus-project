package octopus.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/** @author Dmitry Kozlov */
@Entity
public class Ingredient extends BaseModel {

    @Column(unique = true)
    public String name;

    public String description;

    @ManyToMany(mappedBy = "ingredients", fetch = LAZY)
    public Set<Recipe> recipes;
}
