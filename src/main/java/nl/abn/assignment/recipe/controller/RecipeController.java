package nl.abn.assignment.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.abn.assignment.recipe.model.Recipe;
import nl.abn.assignment.recipe.model.RecipeSearchCriteria;
import nl.abn.assignment.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Operation(summary = "Retrieve all recipes", description = "Fetches a list of all recipes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of recipes")
    })
    @GetMapping()
    public ResponseEntity<List<Recipe>> getRecipe() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(summary = "Retrieve a specific recipe by ID", description = "Fetches a recipe by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The requested recipe"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @Operation(summary = "Add a new recipe", description = "Creates a new recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The created recipe")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipeService.addRecipe(recipe));
    }

    @Operation(summary = "Update an existing recipe", description = "Updates a recipe by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The updated recipe"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
    }

    @Operation(summary = "Delete a recipe", description = "Deletes a recipe by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search for recipes", description = "Searches for recipes based on criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of recipes matching the search criteria")
    })
    @PostMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestBody RecipeSearchCriteria searchCriteria) {
        return ResponseEntity.ok(recipeService.searchRecipes(searchCriteria));
    }
}
