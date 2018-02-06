using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    /// <summary>
    /// An Agency represents a real life transit companies. The object contains information on the Agency
    /// such as its name, its GTFS id, fare urls, a list of routes and service levels.
    /// </summary>
    class Agency
    {
        /// <summary>
        /// GTFS id of the agency
        /// </summary>
        public string Id { get; }
        
        /// <summary>
        /// Name of the agency
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// URL to the website of the agency
        /// </summary>
        public string Url { get; }

        /// <summary>
        /// Timezone in which the agency operates
        /// </summary>
        public string Timezone { get; }

        /// <summary>
        /// Main languages the agency deals in
        /// </summary>
        public string Lang { get; }

        /// <summary>
        /// URL to the agency's fares page
        /// </summary>
        public string FareUrl { get; }

        /// <summary>
        /// List of routes serviced by the agency
        /// </summary>
        public List<Route> Routes { get; }

        /// <summary>
        /// List of service levels provided by the agency
        /// </summary>
        public List<Service> Services { get; }

        /// <summary>
        /// List of exceptional service levels provided by the agency
        /// </summary>
        public List<ExceptionalService> ExceptionServices { get; }

        public Agency(string id, string name, string url, string timezone, string lang, string fareUrl)
        {
            Id = id;
            Name = name;
            Url = url;
            Timezone = timezone;
            Lang = lang;
            FareUrl = fareUrl;
        }

        public void AddRoute(Route route)
        {
            Routes.Add(route);
        }

        public void AddService(Service service)
        {
            Services.Add(service);
        }

        public void AddExceptionalService(ExceptionalService exceptionService)
        {
            ExceptionServices.Add(exceptionService);
        }

    }
}
