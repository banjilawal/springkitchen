//package com.lawal.banji.springkitchen.recipe.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "recipe_step_detail")
//public final class RecipeStep {
//
//    @EmbeddedId
//    private RecipeStepKey key;
//
//    @ManyToOne
//    @MapsId("recipeId")
//    @JoinColumn(name = "recipe_id")
//    private Recipe recipe;
//
//    @ManyToOne
//    @MapsId("stepId")
//    @JoinColumn(name = "step_id")
//    private Step step;
//
//    public RecipeStep() { this(null, null, null);}
//
//    public RecipeStep(Recipe recipe, Step step) {
//        this(new RecipeStepKey(recipe, step), recipe, step);
//    }
//
//    public RecipeStep(RecipeStepKey key, Recipe recipe, Step step) {
//        this.key = key;
//        this.recipe = recipe;
//        this.step = step;
//    }
//
//    public RecipeStepKey getKey() {
//        return key;
//    }
//
//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public Step getStepDetail() {
//        return step;
//    }
//
//    public void setKey(RecipeStepKey key) {
//        assert key != null;
//        this.key = key;
//    }
//
//    public void setRecipe(Recipe recipe) {
//        assert recipe != null;
//        this.recipe = recipe;
//        this.key = new RecipeStepKey(this.recipe, step);
//    }
//
//    public void setStep(Step step) {
//        this.step = step;
//        this.key = new RecipeStepKey(recipe, this.step);
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (object == this) return true;
//        if (object == null) return false;
//        if (object instanceof RecipeStep recipeStep)
//            return key.equals(recipeStep.getKey());
//        return false;
//    }
//
//    @Override
//    public int hashCode() { return key.hashCode(); }
//
//    @Override
//    public String toString() {
//        return "RecipeStep[" + key.toString() + "recipe:" + recipe.getTitle() + " step:" + step.getDirections() + "]";
//    }
//
//}
