package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final List<Recipe> recipes = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();
    RecipeController() {}

    /**
     * Adds a new recipe to the list.
     *
     * @param recipe the recipe to add
     * @return the added recipe with its assigned ID
     */
    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        logger.info("POST /api/recipes - Creating new recipe");
        logger.debug("Recipe details: title={}", recipe.getTitle());
        try {
            recipe.setId(counter.incrementAndGet());
            recipes.add(recipe);
            logger.info("Successfully created recipe with id={}", recipe.getId());
            return recipe;
        } catch (Exception e) {
            logger.error("Error occurred while adding recipe: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves all recipes.
     *
     * @return a list of all recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes() {
        logger.info("GET /api/recipes - Retrieving all recipes");
        try {
            logger.info("Successfully retrieved {} recipes", recipes.size());
            return recipes;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving recipes: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id the ID of the recipe to retrieve
     * @return the recipe with the specified ID, or null if not found
     */
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        logger.info("GET /api/recipes/{} - Retrieving recipe", id);
        try {
            for (Recipe recipe : recipes) {
                if (recipe.getId() == id) {
                    logger.info("Successfully retrieved recipe with id={}", id);
                    return recipe;
                }
            }
            logger.warn("Recipe not found with id={}", id);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving recipe with id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param id the ID of the recipe to delete
     * @return true if the recipe was deleted, false if not found
     */
    @DeleteMapping("/{id}")
    public boolean deleteRecipe(@PathVariable long id) {
        logger.info("DELETE /api/recipes/{} - Deleting recipe", id);
        try {
            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i).getId() == id) {
                    recipes.remove(i);
                        logger.info("Successfully deleted recipe with id={}", id);
                    return true;
                }
            }
            logger.warn("Recipe not found for deletion with id={}", id);
            return false;
        } catch (Exception e) {
            logger.error("Error occurred while deleting recipe with id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    /**
     * Updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param updatedRecipe the updated recipe data
     * @return the updated recipe, or null if not found
     */
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable long id, @RequestBody Recipe updatedRecipe) {
        logger.info("PUT /api/recipes/{} - Updating recipe", id);
        logger.debug("Update details: title={}", updatedRecipe.getTitle());

        try {
            for (Recipe recipe : recipes) {
                if (recipe.getId() == id) {
                    recipe.setTitle(updatedRecipe.getTitle());
                    recipe.setDescription(updatedRecipe.getDescription());
                    recipe.setIngredients(updatedRecipe.getIngredients());
                    recipe.setInstructions(updatedRecipe.getInstructions());
                    logger.info("Successfully updated recipe with id={}", id);
                    return recipe;
                }
            }
            logger.warn("Recipe not found for update with id={}", id);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while updating recipe with id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Partially updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param partialRecipe the partial recipe data to update
     * @return the updated recipe, or null if not found
     */
    @PatchMapping("/{id}")
    public Recipe patchRecipe(@PathVariable long id, @RequestBody Recipe partialRecipe) {
        logger.info("PATCH /api/recipes/{} - Partially updating recipe", id);
        logger.debug("Patch details: title={}", partialRecipe.getTitle());

        try {
            for (Recipe recipe : recipes) {
                if (recipe.getId() == id) {
                    if (partialRecipe.getTitle() != null) {
                        recipe.setTitle(partialRecipe.getTitle());
                    }
                    if (partialRecipe.getDescription() != null) {
                        recipe.setDescription(partialRecipe.getDescription());
                    }
                    if (partialRecipe.getIngredients() != null) {
                        recipe.setIngredients(partialRecipe.getIngredients());
                    }
                    if (partialRecipe.getInstructions() != null) {
                        recipe.setInstructions(partialRecipe.getInstructions());
                    }
                    logger.info("Successfully patched recipe with id={}", id);
                    return recipe;
                }
            }
            logger.warn("Recipe not found for patch with id={}", id);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while patching recipe with id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
