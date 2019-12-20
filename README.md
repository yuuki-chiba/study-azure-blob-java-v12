# study-azure-blob-java-v12

This repository is my learning record to use [Azure Blob Storage][blob_java_source] with Java.

## environment

- Java
  - [Azul Zulu JDK][jdk] with version 8
- Apache Maven
  - version 3.6.3
- Azure Storage Account
  - [Block Blob][understanding_blobs]

## learning steps

1. create test project with maven

    use the archetype called "maven-archetype-quickstart"

2. fix some project settings to create an executable jar file

3. add code based on [the code examples of Azure Blob storage client library for Java][quickstart]

    customize three points

    1. use `java.util.ResourceBundle` to retrieve the connection string
    2. use `org.slf4j.Logger` to log
    3. do not make a unique container every time

<!-- LINKS -->
[blob_java_source]: https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/storage/azure-storage-blob
[jdk]: https://www.azul.com/downloads/azure-only/zulu/?&version=java-8-lts&architecture=x86-64-bit&package=jdk
[understanding_blobs]: https://docs.microsoft.com/ja-jp/rest/api/storageservices/understanding-block-blobs--append-blobs--and-page-blobs#about-block-blobs
[quickstart]: https://docs.microsoft.com/ja-jp/azure/storage/blobs/storage-quickstart-blobs-java
