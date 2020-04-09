# FirestoreDocument-Android Quickstart

## Introduction

FirestoreDocument-Android it's an Android library for getting the size of a Cloud Firestore document. This it you will help you prevent your Firestore writes to fail. You'll always be able to check against the maximum of **1 MiB** (1,048,576 bytes) quota.

## Getting Started

To be able to use this library, you need to add [JitPack.io](https://jitpack.io/) repository into your build.gradle (Poject) file.

    allprojects {
        repositories {
            ...
            maven {
                url 'https://jitpack.io'
            }
        }
    }
