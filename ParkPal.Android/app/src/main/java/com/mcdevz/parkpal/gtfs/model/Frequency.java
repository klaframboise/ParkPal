/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.sql.Time;

// line 151 "../../../../../../../../ump/tmp356049/model.ump"
// line 233 "../../../../../../../../ump/tmp356049/model.ump"
public class Frequency
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Frequency Attributes
  private Time startTime;
  private Time endTime;
  private int headwaySecs;
  private boolean exactTimes;

  //Frequency Associations
  private Trip trip;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Frequency(Time aStartTime, Time aEndTime, int aHeadwaySecs, Trip aTrip)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    headwaySecs = aHeadwaySecs;
    resetExactTimes();
    if (!setTrip(aTrip))
    {
      throw new RuntimeException("Unable to create Frequency due to aTrip");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setHeadwaySecs(int aHeadwaySecs)
  {
    boolean wasSet = false;
    headwaySecs = aHeadwaySecs;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setExactTimes(boolean aExactTimes)
  {
    boolean wasSet = false;
    exactTimes = aExactTimes;
    wasSet = true;
    return wasSet;
  }

  public boolean resetExactTimes()
  {
    boolean wasReset = false;
    exactTimes = getDefaultExactTimes();
    wasReset = true;
    return wasReset;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public int getHeadwaySecs()
  {
    return headwaySecs;
  }

  public boolean getExactTimes()
  {
    return exactTimes;
  }
  /* Code from template attribute_GetDefaulted */
  public boolean getDefaultExactTimes()
  {
    return false;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isExactTimes()
  {
    return exactTimes;
  }
  /* Code from template association_GetOne */
  public Trip getTrip()
  {
    return trip;
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

  public void delete()
  {
    trip = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "headwaySecs" + ":" + getHeadwaySecs()+ "," +
            "exactTimes" + ":" + getExactTimes()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "trip = "+(getTrip()!=null?Integer.toHexString(System.identityHashCode(getTrip())):"null");
  }
}