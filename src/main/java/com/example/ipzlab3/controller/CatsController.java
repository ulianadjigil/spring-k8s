package com.example.ipzlab3.controller;

import com.example.ipzlab3.logic.Cat;
import com.example.ipzlab3.repository.InterfaceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("cats")
public class CatsController {
    private InterfaceDAO dao;
    @Autowired
    public void setDao(InterfaceDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public ApplicationContext applicationContext;

    public CatsController() {
    }

    @GetMapping("/all")
    public String getCats(Model model){
        dao.createTableCats();
        model.addAttribute("listOfCats", this.dao.getCats());
        return "cats";
    }

    @GetMapping("/change")
    public String changeCat(){
        return "updateCreateCat";
    }

    @GetMapping("/create")
    public String createCat(Model model, @RequestParam(value="ccatid") String catId,
                            @RequestParam(value="ccatname") String catName,
                            @RequestParam(value="ccatbreed") String catBreed,
                            @RequestParam(value="ccatage") String catAge){
        System.out.println(catId);
        System.out.println(catName);
        System.out.println(catBreed);
        System.out.println(catAge);
        Cat newCat = new Cat(Integer.parseInt(catId), catName, catBreed, Integer.parseInt(catAge));
        this.dao.addCat(newCat);
        model.addAttribute("listOfCats", this.dao.getCats());
        return "cats";
    }
    @GetMapping("/update")
    public String updateCat(Model model, @RequestParam(value="ucatid") String catId,
                            @RequestParam(value="ucatname") String catName,
                            @RequestParam(value="ucatbreed") String catBreed,
                            @RequestParam(value="ucatage") String catAge){
        System.out.println(catId);
        System.out.println(catName);
        System.out.println(catBreed);
        System.out.println(catAge);
        Cat newCat = new Cat(Integer.parseInt(catId), catName, catBreed, Integer.parseInt(catAge));
        this.dao.updateCat(newCat);
        model.addAttribute("listOfCats", this.dao.getCats());
        return "cats";
    }
    @GetMapping("/delete")
    public String deleteCat(Model model, @RequestParam(value="catid") String catId){
        System.out.println(catId);
        Cat newCat = new Cat(Integer.parseInt(catId));
        this.dao.deleteCat(newCat);
        model.addAttribute("listOfCats", this.dao.getCats());
        return "cats";
    }
}
