using System;
using System.Net;
using Xamarin.Forms;
using System.Reflection;
using System.IO;
using System.Threading.Tasks;
using ParkPal.EmbeddedResources;

namespace ParkPal.ScheduleSystem
{
    public class GTFSDownloadHelper
    {

        public string[] GetUrls()
        {
            string[] urls;

            /* Get urls from embedded list */
            var assembly = typeof(GTFSDownloadHelper).GetTypeInfo().Assembly;
            Stream stream = ResourceLoader.GetEmbeddedResourceStream(assembly, "GTFSFeedLinks.txt");
            using (var reader = new System.IO.StreamReader(stream))
            {
                var urlsBlock = reader.ReadToEnd();
                var urlsBlockNoCarriageReturn = urlsBlock.Replace("\r", "");
                urls = urlsBlockNoCarriageReturn.Split('\n');
                
            }

            return urls;
        }

        public async void DownloadFeeds(string[] urls)
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
