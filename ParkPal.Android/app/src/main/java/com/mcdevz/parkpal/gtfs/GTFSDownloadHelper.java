package com.mcdevz.parkpal.gtfs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mcdevz.parkpal.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class downloads and extracts the GTFS schedule files.
 * @author Kevin Laframboise
 */
public class GTFSDownloadHelper {

    /**
     * Debug tag
     */
    private static final String TAG = "parkpal/GTFS";

    /**
     * Regex used in matching the fields containg resource id's for the GTFS download link in the
     * auto-generated com.mcdevz.parkpal.R
     */
    private static final String FIELD_REGEX = "^gtfs_[a-zA-Z0-9]+_zip$";

    /**
     * Context in which GTFSDownloadHelper is ran. Usually the main activity.
     */
    private Context context;

    /**
     * Dictionary containing the url-filename pairs.
     */
    private Hashtable<URL, String> downloadLinks;

    /**
     * Indicates whether or not to overwrite the previously downloaded GTFS.
     */
    private boolean overwrite;

    /**
     * Constructs a GTFSDownloadHelper and builds the download links dictionary.
     * @param context {@link #context}
     * @param overwrite {@link #overwrite}
     */
    public GTFSDownloadHelper(Context context, boolean overwrite) {
        this.context = context;
        this.overwrite = overwrite;
        makeDownloadLinks();
    }

    /**
     * Initiates the asynchronous download and decompression of the GTFS files.
     */
    public void downloadGTFS() {
        DownloadFilesTask task = new DownloadFilesTask();
        URL urls[] = downloadLinks.keySet().toArray(new URL[1]);

        task.execute(urls);
    }

    /**
     * Extracts all the GTFS download url and file names from the Android Resource (R) class.
     * Saves the result in {@link #downloadLinks} with the URL as the key and the filename as the
     * value.
     */
    private void makeDownloadLinks() {
        downloadLinks = new Hashtable<URL, String>();
        Log.d(TAG, "Making dictionary");
        Field[] resourceFields = R.string.class.getFields();
        Log.d(TAG, "Number of fields extracted: " + resourceFields.length);

        /* Extract links resource from resources */
        List<Field> gtfsLinksResources = new ArrayList<Field>();
        for(Field field : resourceFields) {
            Log.d(TAG, "Checking: " + field.getName());
            if(field.getName().matches(FIELD_REGEX)) {
                gtfsLinksResources.add(field);
            }
        }

        R.string r = new R.string();
        /* Extract link from resources */
        for(Field field : gtfsLinksResources) {
            try {
             downloadLinks.put(new URL(context.getResources().getString(field.getInt(r))), field.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* Log results */
        Log.d(TAG, "Found " + downloadLinks.size() + " download links.");
        Log.d(TAG, "Example value: " + downloadLinks.get(downloadLinks.keySet().iterator().next()).toString());

    }

    /**
     * Asynchronous task to decompress the GTFS zip files.
     * @author Kevin Laframboise
     */
    private class ExtractFilesTask extends AsyncTask<String, Integer, Integer> {

        /**
         * Extracts the files.
         * @param fileNames names of the files to extract
         * @return the number of entries extracted
         */
        @Override
        protected Integer doInBackground(String... fileNames) {
            int count = fileNames.length;
            int totalEntries = 0;
            for(int i = 0; i < count; i++) {
                try {
                    totalEntries += extractFeed(fileNames[i]);
                } catch (IOException e) {
                    publishProgress(ExtractionCallback.Progress.ERROR);
                    e.printStackTrace();
                }
                publishProgress(ExtractionCallback.Progress.FILE_EXTRACTED, (int)(i + 1.0 / count * 100.0));
            }
            return totalEntries;
        }

        /**
         * Prints progress to debug
         * @param progress update attributes
         */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            switch(progress[0]) {
                case ExtractionCallback.Progress.ERROR:
                    Log.d(TAG, "An error occurred while extracting the feeds");
                    break;
                case ExtractionCallback.Progress.ENTRY_EXTRACTED:
                    Log.d(TAG, "Entry successfully extracted. " + progress[1] + "% of entries processed in current zip file.");
                    break;
                case ExtractionCallback.Progress.FILE_EXTRACTED:
                    Log.d(TAG, "File successfully extracted. " + progress[1] + "% of files processed.");
                    break;
            }
        }

        /**
         * Prints success message to debug
         * @param result the number of entries extracted
         */
        @Override
        protected void onPostExecute(Integer result) {
            Log.d(TAG, "Extraction finished. " + result + " entries uncompressed");
        }

        /**
         * Used by {@link #doInBackground(String...)} to extract the files. The GTFS txt files are
         * decompressed to a directory named after the GTFS's agency.
         * @param zipFileName name of the zip file to decompress
         * @return number of decompressed entries
         * @throws IOException
         */
        private int extractFeed(String zipFileName) throws IOException {

            // Get file
            File file = new File(context.getFilesDir(), zipFileName);

            /* Create extraction directory */
            File outDir = new File(context.getFilesDir(), zipFileName.split("_")[1]);
            if(outDir.exists() && !overwrite) return 0;
            else if(outDir.exists() && overwrite) outDir.delete();
            outDir.mkdir();

            /* Get zip file properties */
            ZipFile zipFile = new ZipFile(file);
            int numOfEntries = zipFile.size();
            int entriesProcessed = 0;

            /* Process stream */
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zis.getNextEntry();
            while(zipEntry != null){
                String fileName = zipEntry.getName();
                File newFile = new File(outDir, fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
                // Update progress
                publishProgress(ExtractionCallback.Progress.ENTRY_EXTRACTED, (int)((entriesProcessed + 1.0 / numOfEntries) * 100.0));
                entriesProcessed++;
            }

            // Close streams
            zis.closeEntry();
            zis.close();

            return entriesProcessed;
        }
    }

    /**
     * Asynchronous task to download the GTFS zip files.
     * @author Kevin Laframboise
     */
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        /**
         * Downloads the zip files.
         * @param urls to download
         * @return total number of bytes downloaded
         */
        @Override
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                try {
                    totalSize += downloadFile(urls[i], downloadLinks.get(urls[i])).length();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                publishProgress(DownloadCallback.Progress.A_DOWNLOAD_FINISHED, (int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        /**
         * Prints progress updates to debug.
         * @param progress progress update attributes
         */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            switch(progress[0]) {
                case DownloadCallback.Progress.A_DOWNLOAD_FINISHED:
                    Log.d(TAG, "A download finished. " + progress[1] + "% of files downloaded");
                    break;
                case DownloadCallback.Progress.CONNECT_SUCCESS:
                    Log.d(TAG, "Connection successful");
                    break;
                case DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS:
                    Log.d(TAG, "Get input stream success");
                    break;
                case DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                    Log.d(TAG, "Downloading file... " + progress[1] + " bytes received.");
                    break;
                case DownloadCallback.Progress.PROCESS_INPUT_STREAM_SUCCESS:
                    Log.d(TAG, "Input stream fully processed. " + progress[1] + " bytes received.");
                    break;
                case DownloadCallback.Progress.ERROR:
                    Log.d(TAG, "An error occurred during a download");
            }
        }

        /**
         * Prints a success message to debug and initiates to extraction of the downloaded zip
         * files.
         * @param result total number of bytes downloaded
         */
        @Override
        protected void onPostExecute(Long result) {
            Log.d(TAG, "Download finished. " + result + " bytes downloaded");
            String[] fileNames = downloadLinks.values().toArray(new String[1]);
            Log.d(TAG, fileNames.length + " files to extract. First file: " + fileNames[0]);
            new ExtractFilesTask().execute(fileNames);
        }

        /**
         * Used by {@link #doInBackground(URL...)} to download the GTFS files.
         * @param url to download
         * @param filename of the file created on the device
         * @return the downloaded file
         * @throws IOException
         */
        private File downloadFile(URL url, String filename) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            File result = null;
            try {
                if(url.getProtocol().equals("https")) {
                    connection = (HttpsURLConnection) url.openConnection();
                }
                else if(url.getProtocol().equals("http")) {
                    connection = (HttpURLConnection) url.openConnection();
                }
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                connection.setReadTimeout(3000);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connection.setConnectTimeout(3000);
                // For this use case, set HTTP method to GET.
                connection.setRequestMethod("GET");
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                connection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                connection.connect();
                publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();
                publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS);
                if (stream != null) {
                    // Converts Stream to String with max length of 500.
                    result = readStream(stream, filename);
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    switch(connection.getResponseCode()) {
                        case 200: Log.d(TAG, "Successfully downloaded " + url.toString()); break;
                        case 302: Log.d(TAG, "HTTP 302 found. Redirects to: " + connection.getHeaderField("Location"));
                        default: Log.d(TAG, "Error downloading from: " + url.toString());
                    }

                    connection.disconnect();
                }
            }
            return result;
        }

        /**
         * Reads the download stream and writes it to a file.
         * @param stream to be read
         * @param filename of the file created on the device
         * @return the resulting file
         * @throws IOException
         */
        private File readStream(InputStream stream, String filename) throws IOException {
            File result = new File(context.getFilesDir(), filename);
            // Overwrite previous file
            if(result.exists() && !overwrite) return result;
            else if(result.exists() && overwrite) result.delete();
            result.createNewFile();

            /* Write input stream to file */
            FileOutputStream out = new FileOutputStream(result);
            byte[] buffer = new byte[1024];
            int bytesRead;
            int totalBytesRead = 0;
            while((bytesRead = stream.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, totalBytesRead);
            }

            publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_SUCCESS, totalBytesRead);
            out.close();
            return result;
        }
    }
}
