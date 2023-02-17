package com.example.ipzlab3.controller;

import com.example.ipzlab3.logic.Dog;
import com.example.ipzlab3.repository.InterfaceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("dogs")
public class DogsController {
    private InterfaceDAO dao;
    @Autowired
    public void setDao(InterfaceDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public ApplicationContext applicationContext;

    public DogsController() {
    }

    @GetMapping("/all")
    public String getDogs(Model model){
        model.addAttribute("listOfDogs", this.dao.getDogs());
        return "dogs";
    }

    @GetMapping("/change")
    public String changeDog(){
        return "updateCreateDog";
    }

    @GetMapping("/create")
    public String createDog(Model model,
                            @RequestParam(value="cdogname") String dogName,
                            @RequestParam(value="cdogbreed") String dogBreed,
                            @RequestParam(value="cdogage") String dogAge){
        Dog newDog = new Dog(dogName, dogBreed, Integer.parseInt(dogAge));
        this.dao.addDog(newDog);
        model.addAttribute("listOfDogs", this.dao.getDogs());
        return "dogs";
    }
    @GetMapping("/update")
    public String updateCat(Model model, @RequestParam(value="udogid") String dogId,
                            @RequestParam(value="udogname") String dogName,
                            @RequestParam(value="udogbreed") String dogBreed,
                            @RequestParam(value="udogage") String dogAge){
        Dog newDog = new Dog(dogId, dogName, dogBreed, Integer.parseInt(dogAge));
        this.dao.updateDog(newDog);
        model.addAttribute("listOfDogs", this.dao.getDogs());
        return "dogs";
    }
    @GetMapping("/delete")
    public String deleteCat(Model model, @RequestParam(value="dogid") String dogId){
        System.out.println(dogId);
        Dog newDog = new Dog(dogId);
        this.dao.deleteDog(newDog);
        model.addAttribute("listOfDogs", this.dao.getDogs());
        return "dogs";
    }
}
