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
@RequestMapping("/stars")
public class StarsController {

    private final StarDAO starDAO;

    @Autowired
    public StarsController(StarDAO starDAO) {
        this.starDAO = starDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("stars", starDAO.index());
        return "stars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("star", starDAO.show(id));
        return "stars/show";
    }

    @GetMapping("/new")
    public String newStar(@ModelAttribute("star") Star star) {
        return "stars/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("star") @Valid Star star, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "stars/new";
        starDAO.save(star);
        return "redirect:/stars";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("star", starDAO.show(id));
        return "stars/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("star") @Valid Star star, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "stars/edit";
        starDAO.update(id, star);
        return "redirect:/stars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        starDAO.delete(id);
        return "redirect:/stars";
    }
}
