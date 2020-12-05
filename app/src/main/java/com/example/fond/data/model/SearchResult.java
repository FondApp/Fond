package com.example.fond.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("calories")
    @Expose
    private Integer calories;
    @SerializedName("carbs")
    @Expose
    private String carbs;
    @SerializedName("fat")
    @Expose
    private String fat;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;
    @SerializedName("protein")
    @Expose
    private String protein;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
