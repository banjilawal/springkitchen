package com.lawal.banji.springkitchen.recipe.data;

import com.lawal.banji.springkitchen.recipe.model.Step;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepGenerator {
    private static AtomicLong nextId = new AtomicLong(0);
    private static Random random = new Random();

    public static String direction() {
        String[] directions = {
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
        return directions[random.nextInt(directions.length)];
    }

    public static String[] timeStringExtractor(String string) {
        String timeRegex = "\\b(\\d+)\\s*(seconds|minutes|hours|days|weeks)\\b";
        Pattern pattern = Pattern.compile(timeRegex);
        String[] timeList = new String[2];

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            timeList[0] = matcher.group(1);
            timeList[1] = matcher.group(2);
        } else {
            timeList[0] = "10";
            timeList[1] = "minutes";
        }
        return timeList;
    }

    public static Long getMinutes(String[] string) {
        Long duration = Long.parseLong(string[0]);
        if (string[1].equals("seconds")) return 1L;
        else if (string[1].equals("hours")) return duration * 60L ;
        else return duration;
    }

    public static Step stepDetail () {
        String direction = direction();
        return new Step(nextId.incrementAndGet(), direction, getMinutes(timeStringExtractor(direction)));
    }

    public static Step foundStep (Set<Step> steps, String description) {
        for (Step step : steps) {
            if (step.getDirections().equals(description)) return step;
        }
        return null;
    }

    public static Set<Step> uniqueSteps (int numberOfSteps) {
        Set<Step> steps = new HashSet<>();
        for (int i = 0; i < numberOfSteps; i++) {
            String direction = direction();
            while (foundStep(steps, direction) != null) direction = direction();
            steps.add(new Step(nextId.incrementAndGet(), direction, getMinutes(timeStringExtractor(direction))));
        }
        return steps;
    }
}
