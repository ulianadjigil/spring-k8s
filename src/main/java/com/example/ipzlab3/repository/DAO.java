package com.example.ipzlab3.repository;

import com.example.ipzlab3.logic.Cat;
import com.example.ipzlab3.logic.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DAO implements InterfaceDAO{
    @Autowired
    public Data data;

    public DAO() { }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public void addCat(Cat cat) {
        this.data.createCat(cat);
    }

    @Override
    public void deleteCat(Cat cat) {
        this.data.deleteCat(cat);
    }

    @Override
    public List<Cat> getCats() {
        return this.data.getCats();
    }

    public void createTableCats(){
        this.data.createTableCats();
    }

    @Override
    public void updateCat(Cat cat) {
        this.data.updateCat(cat);
    }

    @Override
    public void addDog(Dog dog) {
        this.data.createDog(dog);
    }

    @Override
    public void deleteDog(Dog dog) {
        this.data.deleteDog(dog);
    }

    @Override
    public List<Dog> getDogs() {
        return this.data.getDogs();
    }

    @Override
    public void updateDog(Dog dog) {
        this.data.updateDog(dog);
    }
}
