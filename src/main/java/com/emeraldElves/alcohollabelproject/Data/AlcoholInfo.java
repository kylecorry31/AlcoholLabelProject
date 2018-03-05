package com.emeraldElves.alcohollabelproject.Data;

import java.util.Objects;

public class AlcoholInfo {

    public static final String DB_TABLE = "alcohol";
    public static final String DB_BRAND_NAME = "brand_name";
    public static final String DB_ALCOHOL_TYPE = "alcohol_type";
    public static final String DB_SERIAL_NUMBER = "serial_number";
    public static final String DB_ORIGIN = "origin";
    public static final String DB_ALCOHOL_CONTENT = "alcohol_content";
    public static final String DB_FANCICUL_NAME = "fanciful_name";
    public static final String DB_ID = "id";

    private String brandName;
    private AlcoholType type;
    private String serialNumber;
    private ProductSource origin;

    private long id;
    private String fancifulName;
    private double alcoholContent;
    private String formula;

    public AlcoholInfo(String brandName, AlcoholType type, String serialNumber, ProductSource origin) {
        this.brandName = brandName;
        this.type = type;
        this.serialNumber = serialNumber;
        this.origin = origin;
        id = -1;
        fancifulName = "";
        alcoholContent = 0;
    }

    public String getBrandName() {
        return brandName;
    }

    public AlcoholType getType() {
        return type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ProductSource getOrigin() {
        return origin;
    }

    public long getId() {
        return id;
    }

    public String getFancifulName() {
        return fancifulName;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFancifulName(String fancifulName) {
        this.fancifulName = fancifulName;
    }

    public void setAlcoholContent(double alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlcoholInfo that = (AlcoholInfo) o;
        return id == that.id &&
                Double.compare(that.alcoholContent, alcoholContent) == 0 &&
                Objects.equals(brandName, that.brandName) &&
                type == that.type &&
                Objects.equals(serialNumber, that.serialNumber) &&
                origin == that.origin &&
                Objects.equals(fancifulName, that.fancifulName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brandName, type, serialNumber, origin, id, fancifulName, alcoholContent);
    }
}
