
def twitterUtil(mod: String) =
  "com.twitter" %% s"util-$mod" %  "6.43.0"

def finagle(mod: String) =
  "com.twitter" %% s"finagle-$mod" % "6.44.0"

def linkerd(mod: String) =
  "io.buoyant" %% s"linkerd-$mod" % "1.0.2"

val pathToHost =
  project.in(file("path-to-host")).
    settings(
      scalaVersion := "2.12.1",
      organization := "com.askattest",
      version := "0.1.0",
      name := "path-to-host",
      resolvers ++= Seq(
        "twitter" at "https://maven.twttr.com",
        "local-m2" at ("file:" + Path.userHome.absolutePath + "/.m2/repository")
      ),
      libraryDependencies ++=
        finagle("http") % "provided" ::
        twitterUtil("core") % "provided" ::
        linkerd("core") % "provided" ::
        linkerd("protocol-http") % "provided" ::
        Nil,
      assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
    )
