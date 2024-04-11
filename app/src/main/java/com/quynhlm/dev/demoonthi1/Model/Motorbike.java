package com.quynhlm.dev.demoonthi1.Model;

public class Motorbike {
    private String _id , name_ph32353,image_ph32353,color_ph32353,describe_ph32353;
    private Double price_ph32353;

    public Motorbike() {
    }

    public Motorbike(String _id, String name_ph32353, String image_ph32353, String color_ph32353, String describe_ph32353, Double price_ph32353) {
        this._id = _id;
        this.name_ph32353 = name_ph32353;
        this.image_ph32353 = image_ph32353;
        this.color_ph32353 = color_ph32353;
        this.describe_ph32353 = describe_ph32353;
        this.price_ph32353 = price_ph32353;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName_ph32353() {
        return name_ph32353;
    }

    public void setName_ph32353(String name_ph32353) {
        this.name_ph32353 = name_ph32353;
    }

    public String getImage_ph32353() {
        return image_ph32353;
    }

    public void setImage_ph32353(String image_ph32353) {
        this.image_ph32353 = image_ph32353;
    }

    public String getColor_ph32353() {
        return color_ph32353;
    }

    public void setColor_ph32353(String color_ph32353) {
        this.color_ph32353 = color_ph32353;
    }

    public String getDescribe_ph32353() {
        return describe_ph32353;
    }

    public void setDescribe_ph32353(String describe_ph32353) {
        this.describe_ph32353 = describe_ph32353;
    }

    public Double getPrice_ph32353() {
        return price_ph32353;
    }

    public void setPrice_ph32353(Double price_ph32353) {
        this.price_ph32353 = price_ph32353;
    }
}
