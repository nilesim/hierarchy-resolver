# Hierarchy Resolver
A Rest application to convert relations from links to trees. 

Application is dockerized and a Kotlin, Spring Boot - Maven - Hsqldb application.

To run:

```bash
docker build --tag=hierarchy-resolver:latest .
docker run --name resolver -p 8080:8080 -d hierarchy-resolver
```

There is a Postman collection file in the path for you to call api endpoints:

Hierarch Resolver.postman_collection.json
 
 or  
 
```bash
curl -H "Content-Type: application/json" -X POST -d '{
    "Pete": "Nick",
    "Barbara": "Nick",
    "Nick": "Sophie",
    "Sophie": "Jonas"
}
'  http://localhost:8080/hierarchies
```


```bash
curl http://localhost:8080/hierarchies/get-supervisors?name=Pete
```
