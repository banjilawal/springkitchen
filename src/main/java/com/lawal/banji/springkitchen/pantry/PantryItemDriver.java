package com.lawal.banji.springkitchen.pantry;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.data.PantryItemGenerator;

import java.util.Set;

public class PantryItemDriver {

    public static void main(String[] args) {
        Set<PantryItemDTO> pantryItemDTOs = PantryItemGenerator.pantryItemDTOs(20, 10L, 25L);
        for (PantryItemDTO pantryItemDTO : pantryItemDTOs) {
            System.out.println(pantryItemDTO.toString());
        }

    }
}
