package com.lawal.banji.springkitchen.pantry.data;

import com.lawal.banji.springkitchen.pantry.model.PantryItem;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PantryItemGenerator {
    private static final Random random = new Random();

    public static String name () {
        String[] names = {
            "Chicken","Beef","Pork","Salmon","Tuna","Turkey","Lamb","Shrimp","Tofu","Eggs","Broccoli","Spinach","Carrots","Tomatoes",
            "Lettuce","Cucumber","Onions","Peppers","Kale","Cauliflower","Potatoes","Garlic","Corn","Zucchini","Eggplant","Cabbage",
            "Asparagus","Mushrooms","Squash","Radishes","Beans","Lentils","Chickpeas","Kidney beans","Black beans","Pinto beans",
            "Navy beans","Soybeans","Green peas","Edamame","Rice","Wheat","Quinoa","Oats","Barley","Millet","Couscous","Bulgur","Farro",
            "Amaranth","Olive oil","Coconut oil","Vegetable oil","Canola oil","Sunflower oil","Sesame oil","Avocado oil","Grapeseed oil",
            "Peanut oil","Corn oil","Orange juice","Apple juice","Grape juice","Cranberry juice","Tomato juice","Pomegranate juice","Carrot juice",
            "Pineapple juice","Grapefruit juice","Mango juice","Canned tuna","Canned salmon","Canned beans","Canned corn","Canned tomatoes",
            "Canned soup","Canned vegetables","Canned fruit","Canned chicken","Canned sardines","Spaghetti","Linguine","Penne","Fettuccine",
            "Rigatoni","Farfalle","Lasagna","Ravioli","Tortellini","Macaroni","Milk","Cheese","Yogurt","Butter","Cream","Cottage cheese",
            "Sour cream","Greek yogurt","Almond milk","Coconut milk","Bread","Tortillas","Bagels","English muffins","Pita bread","Naan",
            "Flatbread","Croissants","Baguette","Rye bread","Ground beef","Chicken breast","Pork chops","Bacon","Sausage","Ham","Turkey breast",
            "Steak","Lamb chops","Veal","Sweet potatoes","Butternut squash","Pumpkin","Turnips","Parsnips","Beets","Celery","Brussels sprouts",
            "Artichokes","Fennel","Green beans","Snow peas","Snap peas","Lima beans","Fava beans","Mung beans","Adzuki beans","Cannellini beans",
            "Garbanzo beans","Black eyed peas","Quinoa pasta","Whole wheat pasta","Rice noodles","Soba noodles","Egg noodles","Gluten free pasta",
            "Buckwheat noodles","Zucchini noodles","Kelp noodles","Shirataki noodles","Almond milk","Soy milk","Oat milk","Rice milk","Hemp milk",
            "Cashew milk","Macadamia milk","Flax milk","Pea protein milk","Coconut milk","Frozen peas","Frozen corn","Frozen spinach","Frozen broccoli",
            "Frozen mixed vegetables","Frozen berries","Frozen chicken","Frozen fish","Frozen pizza","Frozen meals","Kidney beans","Black beans",
            "Pinto beans","Cannellini beans","Great northern beans","Red beans","White beans","Garbanzo beans","Split peas","Lentils","Extra virgin olive oil",
            "Cold pressed olive oil","Refined olive oil","Light olive oil","Organic olive oil","Spanish olive oil","Italian olive oil","Greek olive oil",
            "California olive oil","Moroccan olive oil","Low fat milk","Whole milk","Skim milk","2% milk","Organic milk","Lactose free milk","Raw milk",
            "Ultra filtered milk","A2 milk","Goat milk","Cheddar cheese","Mozzarella","Parmesan","Gouda","Swiss cheese","Feta","Brie","Blue cheese",
            "Ricotta", "Cream cheese","Wild rice","Brown rice","White rice","Jasmine rice","Basmati rice","Arborio rice","Black rice",
            "Red rice", "Sushi rice","Cauliflower rice", "Canned green beans","Canned asparagus","Canned mushrooms","Canned olives",
            "Canned artichoke hearts", "Canned water chestnuts", "Canned bamboo shoots", "Canned palm hearts","Canned peppers","Canned pumpkin"
        };
        return names[random.nextInt(names.length)];
    }

    public static Long quantityInStock (Long maxQuantity) {
        return random.nextLong(maxQuantity);
    }

    public static Long reorderLevel (Long minLevel, Long maxLevel) {
        return random.nextLong(minLevel, maxLevel);
    }

    public static PantryItem pantryItem (Long minLevel, Long maxLevel) {
        assert minLevel <= maxLevel;
        Long reorderLevel = reorderLevel(minLevel, maxLevel);
        return new PantryItem.Builder()
            .name(name())
            .quantityInStock(quantityInStock(reorderLevel))
            .reorderLevel(reorderLevel)
            .build();
    }

    public static PantryItemDTO pantryItemDTO(Long minLevel, Long maxLevel) {
        Long reorderLevel = reorderLevel(minLevel, maxLevel);
        return new PantryItemDTO(null, name(), quantityInStock(reorderLevel), reorderLevel);
    }

    public static Set<PantryItem> pantryItems (int numberOfItems, Long minReorderLevel, Long maxReorderLevel) {
        Set<PantryItem> pantryItems = new HashSet<>();
        for (int i = 0; i < numberOfItems; i++) {
            PantryItem pantryItem = pantryItem(minReorderLevel, maxReorderLevel);
            System.out.println("adding to set: " + pantryItem);
            pantryItems.add(pantryItem);
        }
        return pantryItems;
    }
}
