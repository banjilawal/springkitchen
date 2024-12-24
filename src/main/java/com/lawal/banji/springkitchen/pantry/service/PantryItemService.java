package com.lawal.banji.springkitchen.pantry.service;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.data.PantryItemRepo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class PantryItemService {

    @Resource
    private PantryItemRepo pantryItemRepo;

    @Autowired
    public PantryItemService(PantryItemRepo pantryItemRepo) {
        this.pantryItemRepo = pantryItemRepo;
    }

    public Long count() {
        return pantryItemRepo.count();
    }

    public PantryItem save (PantryItemDTO pantryItemDTO) {
        PantryItem pantryItem = new PantryItem();
        pantryItem.update(pantryItemDTO);
        System.out.println("pantryItemService created item " +pantryItem.toString());
        System.out.println(pantryItemRepo.count() + " items in pantry");
        return pantryItemRepo.save(pantryItem);
    }

    public PantryItem update (Long targetId, PantryItemDTO dto) {
        PantryItem target = pantryItemRepo.findById(targetId).orElse(null);
        if (target == null) { System.out.println("no pantry item with " + targetId +  "was found returning null"); return null; }
        System.out.println("pantryItemService updating item " +target.toString() + " with " +dto.toString());
        target.update(dto);
        return pantryItemRepo.save(target);
    }

    public Iterable<PantryItem> findAll() {
        return pantryItemRepo.findAll();
    }

    public PantryItem findById(Long id) {
        return pantryItemRepo.findById(id).orElse(null);
    }

    public PantryItem findByName(String name) {
        return pantryItemRepo.findByName(name);
    }

    public void deleteById(Long id) {
        pantryItemRepo.deleteById(id);
    }

    public Iterable<PantryItem> search (String string) {
        string = string.toLowerCase();
        Set<PantryItem> matches = new HashSet<>();
        for (PantryItem PantryItem: pantryItemRepo.findAll()) {
            if (PantryItem.getName().toLowerCase().contains(string))
                matches.add(PantryItem);
        }
        return matches;
    }

    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (PantryItem PantryItem: pantryItemRepo.findAll()) {
            stringBuilder.append(PantryItem.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
