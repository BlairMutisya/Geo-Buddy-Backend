package com.GB.Application.model;

public class GF21Message {
    private String deviceId;
    private String timestamp;
    private double latitude;
    private double longitude;

    // Constructor, getters, and setters
    public GF21Message(String deviceId, String timestamp, double latitude, double longitude) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static GF21Message parse(String rawMessage) {
        String[] parts = rawMessage.split(",");
        return new GF21Message(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}