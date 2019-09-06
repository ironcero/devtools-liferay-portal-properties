# devtools-liferay-portal-properties
Gradle Plugin for Portal.properties on Liferay 7.0+ liferay workspace

## Summary
This gradle plugin let you manage your properties files on your liferay workspace (version 7.0 and 7.1).

Blade tool initializes this kind of workpace with one folder named _configs_. There are some folder into _configs_ folder:
* _common_
* _dev_
* _local_
* _prod_
* _uat_

It's very common that you need to keep different values for the same properties depends on your environment. This plugin try to help you to manage this settup: copying all properties files from one common folder to each environment folder and replacing all properties found in filters file to the correct value.

## How to use
First you will need the plugin jar file. You could download latest version from https://repo1.maven.org/maven2/com/github/ironcero/portal-properties (or search on Maven Central) or download source code from this github and to compile it.
If you download jar file you will need move this to correct path in your local repository (gradle coordenates are _com.github.ironcero:portal-properties:2.0.0_). Else if you download source code and compile it you will need to execute _install_ maven task to install jar file on correct path in your local repository.

After jar file is fetched you will need to set up your liferay workspace. You will need to create two newely folder. You can create these folder in path you want but we recommend created into _common_ folder (in _configs_ folder).

Now you will need to set up this plugin in your build.gradle file. You will need add these line to build.gradle file:
```gradle
buildscript {
    dependencies {
        classpath group: "com.github.ironcero", name: "portal-properties", version: "2.0.0"
    }

    repositories {
		mavenLocal()
		maven {
			url "https://repository-cdn.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "devtools-liferay-portal-properties"

buildproperties {
    descFolderPath = 'configs'
    originFolderPath = 'configs/common/origin'
    keysFolderPath = 'configs/common/keys'
}

build.finalizedBy(buildproperties)
```
In this example we're going to use _configs/common/origin_ folder to keep original properties file with pattern, and _configs/common/keys_ folder to keep different values for properties.
In details:
* Dependencies: Gradle coordenates of DevTools Liferay Portal Properties is _devtools.liferay:portal-properties:1.0.0_.
* Repositories: you will need mavenLocal repository because you've moved plugin jar file to your maven local repository.
* Apply plugin: DevTools Liferay Portal Properties plugin id is _devtools-liferay-portal-properties_.
* BuildProperties: In this section we will put all configuration parameters. In 1.0.0 release we have:
  * _descFolderPath_: Path where properties file will be copied and properties will be replaced. Default value: _configs_
  * _originFolderPath_: Location of original properties file (with ${} filter params). Default value: _configs/common/origin_
  * _keysFolderPath_: Location of filter properties file. Default value: _configs/common/keys_
* build.finaluzedBy: With this command we can execute this plugin on _build_ stage and not only on _buildproperties_.

It's time to add your properties files. 

In the example we've created 4 filter file on _keysFolderPath_ folder (_configs/common/keys_):
* _dev.properties_
* _local.properties_
* _prod.properties_
* _uat.properties_
The content of these files are very similar (local.properties):
```local.properties
test1=Local
```
File name (without _.properties_ extension) must be equals to environment folder on _descFolderPath_ folder.

In the example we've created only one properties file on _originFolderPath_ folder (_configs/common/origin_). But we'ld put more properties files and all of then would be copied and replaced.
_portal-ext.properties_ on _configs/common/origin_:
```portal-ext.properties
testKey=testValue
test1Key=${test1}
```

Now you are be able to generated your portal-ext.properties filtered by environment with _buildproperties_ gradle task, or standar _build_ gradle task.
```bash
gradle buildproperties
```
```bash
gradle build
```

This is a common log of process:
```log
:buildproperties
Build properties task...configs
Settings: 
destination folder path: configs
origin folder path: configs/common/origin
keys folder path: configs/common/keys
Parsing dev environment...
Copying C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\common\origin\portal-ext.properties to C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\dev
WARNING: Property not found in file portal-ext.properties on dev folder (${test1})
WARNING: Property not found in file portal-ext.properties on dev folder (${test2})
Parsing local environment...
Copying C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\common\origin\portal-ext.properties to C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\local
Parsing prod environment...
Copying C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\common\origin\portal-ext.properties to C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\prod
WARNING: Property not found in file portal-ext.properties on prod folder (${test1})
Parsing uat environment...
Copying C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\common\origin\portal-ext.properties to C:\dev\workspaces\devtools\liferay\portal-properties-test\liferay-workspace\configs\uat
WARNING: Property not found in file portal-ext.properties on uat folder (${test1})

BUILD SUCCESSFUL

Total time: 0.275 secs
```

You will see _WARNING_ log when you have some properties on your original properties files and you haven't filter for these properties on your filter properties files.
You could review Liferay Test project in https://github.com/ironcero/devtools-liferay-portal-properties/tree/master/testProject/liferay-workspace

## Releases

### 1.0.0
Base release

### 1.0.1
* Add correct configuration default values

### 1.1.0
* Junit and coverage included

### 1.1.1
* Integration test included

### 2.0.0
* Release on Maven Central (through oss.sonatype.org)

### 2.1.0
* Parsing folder capabilities added