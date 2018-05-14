**Note:This project is written in Kotlin**

##How to

To get a Git project into your build:

####Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
####Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.farley-fu:FarleyLibrary:v1.0.0'
	}

##Functional records:

- #####Add loading dialog and EventBus

show:

```Kotlin
var dialog = DialogUtils.showLoadingDialog(this@MainActivity)
```

hide:

```Kot
 DialogUtils.closeDialog(dialog)
```

- ##### Add MVP

This project uses MVP mode

- ##### Add LogUtils

  config:

  ```Kot
  LogUtils.canShow = false
  ```

  use

  ```Kot
  LogUtils.d("open")
  LogUtils.e("open")
  LogUtils.i("open")
  ```

  