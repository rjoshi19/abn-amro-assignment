Feature: Recipe Search

  Scenario: Search for recipes with specific servings
    Given the following recipes exist:
      | name        | vegetarian | servings | instructions          | ingredients          |
      | Pasta       | false      | 4        | Boil pasta.           | Pasta, Tomato        |
      | Salad       | true       | 2        | Mix greens.           | Lettuce, Tomato      |
    When I search for recipes with servings equal to 4
    Then I should see the following recipes:
      | name        | vegetarian | servings | instructions          |
      | Pasta       | false      | 4        | Boil pasta.           |

  Scenario: Search for recipes including specific ingredients
    Given the following recipes exist:
      | name        | vegetarian | servings | instructions          | ingredients          |
      | Veggie Pizza| true       | 4        | Bake at 350.          | Cheese, Tomato       |
      | Meat Pizza  | false      | 4        | Bake at 350.          | Cheese, Pepperoni    |
    When I search for recipes including the ingredients "Tomato, Pepperoni"
    Then I should see the following recipes:
      | name        | vegetarian | servings | instructions          |
      | Veggie Pizza| true       | 4        | Bake at 350.          |
      | Meat Pizza  | false      | 4        | Bake at 350.          |

  Scenario: Search for recipes excluding specific ingredients
    Given the following recipes exist:
      | name        | vegetarian | servings | instructions          | ingredients          |
      | Veggie Salad| true       | 2        | Toss ingredients.     | Lettuce, Tomato      |
      | Chicken Salad| false     | 3        | Mix chicken.          | Chicken, Lettuce     |
    When I search for recipes excluding the ingredient "Chicken"
    Then I should see the following recipes:
      | name        | vegetarian | servings | instructions          |
      | Veggie Salad| true       | 2        | Toss ingredients.     |

  Scenario: Search for recipes with instruction text
    Given the following recipes exist:
      | name        | vegetarian | servings | instructions          | ingredients          |
      | Pancakes    | true       | 4        | Mix and cook batter.  | Flour, Milk          |
      | Omelette    | true       | 2        | Whisk eggs.           | Eggs, Cheese         |
    When I search for recipes with instruction text "Mix"
    Then I should see the following recipes:
      | name        | vegetarian | servings | instructions          |
      | Pancakes    | true       | 4        | Mix and cook batter.  |

  Scenario: Search for vegetarian recipes
    Given the following recipes exist:
      | name        | vegetarian | servings | instructions          | ingredients          |
      | Veggie Soup | true       | 2        | Boil vegetables.      | Carrot, Potato       |
      | Chicken Soup| false      | 4        | Cook chicken.         | Chicken, Onion       |
    When I search for recipes with vegetarian set to "true"
    Then I should see the following recipes:
      | name        | vegetarian | servings | instructions          |
      | Veggie Soup | true       | 2        | Boil vegetables.      |