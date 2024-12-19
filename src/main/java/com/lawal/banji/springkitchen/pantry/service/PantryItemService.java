package com.lawal.banji.springkitchen.pantry.service;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.data.PantryItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class PantryItemService {

    @Autowired
    private PantryItemRepo pantryItemRepo;

    public PantryItemService() {
    }

    @Autowired
    public PantryItemService(PantryItemRepo pantryItemRepo) {
        this.pantryItemRepo = pantryItemRepo;
    }

    public PantryItemRepo getPantryItemRepo() {
        return pantryItemRepo;
    }

    @Autowired
    public void setPantryItemRepo(PantryItemRepo pantryItemRepo) {
        this.pantryItemRepo = pantryItemRepo;
    }

    public Long count() {
        return pantryItemRepo.count();
    }

    public PantryItem save (PantryItem pantryItem) {
        if (pantryItemRepo.findByName(pantryItem.getName()) != null) return null;
        return pantryItemRepo.save(pantryItem);
    }

    public PantryItem update (Long targetId, PantryItemDTO dto) {
        PantryItem target = findById(targetId);
        if (target == null) return null;
        target.setQuantityInStock(dto.getQuantityInStock());
        target.setReorderLevel(dto.getReorderLevel());
        return pantryItemRepo.save(target);
    }

    public Iterable<PantryItem> findAll() {
        return pantryItemRepo.findAll();
    }

    public PantryItem findById(Long id) {
        return pantryItemRepo.findById(id).orElse(null);
    }

    public PantryItem findByName(String name) {
        for (PantryItem PantryItem: pantryItemRepo.findAll()) {
            if (PantryItem.getName().equalsIgnoreCase(name))
                return PantryItem;
        }
        return null;
    }

    public Iterable<PantryItem> search (String name) {
        Set<PantryItem> matches = new HashSet<>();
        for (PantryItem PantryItem: pantryItemRepo.findAll()) {
            if (PantryItem.getName().toLowerCase().contains(name.toLowerCase()))
                matches.add(PantryItem);
        }
        return matches;
    }

    public void deleteById(Long id) {
        pantryItemRepo.deleteById(id);
    }

    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (PantryItem PantryItem: pantryItemRepo.findAll()) {
            stringBuilder.append(PantryItem.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
