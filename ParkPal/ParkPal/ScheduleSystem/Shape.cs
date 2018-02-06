using System;
using System.Collections.Generic;
using System.Text;

namespace ParkPal.ScheduleSystem
{
    class Shape
    {
        public string id { get; set; }
        public double shapePtLat { get; set; }
        public double shapePtLon { get; set; }
        public int shapePtSequence { get; set; }
        public double shapeDistTraveled { get; set; }

        public Shape(string id, double shapePtLat, double shapePtLon, int shapePtSequence, double shapeDistTraveled)
        {
            this.id = id;
            this.shapePtLat = shapePtLat;
            this.shapePtLon = shapePtLon;
            this.shapePtSequence = shapePtSequence;
            this.shapeDistTraveled = shapeDistTraveled;
        }
    }
}
