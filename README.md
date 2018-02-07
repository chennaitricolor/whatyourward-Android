# what-your-ward
Application to find the ward details of your location

## Core Architecture

Dagger + MVP + RxJava + RxAndroid + RxPermissions

## Google Dependencies

Maps - Play services version 9.0.0

MapUtils - Default version 0.5+

Location - Play services version 9.0.0 with Fused Location Provider API

## Other Dependencies

Butter Knife - View dependency Injection

Timber - Logging

Glide - Image loading

Localify - For loading offline data from the text content.


## How to setup the project?

- Download the project or clone this repository.

- Open Android Studio and choose **Open an existing Android Studio Project** option

- Import the project and wait for the gradle sync to complete.


- To build this project, Create **secrets.properties** file in the main folder. Please find the screenshot attached for the steps below.

**Android Studio:** (If project root folder is shown)

- Choose **Project mode** from the side panel

<a href="https://imgur.com/HOfI3Lu"><img src="https://i.imgur.com/HOfI3Lu.png" title="source: imgur.com" /></a>

- Select the **Project -> Right Click -> New -> File**

<a href="https://imgur.com/dEZ1e0G"><img src="https://i.imgur.com/dEZ1e0G.png" title="source: imgur.com" /></a>

<a href="https://imgur.com/v8HAiqF"><img src="https://i.imgur.com/v8HAiqF.png" title="source: imgur.com" /></a>


**Finder Menu:** (Incase project is not synced in Android Studio)

- Open project in the finder.

- Create **secrets.properties** in the main folder (i.e below local.properties, app, build, gradle, README.md,etc.

<a href="https://imgur.com/zQh7ffW"><img src="https://i.imgur.com/zQh7ffW.png" title="source: imgur.com" /></a>

- Open **secrets.properties** and paste your google map key

<a href="https://imgur.com/J0TIrpT"><img src="https://i.imgur.com/J0TIrpT.png" title="source: imgur.com" /></a>


- Now rebuild the project (i.e **Build -> Rebuild**)

<a href="https://imgur.com/cJIKxTF"><img src="https://i.imgur.com/cJIKxTF.png" title="source: imgur.com" /></a>


Yeah! That's it, your project setup is done.

> **Note:** The project will not compile and build without **secrets.dependencies** file. For security concerns, this file is not committed to the repository

## How to build the debug apk in command line?

**cd** to the current project directory

**On Windows:**

Type ``gradlew assembleDebug`` and press **Enter**


**On Mac or Linux:**

Type ``./gradlew assembleDebug`` and press **Enter**

> **Note:** The apk will be inside the path ``WhatYourWard/app/build/outputs/apk/debug/``

## How to build the APK and install it on a running emulator or connected device in command line?

**cd** to the current project directory

**On Windows:**

Type ``gradlew installDebug`` and press **Enter**


**On Mac or Linux:**

Type ``./gradlew installDebug`` and press **Enter**



