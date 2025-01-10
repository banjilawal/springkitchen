package com.lawal.banji.springkitchen.picture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pictures")
public class Picture {

    /* String constants */
    public static final String PICTURE_UPDATE_SOURCE_CANNOT_BE_NULL = "Picture update Source cannot be null";
    public static final String INVALID_UPDATE_SOURCE_ID = "Picture update source id is not ivalid. Update failed";

    public static final String PICTURE_NAME_CANNOT_BE_NULL = "Picture name cannot be null or blank";
    public static final String PICTURE_PATH_CANNOT_BE_NULL = "Picture path cannot be null or blank";

    private static final String METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS = "Picture method does not allow null id parameters";
    public static final String NULL_STEPS_COLLECTION_NOT_ALLOWED = "Picture method doe not allow null step collections";
    public static final String NULL_MEALS_COLLECTION_NOT_ALLOWED = "Picture method does not allow null meal collections";

    public static final String NULL_STEP_CANNOT_BE_PASSED_TO_PICTURE_METHOD = "Picture method does not allow a null step";
    public static final String NULL_MEAL_CANNOT_BE_PASSED_TO_PICTURE_METHOD = "Picture method does not allow a null  meal";

    /* logger */
    private static final Logger logger = LoggerFactory.getLogger(Picture.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = PICTURE_NAME_CANNOT_BE_NULL)
    private String name;

    @Column(nullable = false)
    @NotBlank(message = PICTURE_PATH_CANNOT_BE_NULL)
    private String path;

    /* Bidirectional fields */

    /* Constructors */
    public Picture() {}

    public Picture(Long id, String name, String path) {
        this.id = id;
        setName(name);
        setPath(path);
    }

    /* Builders */
    public static Builder builder () { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String path;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Picture build() { return new Picture(id, name, path); }
    }

    /* Getters */
    public Long getId() { return id; }

    public String getName() { return name; }

    public String getPath() { return path; }

    /* Setters */
    public void setId(Long id) { this.id = id; }

    public void setName(String name) {
        if (name == null || name.trim().isBlank()) loggingExceptionHandler(PICTURE_NAME_CANNOT_BE_NULL);
        else { this.name = name.trim(); }
    }

    public void setPath(String path) {
        if (path == null || path.trim().isBlank()) loggingExceptionHandler(PICTURE_PATH_CANNOT_BE_NULL);
        else { this.path = path.trim(); }
    }

    /* Update methods */
    public void getUpdate(Picture source) {
        if (source == this) return;

        if (source == null) loggingExceptionHandler(PICTURE_UPDATE_SOURCE_CANNOT_BE_NULL);
        else if (source.getId() == null || !id.equals(source.getId())) loggingExceptionHandler(INVALID_UPDATE_SOURCE_ID);
        else if (source.getName() == null || source.getName().trim().isBlank()) loggingExceptionHandler(PICTURE_NAME_CANNOT_BE_NULL);
        else if (source.getPath() == null || source.getPath().trim().isBlank()) loggingExceptionHandler(PICTURE_PATH_CANNOT_BE_NULL);
        else {
            setName(source.getName());
            setPath(source.getPath());
        }
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object instanceof Picture picture ) {
            boolean equalIds = id != null && picture.getId() != null && id.equals(picture.getId());
            boolean equalNames = name != null && picture.getName() != null && name.equalsIgnoreCase(picture.getName());
            boolean equalPaths = path != null && picture.getPath() != null
                && path.equalsIgnoreCase(picture.getPath());
            return equalIds && equalNames && equalPaths;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(name, path);
    }

    /* String methods */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[pictureId:" + id
            + " name:" + name
            + " path:" + path + ']';
    }

    /* logging methods */
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}