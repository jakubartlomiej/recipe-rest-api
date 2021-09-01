package recipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.data.Recipe;
import recipes.service.RecipeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeApi {

    private final RecipeService recipeService;

    @Autowired
    public RecipeApi(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/recipe/new")
    public Map<String, Integer> updateRecipe(@Valid @RequestBody Recipe recipe, HttpServletRequest request) {
        return recipeService.saveRecipe(recipe, request);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id, HttpServletRequest request) {
        return recipeService.deleteById(id, request);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe updateRecipe, HttpServletRequest request) {
        return recipeService.update(id, updateRecipe, request);
    }

    @GetMapping("/recipe/search")
    public Iterable<Recipe> searchRecipeByCategoryOrName(@RequestParam(name = "category", required = false) String category,
                                                         @RequestParam(name = "name", required = false) String name) {
        if (category != null ^ name != null) {
            if (category != null) {
                return recipeService.findByCategory(category);
            } else {
                return recipeService.findByName(name);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
