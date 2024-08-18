package nl.abn.assignment.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abn.assignment.recipe.model.Ingredient;
import nl.abn.assignment.recipe.model.Recipe;
import nl.abn.assignment.recipe.model.RecipeSearchCriteria;
import nl.abn.assignment.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        recipeRepository.deleteAll(); // Clean up the database before each test
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddRecipe() throws Exception {
        final var recipe = createRecipe();

        mockMvc.perform(post("/api/recipes")
                        .with(httpBasic("recipeuser", "recipepass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var recipes = recipeRepository.findAll();
        assertEquals(1, recipes.size());
        assertEquals("Test Recipe", recipes.get(0).getName());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        final var recipe = createRecipe();
        final var savedRecipe = recipeRepository.save(recipe);

        savedRecipe.setName("Updated Recipe");

        mockMvc.perform(put("/api/recipes/{id}", savedRecipe.getId())
                        .with(httpBasic("recipeuser", "recipepass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedRecipe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var updatedRecipe = recipeRepository.findById(savedRecipe.getId()).orElseThrow();
        assertEquals("Updated Recipe", updatedRecipe.getName());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        final var recipe = createRecipe(); 
        recipe.setName("Recipe to Delete");
        final var savedRecipe = recipeRepository.save(recipe);

        mockMvc.perform(delete("/api/recipes/{id}", savedRecipe.getId())
                        .with(httpBasic("recipeuser", "recipepass")))
                .andExpect(status().isNoContent());

        assertFalse(recipeRepository.findById(savedRecipe.getId()).isPresent());
    }

    @Test
    public void testGetRecipe() throws Exception {
       final var recipe = createRecipe(); 
        recipe.setName("Get Recipe");
        Recipe savedRecipe = recipeRepository.save(recipe);

        mockMvc.perform(get("/api/recipes/{id}", savedRecipe.getId())
                        .with(httpBasic("recipeuser", "recipepass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        final var recipe1 = new Recipe(); 
        recipe1.setName("Recipe 1");
        recipeRepository.save(recipe1);

        final var recipe2 = new Recipe(); 
        recipe2.setName("Recipe 2");
        recipeRepository.save(recipe2);

        mockMvc.perform(get("/api/recipes")
                        .with(httpBasic("recipeuser", "recipepass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))); // Check that there are 2 recipes
    }


    @Test
    public void testSearchRecipes() throws Exception {
        final var recipe = new Recipe(); 
        recipe.setName("Searchable Recipe");
        recipe.setInstructions("Instructions for searchable recipe");
        recipeRepository.save(recipe);

        final var criteria = new RecipeSearchCriteria(true, 2, null,
                null, null);

        mockMvc.perform(post("/api/recipes/search")
                        .with(httpBasic("recipeuser", "recipepass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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