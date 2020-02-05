## What is this?

* This android plugin retrieves several code metrics from android & kotlin modules and aggregate it to a json file.

## Preconditions

tbd.

## Installation

Add the following to your project level build.gradle

```groovy
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'de.nanogiants.gradle:android-metrics:0.0.4'
  }
}
```

Add the following to the module level build.gradle

```groovy
apply plugin: 'de.nanogiants.gradle.android-metrics'
```

## Usage

Apply this in your project build.gradle to access androidMetrics task.

### Gradle tasks
- androidMetrics
- publishToMavenLocal
- bintrayUpload -PbintrayUser=XXX -PbintrayApiKey=XXX

## License

Copyright (c) 2017 appcom interactive GmbH

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
