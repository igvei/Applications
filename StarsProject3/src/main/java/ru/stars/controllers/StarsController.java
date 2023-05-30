package ru.stars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.stars.dao.StarDAO;
import ru.stars.models.Star;

import javax.validation.Valid;

@Controller
@RequestMapping("/stars")  //Автоматически к адресу добавляется /stars
public class StarsController {

    private final StarDAO starDAO;

    @Autowired
    public StarsController(StarDAO starDAO) {
        this.starDAO = starDAO;
    }

    @GetMapping()                       // При запросе /stars
    public String index(Model model) {
        model.addAttribute("stars", starDAO.indexStars());
        return "stars/index";
    }

    @GetMapping("/{id}")                //Сервер получает id звезды и выводит представление с параметрами звезды
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("star", starDAO.show(id));
        return "stars/show";
    }

    @GetMapping("/new")             // При запросе /stars/new выводится представление для создании новой звезды
    public String newStar(@ModelAttribute("star") Star star) {
        return "stars/new";
    }

    @PostMapping()                  // Посылает на сервер данные с формы
    public String create(@ModelAttribute("star") @Valid Star star, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "stars/new";
        starDAO.save(star);
        return "redirect:/stars";
    }

    @GetMapping("/{id}/edit")       //Получаем представление для редактирования данных о звезде
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("star", starDAO.show(id));
        return "stars/edit";
    }

    @PatchMapping("/{id}")          //Получаем id звезды, для которой обновим данные
    public String update(@ModelAttribute("star") @Valid Star star, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "stars/edit";
        starDAO.update(id, star);
        return "redirect:/stars";
    }

    @DeleteMapping("/{id}")         //Получаем id звезды, которую удалим из базы данных
    public String delete(@PathVariable("id") int id) {
        starDAO.delete(id);
        return "redirect:/stars";
    }
}
