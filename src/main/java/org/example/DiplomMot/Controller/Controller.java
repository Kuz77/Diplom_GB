package org.example.DiplomMot.Controller;

import lombok.AllArgsConstructor;
import org.example.DiplomMot.Service.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.example.DiplomMot.Model.Motorcycle;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {
    // В контроллер внедряем зависимость - сервис, а также добавляем
    // статические переменные выручки магазина и суммы покупки пользователя
    private final Service service;
    private static double revenue = 0.0;
    private static double purchase = 0.0;

    // По умолчанию отдаем страницу витрины магазина
    @GetMapping("/")
    public String getViewAsGuest(Model model) {
        model.addAttribute("motorcycles", service.getAllMotorcycles());
        return "showroom";
    }

    // Отдаем страницу авторизации
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Отдаем страницу пользователя
    @GetMapping("/user-profile")
    public String getViewAsCommonUser(Model model) {
        model.addAttribute("motorcycles", service.getAllMotorcycles());
        model.addAttribute("purchase", purchase);
        return "user-profile";
    }

    // Отдаем страницу администратора
    @GetMapping("/admin-profile")
    public String getViewAsAdmin(Model model) {
        model.addAttribute("motorcycles", service.getAllMotorcycles());
        model.addAttribute("revenue", revenue);
        return "admin-profile";
    }

    // Отдаем обновленную страницу администратора после добавления нового мотоцикла
    @PostMapping("/admin-profile")
    public String addMotorcycle(Motorcycle motorcycle, Model model) {
        service.createMotorcycle(motorcycle);
        model.addAttribute("motorcycles", service.getAllMotorcycles());
        return "redirect:/admin-profile";
    }

    // Отдаем обновленную страницу администратора после продажи мотоцикла
    @GetMapping("motorcycle-sell/{brand}")
    public String sellMotorcycle(@PathVariable("brand") String brand) {
        Motorcycle motorcycle = service.getMotorcycleByBrand(brand);
        revenue += Math.round(motorcycle.getPrice() * 100.0) / 100.0;
        service.sellMotorcycle(brand);
        return "redirect:/admin-profile";
    }

    // Отдаем обновленную страницу пользователя после покупки мотоцикла
    @GetMapping("motorcycle-purchase/{brand}")
    public String buyMotorcycle(@PathVariable("brand") String brand) {
        Motorcycle motorcycle = service.getMotorcycleByBrand(brand);
        purchase += Math.round(motorcycle.getPrice() * 100.0) / 100.0;
        revenue += Math.round(motorcycle.getPrice() * 100.0) / 100.0;
        service.sellMotorcycle(brand);
        return "redirect:/user-profile";
    }
}

