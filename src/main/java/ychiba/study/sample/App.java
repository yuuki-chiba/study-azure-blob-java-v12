package ychiba.study.sample;

/**
 * Azure blob storage v12 SDK
 */
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * study to use azure blob with v12 SDK
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Azure Blob storage v12 - Java sample\n");

        // Retrieve the connection string for use with the application
        ResourceBundle rb = ResourceBundle.getBundle("application");
        String connectStr = rb.getString("CONNECT_STR");

        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();

        // Use a fixed name for the container
        String containerName = "v12test";

        // if the container is not exist, create the container and return a container client object
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient = blobServiceClient.createBlobContainer(containerName);
        }

        // Create a local file in the ./data/ directory for uploading and downloading
        String localPath = "./data/";
        String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
        File localFile = new File(localPath + fileName);

        // Write text to the file
        FileWriter writer = new FileWriter(localPath + fileName, true);
        writer.write("Hello, World!");
        writer.close();

        // Get a reference to a blob
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        logger.info("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the blob
        blobClient.uploadFromFile(localFile.getAbsolutePath());

        logger.info("\nListing blobs...");

        // List the blob(s) in the container.
        for (BlobItem blobItem : containerClient.listBlobs()) {
            logger.info("\t" + blobItem.getName());
        }

        // EOA
    }
}
