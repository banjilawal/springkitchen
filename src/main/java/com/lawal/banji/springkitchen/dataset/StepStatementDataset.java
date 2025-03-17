package com.lawal.banji.springkitchen.dataset;

import java.util.Random;

public class StepStatementDataset {


    private static Random random = new Random();

//    public static String directions() {
//        String[] directions = {
//            "(5 min) Heat 1 tbsp olive oil in a large pan over medium heat.",
//            "(3 min) Add 1 onion (finely chopped) and sauté until translucent.",
//            "(2 min) Stir in 2 cloves garlic (minced) and cook until fragrant.",
//            "(7 min) Increase heat and add 500g ground beef, cooking until browned.",
//            "(3 min) Mix in 2 tbsp tomato paste and stir thoroughly.",
//            "(10 min) Pour in 400g canned tomatoes and stir to combine.",
//            "(2 min) Sprinkle in 1 tsp sugar and season with salt and pepper to taste.",
//            "(20 min) Simmer sauce on low heat, stirring occasionally.",
//            "(12 min) Cook 400g spaghetti in boiling salted water until al dente.",
//            "(1 min) Drain spaghetti and mix it with sauce.",
//            "(1 min) Serve hot with grated Parmesan cheese.",
//            "(5 min) Mix 3 tbsp soy sauce, 2 tbsp oyster sauce, and 1 tsp cornstarch in a bowl.",
//            "(10 min) Marinate 500g sliced chicken in the sauce.",
//            "(5 min) Heat 1 tbsp sesame oil in a wok over high heat.",
//            "(2 min) Add 2 cloves garlic (minced) and 1 tsp ginger (grated), stirring until fragrant.",
//            "(5 min) Add marinated chicken and cook until no longer pink.",
//            "(3 min) Toss in 1 red bell pepper (sliced) and stir.",
//            "(3 min) Add 2 carrots (sliced) and cook until slightly tender.",
//            "(4 min) Add 1 broccoli head (florets) and stir-fry until bright green.",
//            "(1 min) Serve hot over cooked rice.",
//            "(10 min) Preheat oven to 175°C (350°F).",
//            "(5 min) Cream 225g butter, 200g sugar, and 220g brown sugar until fluffy.",
//            "(2 min) Beat in 2 eggs one at a time.",
//            "(1 min) Stir in 1 tsp vanilla extract.",
//            "(3 min) Whisk 400g flour, 1 tsp baking soda, and 1 tsp salt in a bowl.",
//            "(3 min) Gradually add dry ingredients to butter mixture, stirring continuously.",
//            "(2 min) Fold in 300g chocolate chips.",
//            "(5 min) Drop spoonfuls of dough onto baking sheet.",
//            "(10-12 min) Bake until golden brown.",
//            "(5 min) Cool on tray before transferring to wire rack.",
//            "(5 min) Heat 1 tbsp vegetable oil in a pan over medium heat.",
//            "(4 min) Fry 2 eggs until whites are set and yolks slightly runny.",
//            "(3 min) Toast 2 slices of bread until golden.",
//            "(1 min) Spread 1 tbsp butter on each slice.",
//            "(1 min) Top each toast with a fried egg and sprinkle with salt and pepper.",
//            "(15 min) Boil 4 potatoes (peeled, chopped) until tender.",
//            "(3 min) Mash potatoes with 50g butter and 100ml milk.",
//            "(2 min) Season mashed potatoes with salt and pepper.",
//            "(8 min) Steam 200g green beans until tender-crisp.",
//            "(1 min) Toss beans with 1 tbsp olive oil and salt.",
//            "(10 min) Grill 2 chicken breasts until internal temperature reaches 75°C.",
//            "(3 min) Let chicken rest before slicing.",
//            "(4 min) Mix 150g lettuce, 100g cherry tomatoes, and 1 cucumber (sliced).",
//            "(2 min) Drizzle salad with 2 tbsp balsamic vinaigrette.",
//            "(6 min) Boil 3 eggs until hard-boiled.",
//            "(3 min) Peel and slice eggs.",
//            "(4 min) Mix eggs into salad.",
//            "(10 min) Cook 1 cup rice until water is absorbed.",
//            "(3 min) Fluff rice with a fork.",
//            "(1 min) Serve rice hot.",
//            "(5 min) Blend 2 bananas, 200ml milk, and 1 tbsp honey.",
//            "(1 min) Pour smoothie into glass.",
//            "(3 min) Whisk 3 eggs with 100ml milk and salt.",
//            "(5 min) Scramble eggs in 1 tbsp butter over medium heat.",
//            "(1 min) Serve scrambled eggs hot."
//        };
//        return directions[random.nextInt(directions.length)];
//    }

    public static String instruction() {
        String[] statements = {
            "Simmer over low heat for 45 minutes until reduced by half","Whisk continuously for 2 minutes until thoroughly combined","Bake at 350 degrees for 25 minutes until golden brown",
            "Steam in the bamboo steamer for 12 minutes until tender","Sauté in the wok over high heat for 3 minutes until crispy","Grill on medium-high heat for 8 minutes per side",
            "Broil under high heat for 5 minutes until charred","Knead on a floured surface for 10 minutes until elastic","Fold gently with a spatula for 30 seconds to combine",
            "Blend in the food processor for 1 minute until smooth","Roast in the oven at 400 degrees for 35 minutes","Dice with a sharp knife for 2 minutes until uniform size",
            "Mince using a chef's knife for 1 minute until very fine","Marinate in the refrigerator for 4 hours until flavors combine","Reduce the sauce over medium heat for 20 minutes until thickened",
            "Proof in a warm place for 1 hour until doubled in size","Ferment in a dark place for 3 days until bubbling","Blanch in boiling water for 2 minutes until bright",
            "Caramelize over medium-low heat for 30 minutes until golden","Toast under the broiler for 2 minutes until lightly browned","Crush with a mortar and pestle for 1 minute until powdered",
            "Strain through a fine-mesh sieve for 5 minutes until clear","Braise in the Dutch oven for 3 hours until tender","Deglaze the pan for 30 seconds while scraping the bottom",
            "Poach in simmering water for 4 minutes until set","Scramble over medium heat for 2 minutes until fluffy","Render over low heat for 10 minutes until crispy",
            "Julienne with a sharp knife for 3 minutes until uniform","Cure in the refrigerator for 24 hours until firm","Steep in hot water for 5 minutes until fragrant",
            "Char under the broiler for 3 minutes until blackened","Pickle in brine for 1 week until tangy","Freeze in the ice cream maker for 25 minutes until set",
            "Smoke in the smoker for 6 hours until tender","Shred using two forks for 2 minutes until pulled apart","Garnish with precision for 1 minute until aesthetically pleasing",
            "Torch with a kitchen torch for 30 seconds until caramelized","Grind in the spice grinder for 30 seconds until powdered","Emulsify with an immersion blender for 1 minute until smooth",
            "Toss in the bowl for 1 minute until evenly coated","Rest on the counter for 10 minutes before cutting","Chill in the refrigerator for 2 hours until set",
            "Proof in the proofing box for 45 minutes until risen","Dry in the food dehydrator for 8 hours until crispy","Mash with a potato masher for 2 minutes until smooth",
            "Purée in the blender for 3 minutes until silky","Score with a sharp knife for 30 seconds in a crosshatch pattern","Truss with kitchen twine for 2 minutes until secure",
            "Temper over a double boiler for 5 minutes until glossy","Beat with an electric mixer for 3 minutes until fluffy","Fold with a rubber spatula for 1 minute until incorporated",
            "Shape with wet hands for 2 minutes until uniform","Roll with a rolling pin for 3 minutes until even thickness","Pipe using a pastry bag for 2 minutes until decorative",
            "Crush with a rolling pin for 1 minute until finely ground","Shave with a vegetable peeler for 2 minutes until thin","Grease the pan for 30 seconds until completely coated",
            "Line the baking sheet for 1 minute with parchment paper","Preheat the oven for 15 minutes until temperature is reached","Calibrate the thermometer for 1 minute in ice water",
            "Sharpen the knife for 2 minutes on the whetstone","Season the cast iron for 1 hour in the oven","Clean the cutting board for 2 minutes with hot soapy water",
            "Sanitize the workspace for 5 minutes before beginning","Organize mise en place for 10 minutes until ready","Label containers for 2 minutes with contents and date",
            "Measure with precision for 1 minute using digital scale","Level with a straight edge for 30 seconds until even","Sift through a fine-mesh strainer for 1 minute until uniform",
            "Aerate with a whisk for 30 seconds until light","Separate into portions for 2 minutes using a scale","Layer in the baking dish for 5 minutes until even",
            "Cover with foil for 30 seconds until sealed","Vent the lid for 15 seconds to release steam","Adjust seasoning for 1 minute until balanced",
            "Test temperature for 30 seconds with a thermometer","Remove from heat for immediate cooling","Transfer to serving plates for 2 minutes until arranged",
            "Cool on a wire rack for 30 minutes until room temperature","Store in airtight containers for long-term preservation","Reheat gently for 5 minutes until warmed through",
            "Portion onto plates for 3 minutes until presentable","Garnish with herbs for 1 minute until attractive","Serve immediately while at optimal temperature",
            "Plate with precision for 2 minutes until aesthetically pleasing","Drizzle sauce for 30 seconds in a decorative pattern","Stack carefully for 1 minute until balanced",
            "Position garnishes for 30 seconds until artfully arranged","Wipe plate edges for 30 seconds until clean","Polish glassware for 1 minute until spotless",
            "Arrange table setting for 5 minutes until perfect","Carve at the table for 3 minutes until portioned","Present to guests for immediate service",
            "Finish with sauce for 30 seconds before serving","Clear plates for 2 minutes between courses","Reset place settings for 1 minute before next course",
            "Clean as you go for continuous kitchen organization","Maintain workspace for optimal cooking efficiency","Document recipe changes for 2 minutes after completion",
            "Review preparation notes for 5 minutes before starting","Plan timing for 10 minutes before beginning service","Stage equipment for 5 minutes until organized",
            "Coordinate service for 2 minutes with kitchen staff"
        };
//        System.out.println("available directions " + directions.length);
        return statements[random.nextInt(statements.length)];
    }
}