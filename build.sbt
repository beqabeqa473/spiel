import AndroidKeys._

AndroidProject.androidSettings

AndroidMarketPublish.settings

site.settings

site.sphinxSupport()

name := "Spiel"

version := "3.0.0-SNAPSHOT"

scalaVersion := "2.10.1"

scalacOptions ++= Seq("-deprecation")

platformName in Android := "android-17"

keystorePath in Android := Path.userHome / ".keystore" / "spiel.keystore"

keyalias in Android := "spiel"

PasswordManager.settings

cachePasswords in Android := true

resolvers ++= Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies := Seq(
  "org.scala-lang" % "scala-actors" % "2.10.1",
  "rhino" % "js" % "1.7R2" from "http://android-scripting.googlecode.com/hg/rhino/rhino1_7R2.jar",
  "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.10.0",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "ch.acra" % "acra" % "4.4.0"
)

proguardOption in Android := """
  -keep class scala.collection.SeqLike { public protected *; }
  -keep class info.spielproject.spiel.** { *; }
  -keep class org.mozilla.javascript.* { *; }
  -keep class org.mozilla.javascript.ast.* { *; }
  -keep class org.mozilla.javascript.json.* { *; }
  -keep class org.mozilla.javascript.jdk15.* { *; }
  -keep class org.mozilla.javascript.regexp.* { *; }
  -keep class org.mozilla.javascript.resources.* { *; }
"""
