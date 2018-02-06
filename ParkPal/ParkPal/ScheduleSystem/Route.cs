using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// A route represents a set of trips serviced by a given agency.
    /// </summary>
    class Route
    {
        /// <summary>
        /// GTFS ID of the route
        /// </summary>
        public int Id { get; }

        /// <summary>
        /// Agency servicing the route
        /// </summary>
        public Agency Agency { get; }

        /// <summary>
        /// Short name of the route
        /// </summary>
        public string ShortName { get; }

        /// <summary>
        /// Long name of the route
        /// </summary>
        public string LongName { get; }

        /// <summary>
        /// Route type, as defined by GTFS
        /// </summary>
        public int Type { get; }

        /// <summary>
        /// List of trips serviced by this route
        /// </summary>
        public List<Trip> Trips { get; }

        public Route(int id, Agency agency, string shortName, string longName, int type)
        {
            Id = id;
            Agency = agency;
            ShortName = shortName;
            LongName = longName;
            Type = type;
            Trips = new List<Trip>();
        }

        public void AddTrip(Trip trip)
        {
            Trips.Add(trip);
        }
    }
}
