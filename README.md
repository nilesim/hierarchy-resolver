
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
