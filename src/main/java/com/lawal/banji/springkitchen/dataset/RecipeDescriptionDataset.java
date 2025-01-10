package com.lawal.banji.springkitchen.dataset;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

import java.util.Random;
import java.util.Set;

public class RecipeDescriptionDataset {

    private static final Random random = new Random();

    public static String description () {
        String[] descriptions = {
            "A hearty chicken soup perfect for cold winter nights.",
            "This classic spaghetti Bolognese is a family favorite.",
            "A creamy mushroom risotto that feels like a restaurant-quality dish.",
            "Juicy grilled steaks with a garlic herb butter sauce.",
            "The perfect chocolate chip cookies with a crispy edge and chewy center.",
            "A refreshing summer salad with watermelon and feta cheese.",
            "Tender roast chicken with a hint of lemon and rosemary.",
            "A quick and easy vegetable stir-fry that’s ready in 20 minutes.",
            "Delicious homemade pizza with all your favorite toppings.",
            "A comforting cheesy lasagna straight out of the oven.",
            "Zesty lemon bars with a buttery shortbread crust.",
            "These fluffy pancakes are the perfect start to the day.",
            "A light and tangy Caesar salad with homemade croutons.",
            "A flavorful beef stew cooked low and slow for tender perfection.",
            "Crispy fried chicken that’s crunchy on the outside and juicy on the inside.",
            "Creamy mac and cheese with a golden breadcrumb topping.",
            "Refreshing homemade lemonade for a hot summer day.",
            "A sweet and spicy barbecue pulled pork recipe.",
            "A traditional apple pie with a flaky golden crust.",
            "Garlic butter shrimp paired with pasta or rice for an effortless dinner.",
            "The best fluffy dinner rolls that you’ll want to eat every day.",
            "A rich and creamy New York cheesecake topped with fresh berries.",
            "A simple but satisfying breakfast burrito recipe.",
            "Healthy and delicious quinoa salad with fresh veggies and a lemon vinaigrette.",
            "A classic beef burger with crispy lettuce and juicy tomatoes.",
            "This velvety tomato basil soup goes great with grilled cheese sandwiches.",
            "A crowd-pleasing seven-layer dip perfect for game day.",
            "Sweet and tangy teriyaki chicken served over rice.",
            "Light and flaky croissants that are worth the effort to make.",
            "Sweet cinnamon rolls with cream cheese frosting.",
            "A vibrant mango salsa that pairs perfectly with grilled fish.",
            "Crunchy and golden fish tacos with a creamy lime sauce.",
            "Buttery mashed potatoes with just the right amount of cream and garlic.",
            "Homemade sushi rolls with your favorite fillings and sauces.",
            "Soft and chewy oatmeal raisin cookies filled with warming spices.",
            "A delightful strawberry shortcake topped with fresh whipped cream.",
            "Asian-inspired sesame chicken with a sticky soy glaze.",
            "Roasted Brussels sprouts with crispy bacon and a hint of balsamic glaze.",
            "Fluffy homemade corn muffins served with honey butter.",
            "Tender lamb chops with a flavorful mint jelly sauce.",
            "A spicy chicken curry that’s rich and full of flavor.",
            "A hearty chili con carne topped with shredded cheese and sour cream.",
            "A fun make-your-own taco night with all the trimmings.",
            "A creamy chicken Alfredo pasta that feels indulgent but easy to make.",
            "The lightest, fluffiest angel food cake you’ll ever taste.",
            "A rich and spicy jambalaya straight from Louisiana.",
            "Chocolate lava cakes with gooey molten centers.",
            "A healthy and crisp Greek salad with kalamata olives and feta cheese.",
            "Golden waffles topped with syrup and fresh strawberries.",
            "A simple homemade focaccia bread with olive oil and rosemary.",
            "Sweet and tangy orange chicken paired with steamed rice.",
            "Homemade ice cream sandwiches with chewy cookies.",
            "A vegan lentil stew that’s hearty and packed with vegetables.",
            "A satisfying chicken pot pie with a flaky golden crust.",
            "The ultimate nachos loaded with cheese, jalapeños, and guacamole.",
            "Soft and chewy peanut butter cookies with a crisscross pattern.",
            "A delicious berry smoothie bowl topped with granola and chia seeds.",
            "Perfectly cooked eggs Benedict with creamy hollandaise sauce.",
            "A tropical pineapple fried rice with a hint of curry spice.",
            "A refreshing cucumber and dill yogurt salad.",
            "Homemade marshmallows that are perfect for s’mores or hot chocolate.",
            "Buttery, flaky biscuits that melt in your mouth.",
            "A classic clam chowder that’s hearty and creamy.",
            "A tangy key lime pie with a graham cracker crust and light whipped topping.",
            "Spaghetti carbonara with a silky egg and Parmesan sauce.",
            "Lemon garlic salmon baked in foil for a simple yet elegant dinner.",
            "Crispy sweet potato fries topped with a dash of sea salt.",
            "A Mediterranean-inspired hummus platter with pita bread and vegetables.",
            "Rich chocolate brownies with a gooey fudge center.",
            "A chia seed pudding sweetened with honey and fresh fruit.",
            "A savory shakshuka served with toasted bread for dipping.",
            "Carrot cake with cream cheese frosting and a sprinkle of walnuts.",
            "Warm apple cider donuts coated in cinnamon sugar.",
            "Fresh blueberry muffins that are bursting with flavor.",
            "A tangy tomato bruschetta served on crispy baguette slices.",
            "Grilled zucchini and eggplant drizzled with a balsamic reduction.",
            "A classic French onion soup topped with melted Gruyère cheese.",
            "A cheesy stuffed bell pepper filled with ground beef and rice.",
            "Homemade granola bars packed with oats and dried fruit.",
            "Savory cheddar and chive scones served warm.",
            "A creamy pumpkin soup that’s perfect for fall nights.",
            "Sticky honey garlic chicken wings baked to perfection.",
            "Chocolate chip banana bread that’s moist and irresistible.",
            "Homemade garlic knots brushed with parsley butter.",
            "Sweet and tart raspberry lemonade served over ice.",
            "Roasted cauliflower tacos with a zesty lime crema.",
            "Coconut curry chicken that’s creamy and aromatic.",
            "A bright citrus salad with orange slices and pomegranate seeds.",
            "A butter chicken recipe that’s simple and better than takeout.",
            "A hearty breakfast skillet with potatoes, bacon, and eggs.",
            "Asian food-inspired beef and broccoli stir fry.",
            "Soft pretzels with a crunchy exterior and a buttery twist.",
            "A chocolate mousse so smooth it melts in your mouth.",
            "Elegant baked brie topped with honey and nuts.",
            "Classic deviled eggs with a sprinkle of paprika.",
            "Grilled corn on the cob slathered with chili lime butter.",
            "Tiramisu layered with rich coffee-soaked ladyfingers."
        };
        return descriptions[random.nextInt(descriptions.length)];
    }


    public static Recipe findByDescription(Set<Recipe> recipes, String description) {
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equalsIgnoreCase(description)) return recipe;
        }
        return null;
    }

    public static String recipeDescription(Set<Recipe> recipes) {
        String description = description();
        while (findByDescription(recipes, description) != null) {
            description = description();
        }
        return description;
    }
}
