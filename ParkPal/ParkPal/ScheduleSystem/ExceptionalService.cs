using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// An exceptional service level defines a calendar date during which an agency doesn't 
    /// provide the same service level it usually does on a given day of the week
    /// </summary>
    class ExceptionalService
    {
        /// <summary>
        /// The service level provided on the exception date
        /// </summary>
        public Service Service { get; }
        
        /// <summary>
        /// The date on which the exception occurs
        /// </summary>
        public DateTime Date { get; }

        /// <summary>
        /// Exception type as defined by GTFS
        /// </summary>
        public int ExceptionType { get; }

        public ExceptionalService(Service service, DateTime date, int exceptionType)
        {
            Service = service;
            Date = date;
            ExceptionType = exceptionType;
        }
    }
}
