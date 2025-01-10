package com.lawal.banji.springkitchen.dataset;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

import java.util.Random;
import java.util.Set;

public class RecipeTitleDataset {

    private static final Random random = new Random();

    public static String title () {
        String[] titles = {
            "Classic Spaghetti Bolognese", "Chicken Tikka Masala", "Vegetarian Lasagna", "Beef Stroganoff",
            "Grilled Salmon with Lemon Butter", "Mushroom Risotto", "Greek Salad with Feta", "Shrimp Scampi",
            "BBQ Pulled Pork Sandwiches", "Vegetable Stir Fry", "Lentil Soup", "Chicken Caesar Salad",
            "Thai Green Curry", "Eggplant Parmesan", "Tandoori Chicken", "French Onion Soup",
            "Pesto Pasta with Sun-Dried Tomatoes", "Cheeseburger Sliders", "Teriyaki Chicken Bowls", "Spinach and Feta Quiche",
            "Creamy Tomato Basil Soup", "Stuffed Bell Peppers", "Beef and Broccoli Stir Fry", "Zucchini Noodles with Pesto",
            "Garlic Butter Shrimp", "Mexican Street Corn", "Chicken Alfredo Pasta", "Vegetarian Chili",
            "Roasted Vegetable Medley", "Honey Glazed Carrots", "Chicken Pot Pie", "Lemon Herb Roasted Chicken",
            "Buffalo Chicken Wings", "Vegan Buddha Bowl", "Beef Chili Con Carne", "Seafood Paella",
            "Garlic Mashed Potatoes", "Ratatouille", "Shepherd’s Pie", "Avocado Toast with Egg",
            "Turkey Meatballs", "Cabbage Stir Fry", "Butternut Squash Soup", "Chicken Enchiladas",
            "Fish Tacos with Mango Salsa", "Caprese Salad", "Lamb Kofta", "Sushi Rolls",
            "Shakshuka", "Pulled Beef Tacos", "Vegan Mac and Cheese", "Pad Thai",
            "Homemade Pizza Margherita", "Gnocchi with Brown Butter Sage Sauce", "Sweet Potato Fries", "Chicken Cordon Bleu",
            "Beef Tacos", "Shrimp Tacos with Avocado Cream", "Korean BBQ Beef", "Pumpkin Soup",
            "Stuffed Mushrooms", "Vegetarian Sushi Rolls", "Fried Rice with Vegetables", "Spinach and Ricotta Ravioli",
            "Chicken Shawarma", "Vegan Lentil Curry", "Miso Soup", "Bacon Wrapped Asparagus",
            "Classic Meatloaf", "Pasta Primavera", "Baked Ziti", "Thai Red Curry",
            "Pork Chops with Apple Sauce", "Lobster Bisque", "Vegan Pad Thai", "Bruschetta",
            "Cauliflower Rice Stir Fry", "Clam Chowder", "Quinoa Salad with Cranberries", "BBQ Ribs",
            "Stuffed Cabbage Rolls", "Beef Wellington", "Chicken Katsu", "Roasted Brussels Sprouts",
            "Vegetable Tempura", "Mango Chicken Salad", "Garlic Parmesan Chicken", "Veggie Burgers",
            "Roasted Red Pepper Hummus", "Falafel Wraps", "Spinach Artichoke Dip", "Coconut Shrimp",
        };
        return titles[random.nextInt(titles.length)];
    }

    public static Recipe findByTitle(Set<Recipe> recipes, String title) {
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equalsIgnoreCase(title)) return recipe;
        }
        return null;
    }

    public static String recipeTitle(Set<Recipe> recipes) {
        String title = title();
        while (findByTitle(recipes, title) != null) {
            title = title();
        }
        return title;
    }
}
