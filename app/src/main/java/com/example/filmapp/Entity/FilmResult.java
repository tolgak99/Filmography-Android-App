package com.example.filmapp.Entity;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilmResult implements Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    public final static Creator<FilmResult> CREATOR = new Creator<FilmResult>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FilmResult createFromParcel(android.os.Parcel in) {
            return new FilmResult(in);
        }

        public FilmResult[] newArray(int size) {
            return (new FilmResult[size]);
        }

    }
            ;

    protected FilmResult(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.result, (com.example.filmapp.Entity.Result.class.getClassLoader()));
    }

    public FilmResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(result);
    }

    public int describeContents() {
        return 0;
    }

}

