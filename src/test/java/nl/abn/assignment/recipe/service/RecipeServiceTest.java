package nl.abn.assignment.recipe.service;

import nl.abn.assignment.recipe.exception.ResourceNotFoundException;
import nl.abn.assignment.recipe.model.Ingredient;
import nl.abn.assignment.recipe.model.Recipe;
import nl.abn.assignment.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllRecipes() {
        final var recipe = createRecipe();
        final var recipes = new ArrayList<Recipe>();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        final var result = recipeService.getAllRecipes();
        assertEquals(recipes, result);
        assertEquals(recipe.getName(), result.getFirst().getName());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testAddRecipe() {
        final var recipe = createRecipe();
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        final var result = recipeService.addRecipe(recipe);
        assertEquals(recipe, result);
        assertEquals(recipe.getName(), result.getName());
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    public void testUpdateRecipe() {
        final var recipe = createRecipe();
        recipe.setId(1L);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        final var result = recipeService.updateRecipe(1L, recipe);
        assertEquals(recipe, result);
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    public void testDeleteRecipe() {
        final var recipeId = 1L;

        recipeService.deleteRecipe(recipeId);
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    public void testGetRecipe() {
        final var recipeId = 1L;
        final var recipe = createRecipe();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipe(recipeId);
        assertEquals(recipe, result);
        verify(recipeRepository, times(1)).findById(recipeId);
    }

    @Test
    public void testGetRecipeNotFound() {
        final var recipeId = 1L;
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recipeService.getRecipe(recipeId));
        verify(recipeRepository, times(1)).findById(recipeId);
    }

    private static Recipe createRecipe() {
        final var recipe = new Recipe();
        recipe.setName("Test Recipe");
        recipe.setServings(2);
        recipe.setVegetarian(true);
        Ingredient ingredient_1 = new Ingredient();
        ingredient_1.setName("Onion");
        Ingredient ingredient_2 = new Ingredient();
        ingredient_2.setName("Garlic");
        recipe.setIngredients(List.of(ingredient_1, ingredient_2));
        recipe.setInstructions("Test Instructions");
        return recipe;
    }

}