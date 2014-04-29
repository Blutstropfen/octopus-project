create or replace function process_ingredients() returns trigger as
$body$
  begin
      if not exists (select * from recipe_ingredient where ingredients_id = old.ingredients_id) then
	      delete from ingredient where id = old.ingredients_id;
      end if;
    return null;
  end;
$body$ language plpgsql;

create trigger recipe_ingredient_handle
after delete on recipe_ingredient
for each row execute procedure process_ingredients();