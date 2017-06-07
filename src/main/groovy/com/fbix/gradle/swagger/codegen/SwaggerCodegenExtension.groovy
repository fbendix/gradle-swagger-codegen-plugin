package com.fbix.gradle.swagger.codegen

import org.gradle.api.Project

class SwaggerCodegenExtension {

    List<SwaggerSpec> swaggerSpecs = []

    Project project

    SwaggerCodegenExtension(Project project) {
        this.project = project
    }

    def swaggerSpec(Closure configureClosure) {
        SwaggerSpec swaggerSpec = new SwaggerSpec()
        configureClosure.resolveStrategy = Closure.DELEGATE_FIRST
        configureClosure.delegate = swaggerSpec
        configureClosure()

        def sourceSetName = "generated-${swaggerSpec.outputKey}"
        swaggerSpec.outputDir = project.file("$project.buildDir/generated/${swaggerSpec.outputKey}")
        swaggerSpec.copyFromDir = project.file(swaggerSpec.outputDir.path+"/src/main/java")
        swaggerSpec.copyIntoDir = project.file("$project.projectDir/src/${sourceSetName}/java")

        swaggerSpec.copyIntoDir.mkdirs()
        project.with {
            sourceSets {
                "$sourceSetName" {
                    java {
                        srcDirs = [swaggerSpec.copyIntoDir.path]
                        compileClasspath += project.sourceSets.main.compileClasspath
                        runtimeClasspath += project.sourceSets.main.runtimeClasspath
                    }
                }
            }
        }

        swaggerSpecs << swaggerSpec
    }

    List<SwaggerSpec> getSwaggerSpecs() {
        return swaggerSpecs
    }
}
