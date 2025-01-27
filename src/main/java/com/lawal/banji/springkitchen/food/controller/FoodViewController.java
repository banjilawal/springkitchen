package com.lawal.banji.springkitchen.food.controller;

import com.lawal.banji.springkitchen.food.model.Food;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/food")
public class FoodViewController {
    private final RestTemplate restTemplate;
    private final String apiBaseUrl;
    private static final int PAGE_SIZE = 10;

    public FoodViewController(RestTemplate restTemplate,
                              @Value("${api.base.url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    @GetMapping
    public String listFoods(@RequestParam(defaultValue = "1") int page, Model model) {
        try {
            ResponseEntity<List<Food>> response = restTemplate.exchange(
                apiBaseUrl + "/api/food",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Food>>() {}
            );

            List<Food> allFoods = response.getBody();

            // Simple pagination
            int totalFoods = allFoods != null ? allFoods.size() : 0;
            int totalPages = (int) Math.ceil((double) totalFoods / PAGE_SIZE);
            int start = (page - 1) * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, totalFoods);

            List<Food> pagedFoods = allFoods != null ?
                allFoods.subList(start, end) : Collections.emptyList();

            model.addAttribute("foods", pagedFoods);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);

        } catch (Exception e) {
            model.addAttribute("message", "Unable to load foods");
            model.addAttribute("messageType", "error");
        }

        return "food/list";
    }

    @GetMapping("/{id}")
    public String foodDetails(@PathVariable Long id, Model model) {
        try {
            Food food = restTemplate.getForObject(
                apiBaseUrl + "/api/food/{id}",
                Food.class,
                id
            );
            model.addAttribute("food", food);

        } catch (Exception e) {
            model.addAttribute("message", "Food not found");
            model.addAttribute("messageType", "error");
        }

        return "food/details";
    }

    @GetMapping("/search")
    public String searchFoods(@RequestParam String query, Model model) {
        try {
            ResponseEntity<List<Food>> response = restTemplate.exchange(
                apiBaseUrl + "/api/food/search?query={query}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Food>>() {},
                query
            );

            model.addAttribute("foods", response.getBody());
            model.addAttribute("searchQuery", query);

        } catch (Exception e) {
            model.addAttribute("message", "Search failed");
            model.addAttribute("messageType", "error");
        }

        return "food/list";
    }
}