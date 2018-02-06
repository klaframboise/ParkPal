using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// A stop represents the physical location where a route stops. The Stop object has information on the 
    /// stop's location, its fare zone and a list of times at which the stop is serviced.
    /// </summary>
    class Stop
    {
        /// <summary>
        /// GTFS ID of the stop
        /// </summary>
        public string Id { get; }
        
        /// <summary>
        /// Name, or description, of the stop location
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// Latittude of the stop
        /// </summary>
        public double Lat { get; }

        /// <summary>
        /// Longitude of the stop
        /// </summary>
        public double Lon { get; }

        /// <summary>
        /// Fare zone in which the stop is located
        /// </summary>
        public int ZoneId { get; }

        /// <summary>
        /// Stop code. This code is different from the ID. Stop codes are usually posted on the stop
        /// signalisation. Stop codes can be shared amongst agency.
        /// </summary>
        public int Code { get; }

        /// <summary>
        /// List of times at which the stop is serviced.
        /// </summary>
        public List<StopTime> StopTimes { get; }

        public Stop(string id, string name, double lat, double lon, int zoneId, int code)
        {
            Id = id;
            Name = name;
            Lat = lat;
            Lon = lon;
            ZoneId = zoneId;
            Code = code;
            StopTimes = new List<StopTime>();
        }

        public void AddStopTime(StopTime stopTime)
        {
            StopTimes.Add(stopTime);
        }
    }
}
