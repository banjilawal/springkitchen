package com.lawal.banji.springkitchen;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/kitchen", "/", ""})
public class SystemController {

    public void setModelAttributes(Model model) { model.addAttribute("dashboard", "systemDashboard"); }

    @RequestMapping
    public String getKitchenHome () {
        return "redirect:/kitchen/food";
    }

    @RequestMapping("/notfound")
    public String notFoundPage(Model model) {
        model.addAttribute("error", "The requested resource was not found.");
        return "notFoundView";
    }
}