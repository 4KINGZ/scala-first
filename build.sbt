name := "scala-first"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies +="com.datastax.cassandra" % "cassandra-driver-core" % "3.3.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.11"
  ,"com.typesafe.akka" %% "akka-stream" % "2.5.26" // or whatever the latest version is

)
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11"
resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  "org.mindrot"  % "jbcrypt"   % "0.3m"
)