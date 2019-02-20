package il.ac.colman.cs.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;
import java.net.URL;
import java.util.UUID;

public class ScreenshotGenerator {
    final static String bucketName = "yonof";
    final static String s3Link = "https://s3.amazonaws.com/" + bucketName;

    public static String takeScreenshot(String url) {
        try {
            String screenshotFilePath;
            String imgName = UUID.randomUUID().toString() + ".png";
            String screenshotCommand = "xvfb-run --error-file=screenshot/screenshot_error.txt --auto-servernum --server-num=1 wkhtmltoimage --format png --crop-w 1024 --crop-h 768 --quiet --quality 60 " + url + " screenshot/screenshot.png";
            Runtime.getRuntime().exec(screenshotCommand).waitFor();

            // Configure our client
            AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
            // Upload a file
            client.putObject(bucketName, imgName, new File("./screenshot/screenshot.png"));
            // Get the object URL
            URL s3url = client.getUrl(bucketName, imgName);
            screenshotFilePath = s3url.getPath();

            return (s3Link + screenshotFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
