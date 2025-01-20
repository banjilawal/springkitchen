package com.lawal.banji.springkitchen.dataset;

import com.lawal.banji.springkitchen.pantry.PantryItem;

import java.util.Random;
import java.util.Set;

public class FoodNameDataset {

    private static final Random random = new Random();

    public static String name () {
            String[] names = {
                "Adzuki beans","A2 milk","Almond milk","Almonds","Amaranth","Apples","Arborio rice","Artichokes","Asparagus","Avocado oil",
                "Bacon","Bagels","Baguette","Baking powder","Baking soda","Bamboo shoots","Bananas","Barbecue sauce","Barley","Basmati rice",
                "Basil","Beans","Beer","Beets","Black beans","Black eyed peas","Black rice","Blue cheese","Bottled juice","Bread",
                "Brie","Broccoli","Brown rice","Brown sugar","Brussels sprouts","Buckwheat noodles","Bulgur","Butter","Butternut squash","Cabbage",
                "Cake","Cake mix","California olive oil","Cannellini beans","Candy","Canola oil","Canned artichoke hearts","Canned asparagus","Canned beans","Canned chicken",
                "Canned corn","Canned fruit","Canned green beans","Canned mushrooms","Canned olives","Canned palm hearts","Canned peaches","Canned peppers","Canned pineapple","Canned pumpkin",
                "Canned salmon","Canned sardines","Canned soup","Canned tomatoes","Canned tuna","Canned vegetables","Canned water chestnuts","Carrot juice","Carrots","Cashew milk",
                "Cashews","Cauliflower","Cauliflower rice","Celery","Cereal","Cheddar cheese","Chickpeas","Chili powder","Chips bags","Chocolate",
                "Chocolate chips","Cinnamon","Cocoa powder","Coconut milk","Coconut oil","Coconut water","Coffee","Cold pressed olive oil","Cookies","Corn",
                "Corn oil","Cornmeal","Cornstarch","Cottage cheese","Couscous","Crackers","Cranberry juice","Cream","Cream cheese","Croissants",
                "Cucumber","Cumin","Curry powder","Deli meat","Diced tomatoes","Donuts","Edamame","Egg noodles","Eggplant","Eggs",
                "Energydrinks","English muffins","Extra virgin olive oil","Farfalle","Farro","Fava beans","Fennel","Feta","Fish sauce","Flatbread",
                "Flax milk","Flour","PantryItem coloring","Fresh pasta","Frozen berries","Frozen broccoli","Frozen chicken","Frozen corn","Frozen dinner","Frozen fish",
                "Frozen fries","Frozen ice cream","Frozen meals","Frozen mixed vegetables","Frozen peas","Frozen pizza","Frozen spinach","Frozen vegetables","Frozen waffles","Fruit cocktail",
                "Garbanzo beans","Garlic","Garlic powder","Ginger","Gluten free pasta","Goat milk","Gouda","Granola","Granola bars","Grape juice",
                "Grapefruit juice","Grapes","Grapeseed oil","Great northern beans","Greek olive oil","Greek yogurt","Green beans","Green peas","Ground beef","Ham",
                "Hemp milk","Honey","Hot chocolate","Hot sauce","Hummus","Ice cream","Instant coffee","Instant noodles","Italian olive oil","Jam",
                "Jasmine rice","Juice","Kale","Kelp noodles","Ketchup","Kidney beans","Kimchi","Kiwi","Lactose free milk","Lamb",
                "Lamb chops","Lasagna","Lemons","Lemonade","Lentils","Lettuce","Light olive oil","Lima beans","Limes","Linguine",
                "Low fat milk","Mac and cheese","Macadamia milk","Macaroni","Mango","Mango juice","Maple syrup","Mayonnaise","Milk","Millet",
                "Miso paste","Mixed vegetables","Moroccan olive oil","Mozzarella","Muffins","Mung beans","Mushrooms","Mustard","Naan","Navy beans",
                "Nori","Nuts","Oat milk","Oatmeal","Oatmeal packs","Oats","Olive oil","Onions","Orange juice","Oranges",
                "Organic milk","Organic olive oil","Palm hearts","Pancake mix","Paprika","Parmesan","Parsnips","Pasta","Pasta sauce","Pea protein milk",
                "Peaches","Peanut oil","Peanut butter","Peanuts","Pears","Peas","Penne","Pepper","Peppers","Pineapple",
                "Pineapple juice","Pinto beans","Pistachios","Pita bread","Plums","Pomegranate juice","Popcorn","Pork","Pork chops","Potatoes",
                "Potato chips","Powdered sugar","Pretzels","Protein bars","Protein powder","Pumpkin","Quinoa","Quinoa pasta","Radishes","Ranch dressing",
                "Raspberries","Ravioli","Raw milk","Red beans","Red rice","Refined olive oil","Rice","Rice cakes","Rice milk","Rice noodles",
                "Rice vinegar","Ricotta","Rigatoni","Rolls","Rye bread","Salad dressing","Salsa","Salt","Salmon","Sardines",
                "Sausage","Sesame oil","Shirataki noodles","Shrimp","Skim milk","Snow peas","Soba noodles","Soda","Soup","Soybeans",
                "Soy milk","Soy sauce","Spaghetti","Spanish olive oil","Sparkling water","Split peas","Spinach","Sports drinks","Squash","Sriracha",
                "Steak","Store bought cookies","Strawberries","Sugar","Sunflower oil","Sushi rice","Sweet potatoes","Swiss cheese","Syrup","Tahini",
                "Tea","Tempeh","Thyme","Tofu","Tomato juice","Tomato sauce","Tomatoes","Tortellini","Tortilla chips","Tortillas",
                "Trail mix","Tuna","Turkey","Turkey breast","Turnips","Turmeric","Ultra filtered milk","Vanilla extract","Veal","Vegetable oil",
                "Vinegar","Waffle mix","Wasabi","Water","Watermelon","Wheat","Whipped cream","White beans","White rice","Whole milk",
                "Whole wheat pasta","Wild rice","Wine","Yogurt","Yeast","Zucchini","Zucchini noodles"
            };
        return names[random.nextInt(names.length)];
    }


    public static PantryItem findPantryItemByName(Set<PantryItem> pantryItems, String name) {
        for (PantryItem pantryItem : pantryItems) {
            if (pantryItem.getName().equals(name)) {
                return pantryItem;
            }
        }
        return null;
    }

    public static PantryItem pantryItem(Set<PantryItem> pantryItems, int minReorderLevel, int maxReorderLevel) {
        if (maxReorderLevel <= 1 || minReorderLevel > maxReorderLevel)
            throw new IllegalArgumentException("Invalid reorder level arguments");
        String name = name();
        while (findPantryItemByName(pantryItems, name) != null) name = name();
        int reorderLevel = random.nextInt(maxReorderLevel - minReorderLevel) + minReorderLevel;
        return new PantryItem(null, name, random.nextInt(reorderLevel * 2), reorderLevel);
    }
}
