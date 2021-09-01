package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.data.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    Iterable<Recipe> findAllByCategoryLikeIgnoreCaseOrderByDateDesc(String category);
    Iterable<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
