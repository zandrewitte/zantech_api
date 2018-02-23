name := "Zantech_API"

version := "1.0"

scalaVersion := "2.12.3"

// Do not append Scala versions to the generated artifacts
crossPaths := false
// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

libraryDependencies ++= Seq(
  "com.typesafe.akka"                %%    "akka-http"                  % "10.0.5",
  "com.typesafe.akka"                %%    "akka-http-jackson"          % "10.0.5",
  "org.yaml"                          %    "snakeyaml"                  % "1.18",
  "org.hibernate"                     %    "hibernate-core"             % "5.2.10.Final",
  "org.pcollections"                  %    "pcollections"               % "2.1.2",
  "io.jsonwebtoken"                   %    "jjwt"                       % "0.7.0",
  "mysql"                             %    "mysql-connector-java"       % "6.0.6",
  "com.h2database"                    %    "h2"                         % "1.3.176",
  "javax.validation"                  %    "validation-api"             % "1.1.0.Final",
  "joda-time"                         %    "joda-time"                  % "2.0",
  "org.jadira.usertype"               %    "usertype.core"              % "6.0.0.GA",
  "de.mkammerer"                      %    "argon2-jvm"                 % "2.2",
  "javax.mail"                        %    "mail"                       % "1.4.7",
  "org.apache.velocity"               %    "velocity"                   % "1.7",
  "org.apache.velocity"               %    "velocity-tools"             % "2.0",
  "com.typesafe"                      %    "config"                     % "1.3.1",
  "org.slf4j"                         %    "log4j-over-slf4j"           % "1.7.7",
  "ch.qos.logback"                    %    "logback-classic"            % "1.2.3",
  "com.codepoetics"                   %    "protonpack"                 % "1.13"
)

mainClass in assembly := Some("za.co.zantech.Main")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case x if Assembly.isConfigFile(x) => MergeStrategy.concat
  //  case "reference.conf" | "application.conf" | "hibernate.cfg.xml" => MergeStrategy.concat
  case _ => MergeStrategy.first
}