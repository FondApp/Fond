package com.example.fond.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplexSearchResults {

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("results")
    @Expose
    private List<SearchResult> searchResults = null;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getSearchIds() {

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < searchResults.size() ; i++) {
            ids.add(Integer.toString(this.searchResults.get(i).getId()));
        }

        return String.join(",", ids);
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}

