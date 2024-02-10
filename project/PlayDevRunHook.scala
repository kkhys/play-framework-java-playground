import java.io.PrintWriter
import play.sbt.PlayRunHook
import sbt.*

import scala.io.Source
import scala.language.reflectiveCalls
import scala.sys.process.Process

object PlayDevRunHook {

  object FrontendCommands {
    val install = "pnpm install"
    val viteDev = "pnpm vite:dev"
    val tailwindDev = "pnpm tailwind:dev"
  }

  object Shell {
    /**
     * Execute a command in the shell
     *
     * @param cmd  command to execute
     * @param cwd  working directory
     * @param envs environment variables
     * @return exit code
     */
    def execute(cmd: String, cwd: File, envs: (String, String)*): Int = {
      Process(cmd, cwd, envs *).!
    }

    /**
     * Invoke a command in the shell
     *
     * @param cmd  command to execute
     * @param cwd  working directory
     * @param envs environment variables
     * @return process
     */
    def invoke(cmd: String, cwd: File, envs: (String, String)*): Process = {
      Process(cmd, cwd, envs *).run
    }
  }

  /**
   * Create a PlayRunHook to watch frontend changes
   *
   * @param base base directory
   * @return PlayRunHook
   */
  def apply(base: File): PlayRunHook = {

    val frontendBase = base / "frontend"
    val packageJsonPath = frontendBase / "package.json"

    val frontEndTarget = base / "target" / "frontend"
    val packageJsonHashPath = frontEndTarget / "package.json.hash"

    object FrontendBuildProcess extends PlayRunHook {
      var processes: List[Process] = Nil

      /**
       * Invoked before the Play application starts
       */
      override def beforeStarted(): Unit = {
        def using[A <: { def close(): Unit }, B](resource: A)(file: A => B): B =
          try {
            file(resource)
          } finally {
            resource.close()
          }

        println("Hook to Play Framework dev run -- beforeStarted")

        val currPackageJsonHash = using(Source.fromFile(packageJsonPath)) { source =>
          source.getLines().mkString.hashCode().toString
        }

        val oldPackageJsonHash = getStoredPackageJsonHash

        if (!oldPackageJsonHash.contains(currPackageJsonHash)) {
          println(s"Found new/changed package.json. Run '${FrontendCommands.install}'...")

          Shell.execute(FrontendCommands.install, frontendBase)

          updateStoredPackageJsonHash(currPackageJsonHash)
        }
      }

      /**
       * Invoked after the Play application has been started
       */
      override def afterStarted(): Unit = {
        println(s"> Watching frontend changes in $frontendBase")
        processes = List(
          Shell.invoke(FrontendCommands.viteDev, frontendBase),
          Shell.invoke(FrontendCommands.tailwindDev, frontendBase)
        )
      }

      /**
       * Invoked after the Play application has been stopped
       */
      override def afterStopped(): Unit = {
        processes.foreach(_.destroy())
        processes = Nil
      }

      /**
       * Get the stored package.json hash
       *
       * @return hash
       */
      private def getStoredPackageJsonHash: Option[String] = {
        def using[A <: { def close(): Unit }, B](resource: A)(file: A => B): B =
          try {
            file(resource)
          } finally {
            resource.close()
          }

        if (packageJsonHashPath.exists()) {
          using(Source.fromFile(packageJsonHashPath)) { source =>
            Some(source.getLines().mkString)
          }
        } else {
          None
        }
      }

      /**
       * Update the stored package.json hash
       *
       * @param hash hash
       */
      private def updateStoredPackageJsonHash(hash: String): Unit = {
        val dir = frontEndTarget

        if (!dir.exists) dir.mkdirs

        val pw = new PrintWriter(packageJsonHashPath)

        try {
          pw.write(hash)
        } finally {
          pw.close()
        }
      }
    }

    FrontendBuildProcess
  }
}
