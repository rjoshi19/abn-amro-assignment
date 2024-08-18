package nl.abn.assignment.recipe.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nl.abn.assignment.recipe.exception.ResourceNotFoundException;
import nl.abn.assignment.recipe.model.Recipe;
import nl.abn.assignment.recipe.model.RecipeSearchCriteria;
import nl.abn.assignment.recipe.repository.RecipeRepository;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing recipes.
 * <p>
 * This class provides methods to perform CRUD operations on recipes,
 * including fetching all recipes, adding, updating, deleting, and searching for recipes
 * based on various criteria.
 * </p>
 */
@Service
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final EntityManager entityManager;

    /**
     * Constructs a new RecipeService with the specified RecipeRepository and EntityManager.
     *
     * @param recipeRepository the repository for managing recipe entities
     * @param entityManager    the entity manager for database operations
     */
    public RecipeService(RecipeRepository recipeRepository, EntityManager entityManager) {
        this.recipeRepository = recipeRepository;
        this.entityManager = entityManager;
    }

    /**
     * Retrieves all recipes from the repository.
     *
     * @return a list of all recipes
     */
    public List<Recipe> getAllRecipes() {
        log.debug("Fetching all recipes");
        return recipeRepository.findAll();
    }

    /**
     * Adds a new recipe to the repository.
     *
     * @param recipe the recipe to be added
     * @return the saved recipe
     * @throws DataIntegrityViolationException if the recipe cannot be saved due to integrity constraints
     */
    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        log.debug("Saving recipe::{}", recipe);
        return recipeRepository.save(recipe);
    }

    /**
     * Updates an existing recipe identified by its ID.
     *
     * @param id     the ID of the recipe to be updated
     * @param recipe the recipe data to update
     * @return the updated recipe
     * @throws ResourceNotFoundException if no recipe is found with the specified ID
     */
    @Transactional
    public Recipe updateRecipe(long id, Recipe recipe) {
        log.debug("Updating recipe with id::{}", id);
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe identified by its ID.
     *
     * @param id the ID of the recipe to be deleted
     * @throws ResourceNotFoundException if no recipe is found with the specified ID
     */
    @Transactional
    public void deleteRecipe(long id) {
        log.debug("Deleting recipe with id::{}", id);
        recipeRepository.deleteById(id);
    }

    /**
     * Retrieves a recipe identified by its ID.
     *
     * @param id the ID of the recipe to retrieve
     * @return the recipe with the specified ID
     * @throws ResourceNotFoundException if no recipe is found with the specified ID
     */
    public Recipe getRecipe(long id) {
        log.debug("Getting recipe with id::{}", id);
        return recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
    }

    /**
     * Searches for recipes based on the specified search criteria.
     *
     * @param criteria the criteria for searching recipes
     * @return a list of recipes that match the search criteria
     */
    @Transactional
    public List<Recipe> searchRecipes(RecipeSearchCriteria criteria) {
        log.debug("Searching with params::{}", criteria);
        final var searchSession = Search.session(entityManager);
        return searchSession.search(Recipe.class)
                .where(f -> {
                    var mustHavePredicate = f.bool();
                    var shouldHavePredicate = f.bool();

                    // Text search in instructions
                    if (criteria.instructionSearch() != null && !criteria.instructionSearch().isEmpty()) {
                        mustHavePredicate.must(f.match().field("instructions")
                                .matching(criteria.instructionSearch()).fuzzy());
                    }

                    // Vegetarian filter
                    if (criteria.vegetarian() != null) {
                        mustHavePredicate.must(f.match().field("vegetarian").matching(criteria.vegetarian()));
                    }

                    // Servings filter
                    if (criteria.servings() != null) {
                        mustHavePredicate.must(f.match().field("servings").matching(criteria.servings()));
                    }

                    // Include ingredients
                    if (criteria.includeIngredients() != null && !criteria.includeIngredients().isEmpty()) {
                        for (String ingredient : criteria.includeIngredients()) {
                            shouldHavePredicate.should(f.match().field("ingredients.name").matching(ingredient).fuzzy(1));
                        }
                        mustHavePredicate.must(shouldHavePredicate);
                    }

                    // Exclude ingredients
                    if (criteria.excludeIngredients() != null && !criteria.excludeIngredients().isEmpty()) {
                        for (String ingredient : criteria.excludeIngredients()) {
                            mustHavePredicate.mustNot(f.match().field("ingredients.name").matching(ingredient).fuzzy(1));
                        }
                    }

                    return mustHavePredicate;
                })
                .fetchAllHits();
    }
}
