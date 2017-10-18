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