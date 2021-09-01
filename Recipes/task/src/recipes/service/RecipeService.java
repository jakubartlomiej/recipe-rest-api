package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.data.Recipe;
import recipes.repositories.RecipeRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(int id) {
        //if (StreamSupport.stream(recipeRepository.findAll().spliterator(), false).findAny().isPresent()) {
        return recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Map<String, Integer> saveRecipe(Recipe recipe, HttpServletRequest request) {
        recipe.setAuthor(request.getUserPrincipal().getName());
        recipeRepository.save(recipe);
        return Map.of("id", recipe.getId());
    }

    public ResponseEntity<String> deleteById(int id, HttpServletRequest request) {
        if (recipeRepository.existsById(id)) {
            if (recipeRepository.findById(id).get().getAuthor().equals(request.getUserPrincipal().getName())) {
                recipeRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> update(int id, Recipe updateRecipe, HttpServletRequest request) {
        if (recipeRepository.findById(id).isPresent()) {
            return recipeRepository.findById(id).filter(recipe -> recipe.getAuthor().equals(request.getUserPrincipal().getName()))
                    .map(recipe -> {
                        recipe.setName(updateRecipe.getName());
                        recipe.setCategory(updateRecipe.getCategory());
                        recipe.setDate(updateRecipe.getDate());
                        recipe.setDescription(updateRecipe.getDescription());
                        recipe.setIngredients(updateRecipe.getIngredients());
                        recipe.setDirections(updateRecipe.getDirections());
                        recipeRepository.save(recipe);
                        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
                    }).orElse(new ResponseEntity<String>(HttpStatus.FORBIDDEN));
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    public Iterable<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryLikeIgnoreCaseOrderByDateDesc(category);
    }

    public Iterable<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
