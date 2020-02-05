/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import de.nanogiants.gradle.extensions.MetricsExtension
import de.nanogiants.gradle.extensions.UnifyExtension
import de.nanogiants.gradle.extensions.unify
import de.nanogiants.gradle.tasks.MetricsTask
import de.nanogiants.gradle.tasks.UnifiedTestTask
import de.nanogiants.gradle.utils.runCmd
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File

class AndroidMetricsPlugin : Plugin<ProjectInternal> {

  override fun apply(target: ProjectInternal) {
    with(target) {
      tasks.register(MetricsTask.NAME, MetricsTask::class.java)
      tasks.register(UnifiedTestTask.NAME, UnifiedTestTask::class.java) {
        it.group = "verification"
        it.description = "Unify test task"
      }
      extensions.create(MetricsExtension.NAME, MetricsExtension::class.java)

      subprojects { child ->
        applyUnifiedPlugin(child)
        addShowCoverageTask(child)
      }
    }
  }

  internal fun applyUnifiedPlugin(childProject: Project) {
    childProject.extensions.create(UnifyExtension.NAME, UnifyExtension::class.java)
    childProject.tasks.register(UnifiedTestTask.NAME, UnifiedTestTask::class.java) {
      it.group = "verification"
      it.description = "Unify test task"
    }
    childProject.afterEvaluate { after ->
      val unifyTask = after.tasks.named(UnifiedTestTask.NAME)
      val dependsOnName = with(after.plugins) {
        when {
          hasPlugin(AppPlugin::class.java) -> after.unify().taskName
          hasPlugin(LibraryPlugin::class.java) -> after.unify().taskName
          else -> "test"
        }
      }
      unifyTask.configure {
        if (dependsOnName.isNotEmpty()) {
          it.dependsOn(dependsOnName)
        }
      }
    }
  }

  internal fun addShowCoverageTask(child: Project) {
    child.tasks.withType(JacocoReport::class.java).whenTaskAdded {
      child.tasks.register("coverage${it.name.capitalize()}") { registerTask ->
        registerTask.group = "verification"
        registerTask.dependsOn(it)

        val taskPath = with(child.plugins) {
          when {
            hasPlugin(AppPlugin::class.java) || hasPlugin(LibraryPlugin::class.java) -> it.name
            hasPlugin(LibraryPlugin::class.java) -> "test"
            else -> "test"
          }
        }

        registerTask.doLast {
          val path = File(child.buildDir, "/reports/jacoco/$taskPath/html/index.html")
          if (path.exists()) {
            println("JacocoReport: file://${path.absolutePath}")
            "open ${path.absolutePath}".runCmd()
          } else {
            println("cannot open ${path.absolutePath}")
          }
        }
      }
    }
  }
}