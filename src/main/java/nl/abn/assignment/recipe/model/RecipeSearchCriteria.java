package nl.abn.assignment.recipe.model;

import java.util.List;

public record RecipeSearchCriteria(
        Boolean vegetarian,
        Integer servings,
        List<String> includeIngredients,
        List<String> excludeIngredients,
        String instructionSearch
) {
}
