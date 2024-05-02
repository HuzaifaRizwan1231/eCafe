package com.SDA.eCafe.controller;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.SDA.eCafe.model.Menu;
import com.SDA.eCafe.repository.MenuRepository;

import jakarta.transaction.Transactional;

@Controller
public class MenuController {
    MenuRepository menuRepository;

    @Autowired
    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/openMenu")
    public String openMenu() {
        return "Menu";
    }

    @GetMapping("/RetrieveTiming")
    public String RetrieveTiming(Model model) {

        try {
            model.addAttribute("menu", menuRepository.findAll());
            return "Alltimings";
        } catch (Exception e) {
            return "error";
        }
    }


    @GetMapping("/EditTime/{id}")
    public String EditTime(@PathVariable Long id, Model model) {
        try {
            Optional<Menu> menues = menuRepository.findById(id);
            model.addAttribute("menu", menues.get());
            return "EditMenu";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/Delete/{id}")
    public String Delete(@PathVariable Long id, Model model) {
        try{
            menuRepository.deleteById(id);
            return "redirect:/RetrieveTiming";
        } catch (Exception e) {
            return "error";
        }
    }



    @PostMapping("/saveMenuTiming")
    @Transactional
    public String saveMenu(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime breakFastTime) {
        try {
            Menu menu = new Menu();
            menu.setStartTime(startTime);
            menu.setEndTime(endTime);
            menu.setBreakFastTime(breakFastTime);
            menuRepository.save(menu);
            return "redirect:/RetrieveTiming";
        } catch (Exception e) {
            // Log the exception details here
            return "error";
        }
    }

}
