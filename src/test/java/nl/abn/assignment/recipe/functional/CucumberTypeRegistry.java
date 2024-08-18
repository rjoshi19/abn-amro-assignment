package nl.abn.assignment.recipe.functional;

import io.cucumber.java.DataTableType;
import nl.abn.assignment.recipe.model.Ingredient;
import nl.abn.assignment.recipe.model.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CucumberTypeRegistry {

    @DataTableType
    public Recipe recipeEntry(Map<String, String> entry) {
        final var recipe = new Recipe();
        recipe.setName(entry.get("name"));
        recipe.setVegetarian(Boolean.parseBoolean(entry.get("vegetarian")));
        recipe.setServings(Integer.parseInt(entry.get("servings")));
        recipe.setInstructions(entry.get("instructions"));
        
        if (entry.containsKey("ingredients")) {
            final var ingredients = Arrays.stream(entry.get("ingredients").split(","))
                .map(String::trim)
                .map(ingredientName -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientName);
                    ingredient.setRecipe(recipe);
                    return ingredient;
                })
                .collect(Collectors.toList());
            recipe.setIngredients(ingredients);
        }
        
        return recipe;
    }
}
