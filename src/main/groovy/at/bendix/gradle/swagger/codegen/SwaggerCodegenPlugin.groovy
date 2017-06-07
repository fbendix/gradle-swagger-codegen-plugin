package at.bendix.gradle.swagger.codegen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class SwaggerCodegenPlugin implements Plugin<Project> {

    private static final String NAME = 'swaggerCodegen'
    private static final String CONFIG_NAME = 'swaggerCodegen'

    void apply(Project project) {
        project.extensions.create(NAME, SwaggerCodegenExtension, project)
        project.task('swaggerCodegen', type: SwaggerCodegenTask)

        project.with {
            repositories {
                jcenter()
            }

            configurations {
                swaggerCodegen { description = "swaggerCodegen dependencies"; transitive = true; }
            }

            dependencies {
                swaggerCodegen 'io.swagger:swagger-codegen:2.2.2'
                swaggerCodegen 'io.swagger:swagger-codegen-cli:2.2.2'
            }
        }
    }
}
