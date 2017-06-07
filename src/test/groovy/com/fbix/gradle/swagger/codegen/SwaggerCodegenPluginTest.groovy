package com.fbix.gradle.swagger.codegen

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SwaggerCodegenPluginTest {

    def project

    @Before
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.fbix.gradle.swagger.codegen'

    }

    @Test
    void shouldHaveSwaggerCodegenTask() {
        Assert.assertTrue(project.tasks.swaggerCodegen instanceof SwaggerCodegenTask)
    }
}

