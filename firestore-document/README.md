# FirestoreDocument-Android Quickstart

## Introduction

FirestoreDocument-Android it's an Android library for **getting the size of a Cloud Firestore document**. This it you will help you prevent your Firestore writes to fail. You'll always be able to check against the maximum of **1 MiB** (1,048,576 bytes) quota.

## Getting Started

To be able to use this library, you need to add [JitPack.io](https://jitpack.io/) repository into your build.gradle (Project) file.

    allprojects {
        repositories {
            ...
            maven {
                url 'https://jitpack.io'
            }
        }
    }

And the latest dependency into your build.gradle (app) file.

    implementation 'com.github.alexmamo:FirestoreDocument-Android:0.1.5'
    
In your Android project create a new instance of `FirestoreDocument` class:

    FirestoreDocument firestoreDocument = FirestoreDocument.getInstance();
    
Inside the class, there is a `getSize()` method which will return the size of document when you pass an `DocumentSnapshot` object.

    int documentSize = firestoreDocument.getSize(documentSnapshot);
    
Using the following reference:

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    DocumentReference myTaskIdRef = rootRef.collection("users").document("jeff").collection("tasks").document("my_task_id");
    
As is specified in the offcial documentation regarding [the storage size of Firestore document](https://firebase.google.com/docs/firestore/storage-size), we can create a document with the following structure:

https://i.ibb.co/J5qJH8K/1.png
    
Here is the entre code getting the size of a document:

