using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    interface IScheduleSystemController
    {
        /// <summary>
        /// Returns the agency with given ID. Null if no agency with that ID.
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Agency object with that ID or null if no agency with that ID.</returns>
        Agency GetAgencyById(string id);
        
        /// <summary>
        /// Returns list of agencies whose name contains the search string.
        /// </summary>
        /// <param name="name"></param>
        /// <returns>List of agencies corresponding to search term, or null of no agency matches.</returns>
        List<Agency> SearchAgencyByName(string name);

        /// <summary>
        /// Returns the service level for the given agency on the given date.
        /// </summary>
        /// <param name="agency"></param>
        /// <param name="date"></param>
        /// <returns></returns>
        Service GetServiceByDate(Agency agency, DateTime date);

        /// <summary>
        /// Returns a list of routes serviced by the given agency.
        /// </summary>
        /// <param name="agency"></param>
        /// <returns></returns>
        List<Route> GetRoutesByAgency(Agency agency);

        /// <summary>
        /// Returns the list of all routes matching the id in the system.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        List<Route> SearchRoutesById(string id);

        /// <summary>
        /// Returns the list of routes matching the given id in the given agency.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="agency"></param>
        /// <returns></returns>
        List<Route> SearchRoutesByIdInAgency(string id, Agency agency);

        /// <summary>
        /// Returns the list of trips on the given route, at the given service level.
        /// Note that the route and service must be from the same agency.
        /// </summary>
        /// <param name="route"></param>
        /// <param name="service"></param>
        /// <returns></returns>
        List<Trip> GetTripsByRoute(Route route, Service service);

        /// <summary>
        /// Returns the list of stop times at the given stop at the given service level.
        /// </summary>
        /// <param name="stop"></param>
        /// <param name="service"></param>
        /// <returns></returns>
        List<StopTime> GetStopTimesByStop(Stop stop, Service service);

        /// <summary>
        /// Returns all the stop times of a route at the given stop and service level.
        /// </summary>
        /// <param name="route"></param>
        /// <param name="stop"></param>
        /// <param name="service"></param>
        /// <returns></returns>
        List<StopTime> GetStopTimesByStopAndRoute(Route route, Stop stop, Service service);

        /// <summary>
        /// Returns the list of stop times attached to the given trip.
        /// </summary>
        /// <param name="trip"></param>
        /// <returns></returns>
        List<StopTime> GetStopTimeByTrip(Trip trip);

        /// <summary>
        /// Returns a shape to be displayed on a map depicting the path followed by the trip.
        /// </summary>
        /// <param name="trip"></param>
        /// <returns></returns>
        Shape GetShapeByTrip(Trip trip);
    }

}
