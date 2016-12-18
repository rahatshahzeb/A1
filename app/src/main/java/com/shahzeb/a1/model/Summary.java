package com.shahzeb.a1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Summary implements Parcelable {

    public String manufacturer;
    public String mainType;
    public String builtDate;

    public Summary(String manufacturer, String mainType, String builtDate) {
        this.manufacturer = manufacturer;
        this.mainType = mainType;
        this.builtDate = builtDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.manufacturer);
        dest.writeString(this.mainType);
        dest.writeString(this.builtDate);
    }

    public Summary() {
    }

    protected Summary(Parcel in) {
        this.manufacturer = in.readString();
        this.mainType = in.readString();
        this.builtDate = in.readString();
    }

    public static final Parcelable.Creator<Summary> CREATOR = new Parcelable.Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel source) {
            return new Summary(source);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };
}
