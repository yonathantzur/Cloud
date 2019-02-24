package il.ac.colman.cs.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ScreenshotGenerator {
    final static String bucketName = "yonof";
    final static String s3Link = "https://s3.amazonaws.com/" + bucketName;
    final static int timeout = 15000;

    public static String takeScreenshot(String url) {
        try {
            String screenshotFilePath;
            String imgName = UUID.randomUUID().toString() + ".png";
            String screenshotCommand = "xvfb-run --error-file=screenshot/screenshot_error.txt --auto-servernum --server-num=1 wkhtmltoimage --format png --crop-w 1024 --crop-h 768 --quiet --quality 60 " + url + " screenshot/screenshot.png";
            Process process = Runtime.getRuntime().exec(screenshotCommand);

            Worker worker = new Worker(process);
            worker.start();

            try {
                worker.join(timeout);

                if (worker.exit == null) {
                    throw new TimeoutException();
                }

            } catch (InterruptedException ex) {
                worker.interrupt();
                Thread.currentThread().interrupt();
                throw ex;
            } finally {
                process.destroyForcibly();
            }

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

    private static class Worker extends Thread {
        private final Process process;
        private Integer exit;

        private Worker(Process process) {
            this.process = process;
        }

        public void run() {
            try {
                exit = process.waitFor();
            } catch (InterruptedException ignore) {
                return;
            }
        }
    }
}