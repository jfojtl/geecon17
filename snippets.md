# Simulate fail during POST

## JobController

```java
    // add between private fields and constructor
    @Value("app.node")
    private String node;
```


```java
        // add to accept method between two puIfAbsent calls
        // simulate fail of the node
        if ("node0".equals(node))
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
```

## POST request

```json
{
	"jobId" : "Job.b29a1f53-8b2c-4af7-b206-b01458f155c1",
	"owner" : "jfojtl",
	"jobTitle" : "Infinispan documentation",
	"numberOfPages": "15000",
	"sides" : "TWO_SIDED",
	"color" : "BW",
	"status" : "NEW"
}
```

## UUIDs - in case of offline

```
[ ] 2fda3f86-9e20-4673-8c09-b1a5ee3e449b
[ ] 3e60a867-838b-4e7c-afaa-99170e58d8a1
[ ] b29a1f53-8b2c-4af7-b206-b01458f155c1
[ ] 5f513ed3-470e-463f-8bb0-0465702346dd
[ ] 58256968-5534-4297-bf7c-44724d4cd2da
```
