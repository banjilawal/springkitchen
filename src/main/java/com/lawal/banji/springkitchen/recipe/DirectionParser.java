//package com.lawal.banji.springkitchen.recipe;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class RecipeParser {
//
//    private String instruction;
//
//    public RecipeParser(String instruction) {
//        this.instruction = instruction;
//    }
//
//    public Ingredient getIngredient() {
//        return extractIngredients(instruction).get(0);
//    }
//
//    public Long getMinutesDuration () {
//
//    }
//    public static class Ingredient {
//        private String quantity;
//        private String unit;
//        private String name;
//
//        public Ingredient(String quantity, String unit, String name) {
//            this.quantity = quantity;
//            this.unit = unit;
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return String.format("Ingredient{quantity='%s', unit='%s', name='%s'}", quantity, unit, name);
//        }
//    }
//
//    public static List<Ingredient> extractIngredients(String instruction) {
//        List<Ingredient> ingredients = new ArrayList<>();
//
//        // Pattern to match quantities with units and ingredients
//        Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)?\\s*(g|ml|tbsp|(?:chicken\\s)?breasts|(?:cherry\\s)?tomatoes|cucumber|potatoes|egg)\\s*");
//
//        Matcher matcher = pattern.matcher(instruction);
//
//        while (matcher.find()) {
//            String quantity = matcher.group(1) != null ? matcher.group(1) : "";
//            String unitOrIngredient = matcher.group(2);
//
//            // Determine if the match is a unit or an ingredient without unit
//            if (unitOrIngredient.matches("g|ml|tbsp")) {
//                // Look ahead to get the ingredient name
//                int end = matcher.end();
//                String remainingText = instruction.substring(end);
//                Matcher ingredientMatcher = Pattern.compile("([a-zA-Z\\s]+?)(?=\\s*(?:,|and|\\.|$))").matcher(remainingText);
//                if (ingredientMatcher.find()) {
//                    String ingredientName = ingredientMatcher.group(1).trim();
//                    ingredients.add(new Ingredient(quantity, unitOrIngredient, ingredientName));
//                }
//            } else {
//                // Case where the ingredient doesn't have a separate unit
//                ingredients.add(new Ingredient(quantity, "", unitOrIngredient));
//            }
//        }
//
//        return ingredients;
//    }
//}
