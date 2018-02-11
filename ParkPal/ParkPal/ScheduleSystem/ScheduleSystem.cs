using System;
using System.Collections.Generic;
using System.Text;
using GTFS;

namespace ParkPal.ScheduleSystem
{
    class ScheduleSystem
    {
        private static ScheduleSystem instance;
        private static GTFSFeed gtfs;

        private ScheduleSystem()
        {

        }

        public static ScheduleSystem Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ScheduleSystem();
                }
                return instance;
            }
        }

        public static GTFSFeed Gtfs
        {
            get
            {
                return gtfs;
            }
        }
    }
}
