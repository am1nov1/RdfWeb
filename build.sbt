name := "RdfWeb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.apache.jena" % "jena-core" % "2.11.1"
)     

play.Project.playScalaSettings
