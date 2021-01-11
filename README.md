# akka-cluster-domain-events

This is a demo Akka Cluster project to demonstrate how akka-management's cluster domain events HTTP
route works.

## Running

First, build the JAR:

```bash
sbt clean assembly
```

Next, start node one:

```bash
java -jar target/scala-2.12/akka-cluster-domain-events-assembly-0.1.0-SNAPSHOT.jar
```

In a new terminal, run the following:

```bash
curl http://192.168.1.23:8558/cluster/domain-events
```

You should see something like the following:

```
data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Up","uniqueAddress":{"address":"akka://default@127.0.0.1:2551","longUid":2489134320706880032}},"type":"MemberUp"}
event:MemberUp

data:{"address":"akka://default@127.0.0.1:2551","type":"LeaderChanged"}
event:LeaderChanged

data:{"address":"akka://default@127.0.0.1:2551","role":"dc-default","type":"LeaderChanged"}
event:LeaderChanged
```

Next, start node two in yet another terminal:

```bash
java -Dakka.management.http.port=8559 -Dakka.remote.artery.canonical.port=2552 -jar target/scala-2.12/akka-cluster-domain-events-assembly-0.1.0-SNAPSHOT.jar
```

The cURL terminal should now show something similar to:

```
data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Joining","uniqueAddress":{"address":"akka://default@127.0.0.1:2552","longUid":4278877901175315812}},"type":"MemberJoined"}
event:MemberJoined

data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Up","uniqueAddress":{"address":"akka://default@127.0.0.1:2552","longUid":4278877901175315812}},"type":"MemberUp"}
event:MemberUp

```

Next, hit CTRL-C in node two's terminal and observe from cURL:

```
data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Leaving","uniqueAddress":{"address":"akka://default@127.0.0.1:2552","longUid":4278877901175315812}},"type":"MemberLeft"}
event:MemberLeft

data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Exiting","uniqueAddress":{"address":"akka://default@127.0.0.1:2552","longUid":4278877901175315812}},"type":"MemberExited"}
event:MemberExited

data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Removed","uniqueAddress":{"address":"akka://default@127.0.0.1:2552","longUid":4278877901175315812}},"previousStatus":"Exiting","type":"MemberRemoved"}
event:MemberRemoved
```

In your cURL terminal, press CTRL-C, and execute the following (it uses the `type` parameter to only receive `MemberJoined` and `LeaderChanged` events):

```bash
curl 'http://192.168.1.23:8558/cluster/domain-events?type=MemberUp&type=LeaderChanged'
```

Observe the following:

```
data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Up","uniqueAddress":{"address":"akka://default@127.0.0.1:2551","longUid":4594533045826000111}},"type":"MemberUp"}
event:MemberUp

data:{"address":"akka://default@127.0.0.1:2551","type":"LeaderChanged"}
event:LeaderChanged
```

In your cURL terminal, again press CTRL-C, and this time relaunch the original cURL command:

```bash
curl http://192.168.1.23:8558/cluster/domain-events
```

Lastly, hit CTRL-C in node one's terminal and observe from cURL:

```bash
data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Leaving","uniqueAddress":{"address":"akka://default@127.0.0.1:2551","longUid":2489134320706880032}},"type":"MemberLeft"}
event:MemberLeft

data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Exiting","uniqueAddress":{"address":"akka://default@127.0.0.1:2551","longUid":2489134320706880032}},"type":"MemberExited"}
event:MemberExited

data:{"type":"ClusterShuttingDown"}
event:ClusterShuttingDown

data:{"member":{"dataCenter":"default","roles":["dc-default"],"status":"Removed","uniqueAddress":{"address":"akka://default@127.0.0.1:2551","longUid":2489134320706880032}},"previousStatus":"Exiting","type":"MemberRemoved"}
event:MemberRemoved

data:{"type":"LeaderChanged"}
event:LeaderChanged

data:{"role":"dc-default","type":"LeaderChanged"}
event:LeaderChanged

curl: (56) Recv failure: Connection reset by peer
```