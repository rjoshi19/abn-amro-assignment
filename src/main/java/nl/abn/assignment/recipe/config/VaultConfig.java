package nl.abn.assignment.recipe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "recipe.endpoints")
@Data
public class VaultConfig {
    private String username;
    private String password;
}