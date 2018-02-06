using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// A trip represents a single of a route. As such, a trip is tied to a route and has information on
    /// the vehicules headsign text, the map shape, the trip direction and the stop times.
    /// </summary>
    class Trip
    {
        /// <summary>
        /// Route this trip is servicing
        /// </summary>
        public Route Route { get; }

        /// <summary>
        /// Service level at which this trip occurs
        /// </summary>
        public Service Service{ get; }

        /// <summary>
        /// GTFS ID of this trip
        /// </summary>
        public string Id { get; }

        /// <summary>
        /// Text displayed on the vehicle's headsign
        /// </summary>
        public string TripHeadsign { get; }

        /// <summary>
        /// Direction of the trip
        /// </summary>
        public int DirectionId { get; }

        /// <summary>
        /// Shape object depicting the path followed by the trip
        /// </summary>
        public Shape Shape { get; }

        /// <summary>
        /// Short name of the trip
        /// </summary>
        public int TripShortName { get; }

        /// <summary>
        /// List of stop and times serviced by this trip
        /// </summary>
        public List<StopTime> StopTimes { get; }

        public Trip(Route route, Service service, string id, string tripHeadsign, int directionId, Shape shape, int tripShortName)
        {
            Route = route;
            Service = service;
            Id = id;
            TripHeadsign = tripHeadsign;
            DirectionId = directionId;
            Shape = shape;
            TripShortName = tripShortName;
            StopTimes = new List<StopTime>();
        }

        public void AddStopTime(StopTime stopTime)
        {
            StopTimes.Add(stopTime);
        }
    }
}
