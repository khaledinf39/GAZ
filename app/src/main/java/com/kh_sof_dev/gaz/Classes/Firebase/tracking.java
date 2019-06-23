package com.kh_sof_dev.gaz.Classes.Firebase;

public class tracking {
    private Boolean active;
    private Double lat,lng;
    private car_informatiom car_informatiom_;

    public tracking() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public car_informatiom getCar_informatiom_() {
        return car_informatiom_;
    }

    public void setCar_informatiom_(car_informatiom car_informatiom_) {
        this.car_informatiom_ = car_informatiom_;
    }


}
