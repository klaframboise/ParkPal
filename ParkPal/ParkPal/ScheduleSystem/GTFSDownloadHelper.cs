using System;
using System.Net;
using Xamarin.Forms;
using System.Reflection;
using System.IO;
using System.IO.Compression;
using System.Threading.Tasks;
using ParkPal.EmbeddedResources;
using System.Diagnostics;
using System.Collections.Generic;

namespace ParkPal.ScheduleSystem
{
    public class GTFSDownloadHelper
    {
        public static readonly string GTFS_DIR = Environment.GetFolderPath(Environment.SpecialFolder.Personal) + System.IO.Path.PathSeparator 
            + "ParkPal" + System.IO.Path.PathSeparator + "GTFS";
        
        /// <summary>
        /// Parses the urls and filenames of the GTFS Feeds that are supported by the app.
        /// </summary>
        /// <returns>A Dictionary&ltfilename, url&gt</returns>
        public Dictionary<string, string> GetUrls()
        {
            Dictionary<string, string> urls = new Dictionary<string, string>();

            /* Get urls from embedded list */
            var assembly = typeof(GTFSDownloadHelper).GetTypeInfo().Assembly;
            Stream stream = ResourceLoader.GetEmbeddedResourceStream(assembly, "GTFSFeedLinks.txt");
            using (var reader = new System.IO.StreamReader(stream))
            {
                var urlsBlock = reader.ReadToEnd(); //read file
                var urlsBlockNoCarriageReturn = urlsBlock.Replace("\r", "");    //remove carriage returns
                var lines = urlsBlockNoCarriageReturn.Split('\n');  //separate lines

                foreach (var line in lines)
                {
                    var records = line.Split(',');  //file format: filename,url
                    urls.Add(records[0], records[1]);   //add pair to dictionary
                }
            }

            return urls;
        }

        /// <summary>
        /// Downlads the feeds from the given urls.
        /// </summary>
        /// <param name="urls">A dictionary of format &ltfilenamen,url&gt</param>
        public async void DownloadFeeds(Dictionary<string, string> urls)
        {
            foreach(var url in urls)
            {
                await CreateDownloadTask(url.Key, url.Value);
            }

            foreach(var file in urls)
            {
                ExtractFeed(file.Key);
            }
        }

        /// <summary>
        /// Creates an async download task to download the given url to a file with the given filename.
        /// </summary>
        /// <param name="filename"></param>
        /// <param name="url"></param>
        /// <returns></returns>
        public static async Task<int> CreateDownloadTask(string filename, string url)
        {
            Debug.WriteLine("Creating download job for file {0}, url {1}", filename, url);
            int receivedBytes = 0;  //for progress tracking
            int totalBytes = 0;
            WebClient client = new WebClient();

            using (var stream = await client.OpenReadTaskAsync(url))    //get webclient stream
            using (var output = File.Create(GTFS_DIR + System.IO.Path.PathSeparator + filename.Split('.')[0] + System.IO.Path.PathSeparator + filename))  //create storage file
            {
                byte[] buffer = new byte[4096];
                totalBytes = Int32.Parse(client.ResponseHeaders[HttpResponseHeader.ContentLength]); //get size of file being downloaded

                while (true)
                {
                    int bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length);   //read bytes to buffer
                    if (bytesRead == 0) //break if finished reading
                    {
                        await Task.Yield();
                        break;
                    }

                    receivedBytes += bytesRead; //update progress
                    output.Write(buffer, 0, bytesRead); //write received bytes to file
                    Debug.WriteLine("Downloaded {0}/{1} ({2}% completed) bytes from {3}.", receivedBytes, totalBytes, (float)receivedBytes / totalBytes * 100.0f, url);
                }
            }
            return receivedBytes;
        }

        public static void ExtractFeed(string filename)
        {
            Debug.WriteLine("Extracting {0} to {1}", filename, GTFS_DIR + System.IO.Path.PathSeparator + filename.Split('.')[0]);
            ZipFile.ExtractToDirectory(GTFS_DIR + System.IO.Path.PathSeparator + filename.Split('.')[0] + System.IO.Path.PathSeparator + filename, GTFS_DIR + System.IO.Path.PathSeparator + filename.Split('.')[0]);
        }
    }
}
