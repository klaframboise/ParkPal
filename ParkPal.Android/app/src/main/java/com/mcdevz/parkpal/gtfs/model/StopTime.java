/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.sql.Time;

// line 62 "../../../../../../../../ump/tmp356049/model.ump"
// line 200 "../../../../../../../../ump/tmp356049/model.ump"
public class StopTime
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StopTime Attributes
  private Time arrivalTime;
  private Time departureTime;
  private int sequence;
  private String headsign;
  private int pickupType;
  private int dropOffType;
  private float shadeDistTraveled;
  private int timepoint;

  //StopTime Associations
  private Trip trip;
  private Stop stop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StopTime(Time aArrivalTime, Time aDepartureTime, int aSequence, Trip aTrip, Stop aStop)
  {
    arrivalTime = aArrivalTime;
    departureTime = aDepartureTime;
    sequence = aSequence;
    headsign = null;
    resetPickupType();
    resetDropOffType();
    shadeDistTraveled = 0.0f;
    resetTimepoint();
    if (!setTrip(aTrip))
    {
      throw new RuntimeException("Unable to create StopTime due to aTrip");
    }
    if (!setStop(aStop))
    {
      throw new RuntimeException("Unable to create StopTime due to aStop");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setArrivalTime(Time aArrivalTime)
  {
    boolean wasSet = false;
    arrivalTime = aArrivalTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDepartureTime(Time aDepartureTime)
  {
    boolean wasSet = false;
    departureTime = aDepartureTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setSequence(int aSequence)
  {
    boolean wasSet = false;
    sequence = aSequence;
    wasSet = true;
    return wasSet;
  }

  public boolean setHeadsign(String aHeadsign)
  {
    boolean wasSet = false;
    headsign = aHeadsign;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setPickupType(int aPickupType)
  {
    boolean wasSet = false;
    pickupType = aPickupType;
    wasSet = true;
    return wasSet;
  }

  public boolean resetPickupType()
  {
    boolean wasReset = false;
    pickupType = getDefaultPickupType();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setDropOffType(int aDropOffType)
  {
    boolean wasSet = false;
    dropOffType = aDropOffType;
    wasSet = true;
    return wasSet;
  }

  public boolean resetDropOffType()
  {
    boolean wasReset = false;
    dropOffType = getDefaultDropOffType();
    wasReset = true;
    return wasReset;
  }

  public boolean setShadeDistTraveled(float aShadeDistTraveled)
  {
    boolean wasSet = false;
    shadeDistTraveled = aShadeDistTraveled;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setTimepoint(int aTimepoint)
  {
    boolean wasSet = false;
    timepoint = aTimepoint;
    wasSet = true;
    return wasSet;
  }

  public boolean resetTimepoint()
  {
    boolean wasReset = false;
    timepoint = getDefaultTimepoint();
    wasReset = true;
    return wasReset;
  }

  public Time getArrivalTime()
  {
    return arrivalTime;
  }

  public Time getDepartureTime()
  {
    return departureTime;
  }

  public int getSequence()
  {
    return sequence;
  }

  public String getHeadsign()
  {
    return headsign;
  }

  public int getPickupType()
  {
    return pickupType;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultPickupType()
  {
    return 0;
  }

  public int getDropOffType()
  {
    return dropOffType;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultDropOffType()
  {
    return 0;
  }

  public float getShadeDistTraveled()
  {
    return shadeDistTraveled;
  }

  public int getTimepoint()
  {
    return timepoint;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultTimepoint()
  {
    return 2;
  }
  /* Code from template association_GetOne */
  public Trip getTrip()
  {
    return trip;
  }
  /* Code from template association_GetOne */
  public Stop getStop()
  {
    return stop;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTrip(Trip aNewTrip)
  {
    boolean wasSet = false;
    if (aNewTrip != null)
    {
      trip = aNewTrip;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setStop(Stop aNewStop)
  {
    boolean wasSet = false;
    if (aNewStop != null)
    {
      stop = aNewStop;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    trip = null;
    stop = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "sequence" + ":" + getSequence()+ "," +
            "headsign" + ":" + getHeadsign()+ "," +
            "pickupType" + ":" + getPickupType()+ "," +
            "dropOffType" + ":" + getDropOffType()+ "," +
            "shadeDistTraveled" + ":" + getShadeDistTraveled()+ "," +
            "timepoint" + ":" + getTimepoint()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "arrivalTime" + "=" + (getArrivalTime() != null ? !getArrivalTime().equals(this)  ? getArrivalTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "departureTime" + "=" + (getDepartureTime() != null ? !getDepartureTime().equals(this)  ? getDepartureTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "trip = "+(getTrip()!=null?Integer.toHexString(System.identityHashCode(getTrip())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "stop = "+(getStop()!=null?Integer.toHexString(System.identityHashCode(getStop())):"null");
  }
}