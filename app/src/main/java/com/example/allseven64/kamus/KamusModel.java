package com.example.allseven64.kamus;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusModel implements Parcelable {
    private int id;
    private String kata_asal;
    private String arti;

    public KamusModel(){

    }

    public KamusModel(String kata_asal, String arti){
        this.kata_asal = kata_asal;
        this.arti = arti;
    }

    public KamusModel (int id, String kata_asal, String arti){
        this.id = id;
        this.kata_asal = kata_asal;
        this.arti = arti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata_asal() {
        return kata_asal;
    }

    public void setKata_asal(String kata_asal) {
        this.kata_asal = kata_asal;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeString(this.kata_asal);
        dest.writeString(this.arti);
    }

    protected KamusModel(Parcel in){
        this.id = in.readInt();
        this.kata_asal = in.readString();
        this.arti = in.readString();
    }

    public static final Parcelable.Creator<KamusModel> CREATOR = new Parcelable.Creator<KamusModel>(){
        @Override
        public KamusModel createFromParcel(Parcel source){
            return new KamusModel(source);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };


}
