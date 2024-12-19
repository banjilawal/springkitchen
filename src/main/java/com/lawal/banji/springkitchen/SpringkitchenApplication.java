package com.lawal.banji.springkitchen;

import com.lawal.banji.springkitchen.pantry.data.PantryItemGenerator;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.service.PantryItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringkitchenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringkitchenApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedPantryItems(PantryItemService pantryItemService) {
        return args -> {
            for (PantryItem pantryItem : PantryItemGenerator.pantryItems(15, 10L, 25L)) {
                pantryItemService.save(pantryItem);
            }
            for (PantryItem pantryItem : pantryItemService.findAll()) {
                System.out.println(pantryItem.toString());
            }
        };
    }

}
