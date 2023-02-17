package com.example.ipzlab3.repository;

import com.example.ipzlab3.logic.Cat;
import com.example.ipzlab3.logic.Dog;

import java.util.List;

public interface InterfaceDAO {
    void addCat(Cat cat);
    void deleteCat(Cat cat);
    List<Cat> getCats();
    void updateCat(Cat cat);

    void addDog(Dog dog);
    void deleteDog(Dog dog);
    List<Dog> getDogs();
    void updateDog(Dog dog);
    void createTableCats();

}
