-- Clear existing data
DELETE FROM ingredient;
DELETE FROM recipe;

-- Insert sample recipes
INSERT INTO recipe (name, vegetarian, servings, instructions) VALUES
('Vegetarian Pasta Primavera', true, 4, 'Boil pasta until al dente. In a large skillet, sauté garlic and mixed vegetables. Toss pasta with vegetables, olive oil, and Parmesan cheese. Season with salt and pepper to taste.'),
('Grilled Chicken Caesar Salad', false, 2, 'Grill chicken breast until cooked through. Chop romaine lettuce and toss with Caesar dressing. Top with grilled chicken, croutons, and extra Parmesan cheese.'),
('Baked Salmon with Lemon-Dill Sauce', false, 3, 'Preheat oven to 400°F (200°C). Season salmon fillets and bake for 12-15 minutes. Meanwhile, prepare a sauce with lemon juice, dill, and Greek yogurt. Serve salmon with the sauce.'),
('Vegetable Stir Fry with Tofu', true, 3, 'Press and cube tofu. Stir fry tofu until golden. Remove and set aside. Stir fry mixed vegetables in a wok. Add tofu back in with soy sauce and sesame oil. Serve over rice.'),
('Beef Tacos with Homemade Salsa', false, 4, 'Brown ground beef with taco seasoning. Warm taco shells in the oven. Prepare homemade salsa by mixing diced tomatoes, onions, cilantro, and lime juice. Assemble tacos with beef, salsa, lettuce, and cheese.'),
('Mushroom Risotto', true, 3, 'Sauté mushrooms and set aside. In the same pan, toast arborio rice with onions. Gradually add warm vegetable broth, stirring constantly until rice is creamy. Stir in mushrooms and Parmesan cheese.'),
('Grilled Vegetable Quinoa Bowl', true, 2, 'Cook quinoa according to package instructions. Grill assorted vegetables like zucchini, bell peppers, and eggplant. Arrange grilled vegetables over quinoa and drizzle with a lemon-tahini dressing.'),
('Spicy Shrimp Curry', false, 4, 'Sauté onions, garlic, and ginger. Add curry paste and coconut milk. Simmer with diced vegetables. Add shrimp and cook until pink. Serve over basmati rice with fresh cilantro.'),
('Oven-Roasted Chicken with Herbs', false, 4, 'Preheat oven to 425°F (220°C). Rub whole chicken with herb mixture. Roast for 1 hour or until internal temperature reaches 165°F (74°C). Let rest before carving.'),
('Vegetarian Chili', true, 6, 'In a large pot, sauté onions and garlic. Add diced vegetables, canned tomatoes, and beans. Simmer with vegetable broth and chili spices. Cook until vegetables are tender. Serve with cornbread.');

-- Insert ingredients for each recipe
INSERT INTO ingredient (recipe_id, name) VALUES
(1, 'pasta'), (1, 'mixed vegetables'), (1, 'garlic'), (1, 'olive oil'), (1, 'Parmesan cheese'),
(2, 'chicken breast'), (2, 'romaine lettuce'), (2, 'Caesar dressing'), (2, 'croutons'), (2, 'Parmesan cheese'),
(3, 'salmon fillet'), (3, 'lemon'), (3, 'dill'), (3, 'Greek yogurt'),
(4, 'tofu'), (4, 'mixed vegetables'), (4, 'soy sauce'), (4, 'sesame oil'), (4, 'rice'),
(5, 'ground beef'), (5, 'taco shells'), (5, 'taco seasoning'), (5, 'tomatoes'), (5, 'onions'), (5, 'cilantro'), (5, 'lime'), (5, 'lettuce'), (5, 'cheese'),
(6, 'arborio rice'), (6, 'mushrooms'), (6, 'onions'), (6, 'vegetable broth'), (6, 'Parmesan cheese'),
(7, 'quinoa'), (7, 'zucchini'), (7, 'bell peppers'), (7, 'eggplant'), (7, 'lemon'), (7, 'tahini'),
(8, 'shrimp'), (8, 'onions'), (8, 'garlic'), (8, 'ginger'), (8, 'curry paste'), (8, 'coconut milk'), (8, 'mixed vegetables'), (8, 'basmati rice'), (8, 'cilantro'),
(9, 'whole chicken'), (9, 'mixed herbs'),
(10, 'onions'), (10, 'garlic'), (10, 'mixed vegetables'), (10, 'canned tomatoes'), (10, 'beans'), (10, 'vegetable broth'), (10, 'chili spices');