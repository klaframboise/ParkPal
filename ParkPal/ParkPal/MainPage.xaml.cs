using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ParkPal.ScheduleSystem;
using Xamarin.Forms;

namespace ParkPal
{
	public partial class MainPage : ContentPage
	{
		public MainPage()
		{
			InitializeComponent();
            DownloadFeeds();
		}

        void DownloadFeeds()
        {
            var helper = new GTFSDownloadHelper();
            helper.DownloadFeeds(helper.GetUrls());
        }

	}
}
