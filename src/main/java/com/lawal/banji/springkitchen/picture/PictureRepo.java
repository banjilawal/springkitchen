package com.lawal.banji.springkitchen.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Set;

public interface PictureRepo extends JpaRepository<Picture, Long> {

    Picture findById(long id);
    Picture findByNameIgnoreCase(String name);
    Picture findByPathIgnoreCase(String path);

    @Query("SELECT r FROM Picture r WHERE lower(r.name) LIKE lower(:string) OR lower(r.path) LIKE lower(:string)")
    Set<Picture> search(@Param("string") String string);
}
