package nl.abn.assignment.recipe.functional;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import nl.abn.assignment.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@CucumberContextConfiguration
@SpringBootTest
@EnableTransactionManagement
public class CucumberSpringConfiguration {
    @Autowired
    private RecipeRepository recipeRepository;

    @Before
    public void clearDatabase() {
        // Get all entity names
        recipeRepository.deleteAll();
    }
}
