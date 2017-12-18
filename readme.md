# Sample code for talk at Geecon Prague 2017

Presentation at [speakerdeck](https://speakerdeck.com/jfojtl/). Client code at `src/main/js`.

## How to run

First build the project by running e.g. `./gradlew clean build assemble` and then start server cluster:

```
java -jar ./build/libs/geecon-<version>.jar -Dapp.node=node0 -Dserver.port=8000 -Djgroups.tcp.port=7800
java -jar ./build/libs/geecon-<version>.jar -Dapp.node=node1 -Dserver.port=8001 -Djgroups.tcp.port=7801
java -jar ./build/libs/geecon-<version>.jar -Dapp.node=node2 -Dserver.port=8002 -Djgroups.tcp.port=7802
```

Or you can use IntelliJ IDEA's spring boot run configurations
```
configuration name: node0
main class: com.fojtl.geecon.server.Server
VM options: -Dapp.node=node0 -Dserver.port=8000 -Djgroups.tcp.port=7800

configuration name: node1
main class: com.fojtl.geecon.server.Server
VM options: -Dapp.node=node1 -Dserver.port=8001 -Djgroups.tcp.port=7801

configuration name: node2
main class: com.fojtl.geecon.server.Server
VM options: -Dapp.node=node2 -Dserver.port=8002 -Djgroups.tcp.port=7802
```

Then go to `src/main/js` and run `npm i && npm start` or use IntelliJ IDEA's npm configuration with command `start`.

## Simulations

To simulate fail during write, insert snippets from `./snippets.md` to `com.fojtl.geecon.server.web.JobController`

## Architecture

```
                           +----------------+--------------+
                           |                |              |
                       +--->    JobService  |   ispn       |
                       |   |                |              |
                       |   +----------------+-----+--------+
                       |                          |
+-------------------+  |   +----------------+-----+--------+
|                   |  |   |                |              |
|   Client          +------>    JobService  |   ispn       |
|                   |  |   |                |              |
+-------------------+  |   +----------------+-----+--------+
                       |                          |
                       |   +----------------+-----+--------+
                       |   |                |              |
                       +--->    JobService  |   ispn       |
                           |                |              |
                           +----------------+--------------+
```

## Use cases

1. Client can send jobs to JobService
2. Client can obtain list of jobs from JobService
3. Client can print a job

## JobService REST API

### POST /{userId}/jobs

Creates a new job

  + Request
    + body `com.fojtl.geecon.server.web.models.JobTicket`
  + Response
    + status 201 on success
    + status 500 if anything goes wrong
     
### GET /{userId}/jobs

Loads all jobs for user defined by `userId`

  + Response
    + status 200 on success
    + body `List<com.fojtl.geecon.server.web.models.JobTicket>`

### PUT /{userId}/jobs/{jobId}

Prints job - as a result, print state will change

  + Request
    + example `{ "printed": "true", "op-id": "Print.0ef1f20f-28ec-4834-8561-b0bb800399c8" }`
  + Response
    + status 200 on success
    + status 500 if anything goes wrong
    
### GET /{userId}/jobs/{jobId}

Loads single job of user `userId` and job ID `jobId`

  + Response
    + status 200 on success
    + body `com.fojtl.geecon.server.web.models.JobTicket`
