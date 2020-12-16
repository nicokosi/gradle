/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.tasks.bundling

import groovy.transform.CompileStatic
import org.gradle.api.provider.Property
import org.gradle.api.tasks.AbstractCopyTaskContractTest

abstract class AbstractArchiveTaskTest extends AbstractCopyTaskContractTest {
    abstract AbstractArchiveTask getArchiveTask()

    @Override
    AbstractArchiveTask getTask() {
        archiveTask
    }

    protected void checkConstructor() {
        assert archiveTask.archiveClassifier.get() == ''
    }

    protected void configure(AbstractArchiveTask archiveTask) {
        archiveTask.archiveBaseName.set('testbasename')
        archiveTask.archiveAppendix = 'testappendix'
        archiveTask.archiveVersion = '1.0'
        archiveTask.archiveClassifier = 'src'
        archiveTask.destinationDirectory = new File(temporaryFolder.testDirectory, 'destinationDir')
    }

    def "test execute()"() {
        given:
        archiveTask.from temporaryFolder.createFile('file.txt')

        when:
        execute(archiveTask)

        then:
        archiveTask.destinationDirectory.present
        archiveTask.archiveFile.present
        archiveTask.destinationDirectory.asFile.get().directory
        archiveTask.archiveFile.get().asFile.file
    }

    def "archiveName with empty extension"() {
        when:
        archiveTask.archiveExtension = null

        then:
        archiveTask.archiveFileName.get() == 'testbasename-testappendix-1.0-src'
    }

    def "archiveName with empty extension in provider"() {
        when:
        archiveTask.archiveExtension.set(project.provider { null })

        then:
        archiveTask.archiveFileName.get() == 'testbasename-testappendix-1.0-src'
    }

    def "archiveName with empty basename"() {
        when:
        archiveTask.archiveBaseName = null

        then:
        archiveTask.archiveFileName.get() == "testappendix-1.0-src.${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty basename and appendix"() {
        when:
        archiveTask.archiveBaseName = null
        archiveTask.archiveAppendix = null

        then:
        archiveTask.archiveFileName.get() == "1.0-src.${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty basename, appendix, and version" () {
        when:
        archiveTask.archiveBaseName = null
        archiveTask.archiveAppendix = null
        archiveTask.archiveVersion = null

        then:
        archiveTask.archiveFileName.get() == "src.${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty basename, appendix, version, and classifier"() {
        when:
        archiveTask.archiveBaseName = null
        archiveTask.archiveAppendix = null
        archiveTask.archiveVersion = null
        archiveTask.archiveClassifier = null

        then:
        archiveTask.archiveFileName.get() == ".${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty classifier"() {
        when:
        archiveTask.archiveClassifier = null

        then:
        archiveTask.archiveFileName.get() == "testbasename-testappendix-1.0.${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty appendix"() {
        when:
        archiveTask.archiveAppendix = null

        then:
        archiveTask.archiveFileName.get() == "testbasename-1.0-src.${archiveTask.archiveExtension.get()}".toString()
    }

    def "archiveName with empty version"() {
        when:
        archiveTask.archiveVersion = null

        then:
        archiveTask.archiveFileName.get() == "testbasename-testappendix-src.${archiveTask.archiveExtension.get()}".toString()
    }

    def "uses custom archive name when set"() {
        when:
        archiveTask.archiveFileName = 'somefile.out'

        then:
        archiveTask.archiveFileName.get() == 'somefile.out'
    }

    def "correct archive path"() {
        expect:
        archiveTask.archiveFile.get().getAsFile() == new File(archiveTask.destinationDirectory.getAsFile().get(), archiveTask.archiveFileName.get())
    }
}
