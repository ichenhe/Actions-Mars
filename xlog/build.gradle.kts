plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.vanniktech.maven.publish")
}

group = "me.chenhe"
version = if (System.getenv("GITHUB_ACTIONS").toBoolean())
    System.getenv("VERSION")?.toString() ?: "unspecified"
else
    findProperty("version")?.toString() ?: "unspecified"

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 16
        targetSdk = 30

        consumerProguardFiles("consumer-rules.pro")
    }
    
    sourceSets {
        getByName("main"){
            jniLibs.srcDir("src/main/libs")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

mavenPublish {
    releaseSigningEnabled = false
}

publishing {
    repositories {
        maven {
            name = "GithubPackages"

            val id: String?
            val pwd: String?
            if (System.getenv("GITHUB_ACTIONS").toBoolean()) {
                id = System.getenv("GITHUB_ACTOR")
                pwd = System.getenv("GITHUB_TOKEN")
                setUrl("https://maven.pkg.github.com/" + System.getenv("GITHUB_REPOSITORY"))
            } else {
                id = findProperty("github_username")?.toString()
                pwd = findProperty("github_token")?.toString()
                setUrl(findProperty("maven_url")?.toString() ?: "")
            }
            credentials {
                username = id
                password = pwd
            }
        }
    }
}
