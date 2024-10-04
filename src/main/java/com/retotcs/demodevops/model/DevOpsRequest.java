package com.retotcs.demodevops.model;

public class DevOpsRequest {
  
  private String message;
  private String to;
  private String from;
  private int timeToLifeSec;

  // Getters y setters

  public String getMessage() {
      return message;
  }

  public void setMessage(String message) {
      this.message = message;
  }

  public String getTo() {
      return to;
  }

  public void setTo(String to) {
      this.to = to;
  }

  public String getFrom() {
      return from;
  }

  public void setFrom(String from) {
      this.from = from;
  }

  public int getTimeToLifeSec() {
      return timeToLifeSec;
  }

  public void setTimeToLifeSec(int timeToLifeSec) {
      this.timeToLifeSec = timeToLifeSec;
  }
  
}
