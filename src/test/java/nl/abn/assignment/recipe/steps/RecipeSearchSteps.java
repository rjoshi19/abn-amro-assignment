package nl.abn.assignment.recipe.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import nl.abn.assignment.recipe.model.Recipe;
import nl.abn.assignment.recipe.model.RecipeSearchCriteria;
import nl.abn.assignment.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeSearchSteps {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private EntityManager entityManager;

    private RecipeSearchCriteria criteria;
    private List<Recipe> result;

    @Given("the following recipes exist:")
    public void givenTheFollowingRecipesExist(List<Recipe> recipes) {
        for (final var recipe : recipes) {
            recipeService.addRecipe(recipe);
        }
    }

    @When("I search for recipes with vegetarian set to {string}")
    public void whenISearchForRecipesWithVegetarianSetTo(String vegetarian) {
        criteria = new RecipeSearchCriteria(Boolean.parseBoolean(vegetarian), null, null, null, null);
        result = recipeService.searchRecipes(criteria);
    }

    @When("I search for recipes with servings equal to {int}")
    public void whenISearchForRecipesWithServingsEqualTo(Integer servings) {
        criteria = new RecipeSearchCriteria(null, servings, null, null, null);
        result = recipeService.searchRecipes(criteria);
    }

    @When("I search for recipes including the ingredients {string}")
    public void whenISearchForRecipesIncludingTheIngredient(String ingredientList) {
        final var ingredients = Arrays.asList(ingredientList.split(","));
        criteria = new RecipeSearchCriteria(null, null, ingredients, null, null);
        result = recipeService.searchRecipes(criteria);
    }

    @When("I search for recipes excluding the ingredient {string}")
    public void whenISearchForRecipesExcludingTheIngredient(String ingredient) {
        criteria = new RecipeSearchCriteria(null, null, null, List.of(ingredient), null);
        result = recipeService.searchRecipes(criteria);
    }

    @When("I search for recipes with instruction text {string}")
    public void whenISearchForRecipesWithInstructionText(String instructionText) {
        criteria = new RecipeSearchCriteria(null, null, null, null, instructionText);
        result = recipeService.searchRecipes(criteria);
    }

    @Then("I should see the following recipes:")
    public void thenIShouldSeeTheFollowingRecipes(List<Recipe> expectedRecipes) {
        assertEquals(expectedRecipes.size(), result.size());
        for (final var expected : expectedRecipes) {
            assertTrue(result.stream().anyMatch(res-> res.getName().equals(expected.getName())));
            assertTrue(result.stream().anyMatch(res-> res.isVegetarian() == expected.isVegetarian()));
            assertTrue(result.stream().anyMatch(res-> res.getServings() == expected.getServings()));
        }
    }
}
