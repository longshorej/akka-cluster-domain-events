# akka-cluster-membership-events

This is a demo Akka Cluster project to demonstrate how akka-management's cluster membership HTTP
routes work.

## Running

First, build the JAR:

```bash
sbt assembly
```

Next, start node one:

```bash
java -jar target/scala-2.12/akka-cluster-membership-events-assembly-0.1.0-SNAPSHOT.jar
```

In a new terminal, run the following:

```bash
curl http://192.168.1.23:8558/cluster/membership-events
```

You should see something like the following:

```
data:MemberUp(Member(akka://default@127.0.0.1:2551, Up))

data:
```

Next, start node two in yet another terminal:

```bash
java -Dakka.management.http.port=8559 -Dakka.remote.artery.canonical.port=2552 -jar target/scala-2.12/akka-cluster-membership-events-assembly-0.1.0-SNAPSHOT.jar
```

The cURL terminal should now show something similar to:

```bash
data:MemberJoined(Member(akka://default@127.0.0.1:2552, Joining))

data:MemberUp(Member(akka://default@127.0.0.1:2552, Up))
```

Finally, hit CTRL-C in node two's terminal and observe from cURL:

```bash
data:MemberLeft(Member(akka://default@127.0.0.1:2552, Leaving))

data:MemberExited(Member(akka://default@127.0.0.1:2552, Exiting))

data:MemberRemoved(Member(akka://default@127.0.0.1:2552, Removed),Exiting)
```
