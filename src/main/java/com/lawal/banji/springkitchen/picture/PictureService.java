package com.lawal.banji.springkitchen.picture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PictureService {

    public static final String PICTURE_REPO_IS_EMPTY = "PictureRepo is empty";

    public static final String PICTURE_NOT_FOUND_BY_ID = "PictureService did not find item with ID %d";
    public static final String PICTURE_NOT_FOUND_BY_NAME = "PictureService did not find item with NAME %s";
    public static final String PICTURE_NOT_FOUND_BY_PATH = "PictureService did not find item with PATH %s";

    public static final String PICTURE_ID_CANNOT_BE_NULL = "PictureService method does not accept null pictureID";
    public static final String PICTURE_FAILED_VALIDATION = "The picture did not pass PictureService validation checks";
    public static final String METHOD_DOES_NOT_ALLOW_NULL_PICTURE_AS_PARAMETER = "Null picture cannot be passed to PictureService method";

    public static final String PICTURE_UPDATE_SOURCE_FAILED_VALIDATION = "Picture update source validation failed";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "One or more validation checks failed. PictureService.update() failed";

    public static final String PICTURE_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(PictureService.class);

    private final PictureRepo pictureRepo;

    @Autowired
    public PictureService(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    /* Create methods */
    @Transactional
    public Picture save (Picture picture) {
        if (!isValidPicture(picture)) loggingExceptionHandler(PICTURE_FAILED_VALIDATION);
        return pictureRepo.save(picture);
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() { return pictureRepo.count();}

    @Transactional(readOnly = true)
    public Picture findById(Long id) {
        if (id == null) loggingExceptionHandler(PICTURE_ID_CANNOT_BE_NULL);
        else {
            Picture picture = pictureRepo.findById(id).orElse(null);
            if (picture == null) return nullPictureLogHandler(String.format(PICTURE_NOT_FOUND_BY_ID, id));
            return pictureRepo.save(picture);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Picture findByName(String name) {
        if (name == null || name.trim().isBlank()) loggingExceptionHandler(Picture.PICTURE_NAME_CANNOT_BE_NULL);

        Picture picture = pictureRepo.findByNameIgnoreCase(name);
        if (picture == null) { return nullPictureLogHandler(String.format(PICTURE_NOT_FOUND_BY_NAME, name)); }
        else {
            return pictureRepo.save(picture);
        }
    }

    @Transactional(readOnly = true)
    public Picture findByPath(String path) {
        if (path == null || path.trim().isBlank()) loggingExceptionHandler(Picture.PICTURE_PATH_CANNOT_BE_NULL);

        Picture picture = pictureRepo.findByPathIgnoreCase(path);
        if (picture == null) { return nullPictureLogHandler(String.format(PICTURE_NOT_FOUND_BY_NAME, path)); }
        else {
            return pictureRepo.save(picture);
        }
    }

    @Transactional(readOnly = true)
    public List<Picture> findAll() {
        return pictureRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Picture> search(String string) {
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return new HashSet<>(pictureRepo.search(string));
    }

    @Transactional(readOnly = true)
    public Picture randomPicture() {
        long count = count();
        if (count == 0) {
            logger.info(PICTURE_REPO_IS_EMPTY + " PictureService.randomPicture() cannot return random picture");
            return null;
        }
        return pictureRepo.findAll().get(random.nextInt((int) count));
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (Picture picture : pictureRepo.findAll()) {
            stringBuilder.append(picture.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public Picture update (Long targetId, Picture source) {
        if (!areValidUpdateParameters(targetId, source)) loggingExceptionHandler(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);

        Picture target = findById(targetId);
        if (target == null) return nullPictureLogHandler(String.format(PICTURE_NOT_FOUND_BY_ID, targetId));

        target.getUpdate(source);
        return pictureRepo.save(target);
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        if (id == null) { loggingExceptionHandler(PICTURE_ID_CANNOT_BE_NULL); }
        else pictureRepo.deleteById(id);
    }

    /* Validation methods */
    @Transactional
    public boolean isValidPicture(Picture picture) {
        if (picture == null) {
            loggingExceptionHandler(METHOD_DOES_NOT_ALLOW_NULL_PICTURE_AS_PARAMETER);
            return false;
        }
        if (picture.getName() == null || picture.getName().isBlank()) {
            loggingExceptionHandler(Picture.PICTURE_NAME_CANNOT_BE_NULL);
            return false;
        }
        if (picture.getPath() == null || picture.getPath().isBlank()) {
            loggingExceptionHandler(Picture.PICTURE_PATH_CANNOT_BE_NULL);
            return false;
        }
        return true;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, Picture source) {
        if (targetId == null || source.getId() == null) {
            loggingExceptionHandler(PICTURE_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            loggingExceptionHandler(Picture.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidPicture(source)) {
            loggingExceptionHandler(PICTURE_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public Picture nullPictureLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}