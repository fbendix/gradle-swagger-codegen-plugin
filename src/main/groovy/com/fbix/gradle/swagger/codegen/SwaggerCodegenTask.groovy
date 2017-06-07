package com.fbix.gradle.swagger.codegen

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SwaggerCodegenTask extends DefaultTask {

    SwaggerCodegenTask() {
        group = "codegen"
        description = 'Run swagger code generation'
    }

    @TaskAction
    public release() {
        SwaggerCodegenExtension swaggerCodegenExt = project.swaggerCodegen

        def buildArgs = { SwaggerSpec swaggerSpec ->

            Map params = [
                    'language': swaggerSpec.language,
                    'inputFile': swaggerSpec.inputFile,
                    'configFile': swaggerSpec.configFile,
                    'outputDir': swaggerSpec.outputDir
                    // 'components':  ['models', 'apis'],
            ]

            def options = []
            options << 'generate'
            if (params?.language) {
                options << '-l' << params.language
            }
            if (params?.inputFile) {
                options << '-i' << params.inputFile.path
            }
            if (params?.outputDir) {
                options << '-o' << params.outputDir.path
            }
            if (params?.library) {
                options << '--library' << params.library
            }
            if (params?.configFile) {
                options << '-c' << params.configFile.path
            }
            if (params?.templateDir) {
                options << '-t' << params.templateDir.path
            }
            if (params?.additionalProperties) {
                options << '--additional-properties' << params.additionalProperties.collect { key, value ->
                    "$key=$value"
                }.join(',')
            }
            options
        }


        swaggerCodegenExt.getSwaggerSpecs().each { SwaggerSpec swaggerSpec ->
            List arguments = buildArgs(swaggerSpec)
            project.javaexec {
                main = 'io.swagger.codegen.SwaggerCodegen'
                classpath += project.configurations.swaggerCodegen
                args arguments
            }
            project.copy {
                from swaggerSpec.copyFromDir
                into swaggerSpec.copyIntoDir
                include "**/*.java"
            }
        }
    }
}

