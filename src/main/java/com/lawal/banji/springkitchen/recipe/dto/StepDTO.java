package com.lawal.banji.springkitchen.recipe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class StepDTO {

    private Long id;

    @NotBlank(message = "Directions are required")
    @Length(min=3, max=50, message = "Title must be at least 3 characters  long")
    private String directions;

    @Min(value=0, message = "Minutes duration must be greater than zero")
    private Long minutesDuration;

    public StepDTO() { this(null, null, null); }

    public StepDTO(Builder builder) {
        this(builder.id, builder.directions, builder.minutesDuration);
    }

    public StepDTO(String directions, Long minutesDuration) {
        this(null, directions, minutesDuration);
    }

    public StepDTO(Long id, String directions, Long minutesDuration) {
        this.id = id;
        this.directions = directions;
        this.minutesDuration = minutesDuration;
    }

    public Long getId() {
        return id;
    }

    public String getDirections() {
        return directions;
    }

    public Long getMinutesDuration() {
        return minutesDuration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String directions;
        private Long minutesDuration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder directions(String directions) {
            this.directions = directions;
            return this;
        }

        public Builder minutesDuration(Long minutesDuration) {
            this.minutesDuration = minutesDuration;
            return this;
        }

        public StepDTO build () { return new StepDTO(this); }
    }
}
