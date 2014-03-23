package octopus.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/** @author Dmitry Kozlov */
@Entity
public class Ingredient extends BaseModel<Ingredient> {

    @Column(unique = true)
    public String name;

    public String description;

    @ManyToMany(mappedBy = "ingredients", fetch = LAZY)
    public Set<Recipe> recipes;

    public Ingredient() {}

    public Ingredient(Ingredient source) {
        super(source);
        this.name = source.name;
        this.description = source.description;
    }

    @Override
    public Ingredient shallowCopy() {
        return new Ingredient(this);
    }

    @Override
    public Ingredient self() {
        return this;
    }
}
