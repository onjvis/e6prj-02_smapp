package dk.au671048.e6prj02.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Bin {
    @PrimaryKey
    @NonNull
    private String _id;
    private double latitude;
    private double longitude;
    private String address;
    private float battery;
    private float fullness;

    public Bin(@NotNull String _id, double latitude, double longitude, String address,
               float battery, float fullness) {
        this._id = _id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.battery = battery;
        this.fullness = fullness;
    }

    @NotNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NotNull String _id) {
        this._id = _id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBattery() {
        return battery;
    }

    public void setBattery(float battery) {
        this.battery = battery;
    }

    public float getFullness() {
        return fullness;
    }

    public void setFullness(float fullness) {
        this.fullness = fullness;
    }
}
