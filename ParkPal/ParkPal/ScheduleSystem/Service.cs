using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// A Service represents a service level. The service level is defined as list of days of the week
    /// on which the service level is offered, calendar start and end dates.
    /// </summary>
    public class Service
    {
        /// <summary>
        /// GTFS ID of the service level
        /// </summary>
        public int Id { get; }

        /// <summary>
        /// List of days of the week on which the service level is offered.
        /// Represented as an bool array where i = 0 is Sunday, i = 1 is Monday, etc.
        /// </summary>
        public bool[] Days { get; }

        /// <summary>
        /// Calendar date at which this service levels starts to apply.
        /// </summary>
        public DateTime StartDate { get; }

        /// <summary>
        /// Calendar date at which this service levels ends.
        /// </summary>
        public DateTime EndDate { get; }

        public Service(int id, bool[] days, DateTime startDate, DateTime endDate)
        {
            Id = id;
            Days = days;
            StartDate = startDate;
            EndDate = endDate;
        }
    }
}
