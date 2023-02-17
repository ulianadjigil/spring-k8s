package com.example.ipzlab3.repository;

import com.example.ipzlab3.logic.Cat;
import com.example.ipzlab3.logic.Dog;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Data {
    private List<Cat> cats;
    private List<Dog> dogs;

    private boolean catsdbCreated = false;
    public Data() {
        /*try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/CATS",
                    "user", "password"
            );
            try (PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO CATSLIST(name, breed, age)
        VALUES (?, ?, ?)
      """)) {
                statement.setString(1, "Bunny");
                statement.setString(2, "york");
                statement.setInt(3, 10);
                int rowsInserted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        this.cats = new ArrayList<>();
        this.dogs = new ArrayList<>();
        Cat cat1 = new Cat(1, "Персик", "шотландская", 8);
        Cat cat2 = new Cat(2, "Пушок", "мейн-кун", 4);
        Cat cat3 = new Cat(3, "Багира", "бомбейская", 5);
        Cat cat4 = new Cat(4, "Клеопатра", "сфинкс", 3);
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        cats.add(cat4);

    }

    public void createTableCats() {
        if(!catsdbCreated){
            final String url1 = "jdbc:postgresql://db:5432/";
            final String user = "postgres";
            final String password = "postgres";
            String sqlCreate = "CREATE DATABASE cats";
            try (Connection conn = DriverManager.getConnection(url1, user, password);
                 PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            String sqlTable = "CREATE TABLE cats ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR ( 50 ), " +
                    "breed VARCHAR ( 50 ), " +
                    "age INTEGER " +
                    ");";
            final String url2 = "jdbc:postgresql://db:5432/cats";
            try (Connection conn = DriverManager.getConnection(url2, user, password);
                 PreparedStatement ps = conn.prepareStatement(sqlTable)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            catsdbCreated = true;
        }
    }
    public List<Cat> getCats() {
        final String url = "jdbc:postgresql://db:5432/cats";
        final String sql = "SELECT * FROM cats";
        final String user = "postgres";
        final String password = "postgres";
        ArrayList<Cat> cats = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String breed = rs.getString("breed");
                Integer age = rs.getInt("age");
                Cat cat = new Cat(id, name, breed, age);
                cats.add(cat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public void createCat(Cat cat) {
        final String url = "jdbc:postgresql://db:5432/cats";
        final String sql = "INSERT INTO cats (id, name, breed, age) VALUES (?, ?, ?, ?)";
        final String user = "postgres";
        final String password = "postgres";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, cat.getId());
                ps.setString(2, cat.getName());
                ps.setString(3, cat.getBreed());
                ps.setInt(4, cat.getAge());
                ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateCat(Cat cat) {
        final String url = "jdbc:postgresql://db:5432/cats";
        final String sql = "UPDATE cats SET name = ?, breed = ?, age = ?  WHERE id::varchar = ?";
        final String user = "postgres";
        final String password = "postgres";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getName());
            ps.setString(2, cat.getBreed());
            ps.setInt(3, cat.getAge());
            ps.setString(4, cat.getId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCat(Cat cat) {
        final String url = "jdbc:postgresql://db:5432/cats";
        final String sql = "DELETE FROM cats WHERE id::varchar = ?";
        final String user = "postgres";
        final String password = "postgres";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Dog> getDogs() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://root:pass@mongo:27017"));
        DB database = mongoClient.getDB("Dogs");
        DBCollection collection = database.getCollection("DogsList");
        DBObject query = new BasicDBObject();
        this.dogs.clear();
        DBCursor cursor = collection.find(query);
        cursor.forEach(dog ->{
            Integer agedog = (Integer) dog.get("age");
            Dog dog1 = new Dog(((ObjectId) dog.get("_id")).toHexString(),
                    (String) dog.get("name"),(String) dog.get("breed"),
                    agedog);
            this.dogs.add(dog1);
        });
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
    public void createDog(Dog dog) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://root:pass@mongo:27017"));
        DB database = mongoClient.getDB("Dogs");
        DBCollection collection = database.getCollection("DogsList");
        DBObject newDog = new BasicDBObject();
        newDog.put("_id", new ObjectId());
        newDog.put("name", dog.getName());
        newDog.put("breed", dog.getBreed());
        newDog.put("age", dog.getAge());
        collection.insert(newDog);
    }
    public void updateDog(Dog dog) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://root:pass@mongo:27017"));
        DB database = mongoClient.getDB("Dogs");
        DBCollection collection = database.getCollection("DogsList");
        DBObject newDog = new BasicDBObject();
        ObjectId id = new ObjectId(dog.getId());
        newDog.put("name", dog.getName());
        newDog.put("breed", dog.getBreed());
        newDog.put("age", dog.getAge());
        DBObject query = new BasicDBObject("_id", id);
        collection.findAndModify(query, newDog);
    }

    public void deleteDog(Dog dog) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://root:pass@mongo:27017"));
        DB database = mongoClient.getDB("Dogs");
        DBCollection collection = database.getCollection("DogsList");
        ObjectId id = new ObjectId(dog.getId());
        DBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

}
