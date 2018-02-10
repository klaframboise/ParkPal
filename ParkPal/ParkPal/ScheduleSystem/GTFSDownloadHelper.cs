using System;
using System.Net;
using Xamarin.Forms;
using System.Reflection;
using System.IO;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace ParkPal.ScheduleSystem
{
    public class GTFSDownloadHelper
    {
        public List<string> GetUrls()
        {
            List<string> urls = new List<string>();

            /* Get urls from embedded list */
            var assembly = typeof(GTFSDownloadHelper).GetTypeInfo().Assembly;
            Stream stream = assembly.GetManifestResourceStream("ParkPal.SharedResources.GTFSFeedLinks.txt");
            using (var reader = new System.IO.StreamReader(stream))
            {
                urls.Add(reader.ReadLine());
            }

            return urls;
        }

        public async void DownloadFeeds(List<string> urls)
        {
            foreach(var url in urls)
            {
                await CreateDownloadTask(url);
            }
        }

        public static async Task<int> CreateDownloadTask(string url)
        {
            int receivedBytes = 0;
            int totalBytes = 0;
            WebClient client = new WebClient();

            using (var stream = await client.OpenReadTaskAsync(url))
            {
                byte[] buffer = new byte[4096];
                totalBytes = Int32.Parse(client.ResponseHeaders[HttpResponseHeader.ContentLength]);

                while (true)
                {
                    int bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length);
                    if (bytesRead == 0)
                    {
                        await Task.Yield();
                        break;
                    }

                    receivedBytes += bytesRead;
                }
            }

            return receivedBytes;
        }
    }
}
