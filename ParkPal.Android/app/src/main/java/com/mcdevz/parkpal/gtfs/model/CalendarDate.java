/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.sql.Date;

// line 143 "../../../../../../../../ump/tmp356049/model.ump"
// line 228 "../../../../../../../../ump/tmp356049/model.ump"
public class CalendarDate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CalendarDate Attributes
  private String id;
  private Date date;
  private int exceptionType;

  //CalendarDate Associations
  private Agency agency;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CalendarDate(String aId, Date aDate, int aExceptionType, Agency aAgency)
  {
    id = aId;
    date = aDate;
    exceptionType = aExceptionType;
    if (!setAgency(aAgency))
    {
      throw new RuntimeException("Unable to create CalendarDate due to aAgency");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setExceptionType(int aExceptionType)
  {
    boolean wasSet = false;
    exceptionType = aExceptionType;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public Date getDate()
  {
    return date;
  }

  public int getExceptionType()
  {
    return exceptionType;
  }
  /* Code from template association_GetOne */
  public Agency getAgency()
  {
    return agency;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAgency(Agency aNewAgency)
  {
    boolean wasSet = false;
    if (aNewAgency != null)
    {
      agency = aNewAgency;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    agency = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "exceptionType" + ":" + getExceptionType()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "agency = "+(getAgency()!=null?Integer.toHexString(System.identityHashCode(getAgency())):"null");
  }
}