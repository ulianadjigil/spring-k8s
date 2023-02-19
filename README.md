# spring-k8s

## Microservices
We have three microservices: backend, MongoDB and PostgreSQL.
There is a document with information about Dogs in MongoDB. Also there is a database with table Cats in PostgreSQL.

To run the project:
```bash
kubectl apply ./kuber
```
Backend is running on port 8082. 
We can see lists of animals and perform CRUD operations.
Full resource address is: 
```bash
http://<backend-service-ip>:8082/dogs/all
http://<backend-service-ip>:8082/cats/all
```
