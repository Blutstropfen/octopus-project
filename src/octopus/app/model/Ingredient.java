package octopus.app.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/** @author Dmitry Kozlov */
@Entity
public class Ingredient extends BaseModel {

    public String name;
    public String description;

    @ManyToMany(mappedBy = "ingredients", fetch = LAZY)
    public Set<Recipe> recipes;
}
