/*
 * Copyright 2018 the original author or authors.
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

package org.gradle.api.plugins.internal;

import org.gradle.api.NonNullApi;
import org.gradle.api.Project;
import org.gradle.api.plugins.ProjectReportsPluginConvention;
import org.gradle.api.plugins.ProjectReportsPluginExtension;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;

import java.io.File;
import java.util.Set;

import static org.gradle.api.reflect.TypeOf.typeOf;

@NonNullApi
public class DefaultProjectReportsPluginConvention extends ProjectReportsPluginConvention implements HasPublicType {
    private String projectReportDirName = "project";
    private final ProjectReportsPluginExtension extension;
    private final Project project;

    public DefaultProjectReportsPluginConvention(ProjectReportsPluginExtension extension, Project project) {
        this.extension = extension;
        this.project = project;
    }

    @Override
    public TypeOf<?> getPublicType() {
        return typeOf(ProjectReportsPluginConvention.class);
    }

    @Override
    public String getProjectReportDirName() {
        return extension.getProjectReportDirName();
    }

    @Override
    public void setProjectReportDirName(String projectReportDirName) {
        extension.setProjectReportDirName(projectReportDirName);
    }

    @Override
    public File getProjectReportDir() {
        return extension.getProjectReportDir();
    }

    @Override
    public Set<Project> getProjects() {
        return extension.getProjects();
    }
}
