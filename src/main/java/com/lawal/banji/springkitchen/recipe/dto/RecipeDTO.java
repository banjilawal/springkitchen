package com.lawal.banji.springkitchen.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public final class RecipeDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Length(min=3, max=50, message = "Title must be at least 3 characters  long")
    private String title;

    @NotBlank(message = "Description is required")
    @Length(min=3, max=255, message = "Description must be at least 3 characters long")
    private String description;

    public RecipeDTO() { this(null, null); }

    public RecipeDTO(Builder builder) {
        this(builder.id, builder.title, builder.description);
    }

    public RecipeDTO(String title, String description) {
        this(null, title, description);
    }

    public RecipeDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public RecipeDTO build () { return new RecipeDTO(this); }
    }
}
