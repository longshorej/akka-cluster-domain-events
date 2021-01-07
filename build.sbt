name := "akka-cluster-membership-events"

val AkkaHttpVersion = "10.2.2"
val AkkaVersion = "2.6.10"
val AkkaManagementVersion = "1.0.8+69-0fade738+20210107-1609"

libraryDependencies ++= Seq(
  "com.typesafe.akka"             %% "akka-actor"                   % AkkaVersion,
  "com.typesafe.akka"             %% "akka-cluster"                 % AkkaVersion,
  "com.typesafe.akka"             %% "akka-http"                    % AkkaHttpVersion,
  "com.typesafe.akka"             %% "akka-http-spray-json"         % AkkaHttpVersion,
  "com.typesafe.akka"             %% "akka-stream"                  % AkkaVersion,
  "com.lightbend.akka.management" %% "akka-management"              % AkkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % AkkaManagementVersion
)

// transitive deps that need to pin Akka version

libraryDependencies ++= Seq(
  "com.typesafe.akka"             %% "akka-cluster-sharding"        % AkkaVersion,
  "com.typesafe.akka"             %% "akka-cluster-tools"           % AkkaVersion,
  "com.typesafe.akka"             %% "akka-distributed-data"        % AkkaVersion,
  "com.typesafe.akka"             %% "akka-persistence"             % AkkaVersion,
  "com.typesafe.akka"             %% "akka-protobuf"                % AkkaVersion
)

