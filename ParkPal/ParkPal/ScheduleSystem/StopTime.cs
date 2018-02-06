using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// Represent a stop and time pair. The object contains a Stop object, the time at which the stop
    /// is serviced and information on the trip servicing the stop at that time.
    /// </summary>
    class StopTime
    {
        /// <summary>
        /// Trip servicing this object's stop at this object's time
        /// </summary>
        public Trip Trip { get; }

        /// <summary>
        /// Time at which the vehicule arrives at the stop
        /// </summary>
        public DateTime ArrivalTime { get; }

        /// <summary>
        /// Time at which the vehicule leaves the stop
        /// </summary>
        public DateTime DepartureTime { get; }

        /// <summary>
        /// Stop serviced by this object's Trip at this object's time
        /// </summary>
        public Stop Stop { get; }

        /// <summary>
        /// The stop number on this onject's trip
        /// </summary>
        public int StopSequence { get; }

        /// <summary>
        /// Specifies what type of pickup is allowed, values defined in GTFS
        /// </summary>
        public int PickupType { get; }

        /// <summary>
        /// Specifies what type of drop off is allowed, values defined in GTFS
        /// </summary>
        public int DropOffType { get; }

        /// <summary>
        /// Destined travelled on the trip since the start
        /// </summary>
        public int ShapeDistTraveled { get; }

        public StopTime(Trip trip, DateTime arrival_time, DateTime departure_time, Stop stop, int stopSequence, int pickupType, int dropOffType, int shapeDistTraveled)
        {
            Trip = trip;
            ArrivalTime = arrival_time;
            DepartureTime = departure_time;
            Stop = stop;
            StopSequence = stopSequence;
            PickupType = pickupType;
            DropOffType = dropOffType;
            ShapeDistTraveled = shapeDistTraveled;
        }
    }
}
