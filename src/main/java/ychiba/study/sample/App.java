package ychiba.study.sample;

/**
 * Azure blob storage v12 SDK
 */
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.specialized.*;

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
        String fileName = "v12test_" + java.util.UUID.randomUUID() + ".txt";
        File localFile = new File(localPath + fileName);

        // Write text to the file
        FileWriter writer = new FileWriter(localPath + fileName, true);
        writer.write("Hello, World!");
        writer.close();

        // Get a reference to a blob
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        logger.info("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the blob
        // uploadAsBlock(blobClient, localFile.getAbsolutePath());
        uploadAsAppend(blobClient, localFile.getAbsolutePath());

        logger.info("\nListing blobs...");

        // List the blob(s) in the container.
        for (BlobItem blobItem : containerClient.listBlobs()) {
            logger.info("\t" + blobItem.getName());
        }

        // Append to the blob
        appendFile(blobClient, localFile.getAbsolutePath());

        // Download the blob to a local file
        // Append the string "DOWNLOAD" before the .txt extension so that you can see both files.
        String downloadFileName = fileName.replace(".txt", ".DOWNLOAD.txt");
        File downloadedFile = new File(localPath + downloadFileName);

        logger.info("\nDownloading blob to\n\t " + localPath + downloadFileName);

        blobClient.downloadToFile(localPath + downloadFileName);

        // Clean up
        logger.info("\nPress the Enter key to begin clean up");
        System.console().readLine();

        logger.info("Deleting blob...");
        blobClient.delete();

        // logger.info("Deleting blob container...");
        // containerClient.delete();

        logger.info("Deleting the local source and downloaded files...");
        localFile.delete();
        downloadedFile.delete();

        logger.info("Done");

        // EOA
    }

    private static void uploadAsBlock(BlobClient blobClient, String absolutePath) {
        // Upload the blob
        blobClient.uploadFromFile(absolutePath);
    }

    private static void uploadAsAppend(BlobClient blobClient, String absolutePath) {
        // get client for appendable
        AppendBlobClient client = blobClient.getAppendBlobClient();
        client.create();

        appendFile(client, absolutePath);
    }

    private static void appendFile(BlobClient blobClient, String absolutePath) {
        // get client for appendable
        AppendBlobClient client = blobClient.getAppendBlobClient();

        appendFile(client, absolutePath);
    }

    private static void appendFile(AppendBlobClient client, String absolutePath) {
        try (final BlobOutputStream oStream = client.getBlobOutputStream();
             final BufferedInputStream biStream = new BufferedInputStream(new FileInputStream(absolutePath))) {
            int c;
            while ((c = biStream.read()) != -1) {
                oStream.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
