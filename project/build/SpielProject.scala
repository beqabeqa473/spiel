import sbt._
import Process._
import java.io.File

class SpielProject(info: ProjectInfo) extends AndroidProject(info) {

  override def androidPlatformName = "android-2.1"

  val rhino = "rhino" % "js" % "1.7R2" from "http://spielproject.info/attachments/download/3/js.jar"

  override def proguardOption = """
    -keep class info.spielproject.spiel.scripting.Scripter {
      public void registerHandlerFor(java.lang.String, java.lang.String, java.lang.Object);
    }
    -keep class info.spielproject.spiel.scripting.Scripter
  """

  val rhinoPath = Path.fromFile("lib_managed/scala_2.7.7/compile/js-1.7R2.jar")
  override def proguardExclude = super.proguardExclude+++rhinoPath

  override def dxTask = execTask {<x> {dxPath.absolutePath} --dex --output={classesDexPath.absolutePath} {classesMinJarPath.absolutePath} {rhinoPath.absolutePath}</x> }

  override def aaptPackageTask = task {
    super.aaptPackageTask.run
    FileUtilities.unzip(rhinoPath, outputDirectoryName, GlobFilter("org/mozilla/javascript/resources/*.properties"), log)
    for(p <- (outputDirectoryName/"org"**"*.properties").get) {
      (
        (new java.lang.ProcessBuilder(
          aaptPath.absolutePath,
          "add",
          resourcesApkPath.absolutePath,
          p.toString.replace("./"+outputDirectoryName+"/", "")
        ))
        directory outputDirectoryName.asFile
      ) ! log
    }
    FileUtilities.clean(outputDirectoryName/"org", log)
    None
  }
}
