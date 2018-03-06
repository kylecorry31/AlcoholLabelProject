package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.*;

import java.time.LocalDate;
import java.util.Objects;

public class COLA {

    public static final String DB_TABLE = "alcohol";
    public static final String DB_BRAND_NAME = "brand_name";
    public static final String DB_ALCOHOL_TYPE = "alcohol_type";
    public static final String DB_SERIAL_NUMBER = "serial_number";
    public static final String DB_ORIGIN = "origin";
    public static final String DB_ALCOHOL_CONTENT = "alcohol_content";
    public static final String DB_FANCIFUL_NAME = "fanciful_name";
    public static final String DB_SUBMISSION_DATE = "submission_date";
    public static final String DB_APPROVAL_DATE = "approval_date";
    public static final String DB_STATUS = "status";
    public static final String DB_LABEL_IMAGE = "label_image";
    public static final String DB_ID = "id";

    public static final String DEFAULT_LABEL_IMAGE = "/images/noLabel.png";

    private String brandName;
    private AlcoholType type;
    private String serialNumber;
    private ProductSource origin;

    private long id;
    private String fancifulName;
    private double alcoholContent;
    private String formula;
    private LocalDate submissionDate;
    private LocalDate approvalDate;
    private ApplicationStatus status;
    private ILabelImage labelImage;

    public COLA(String brandName, AlcoholType type, String serialNumber, ProductSource origin) {
        this.brandName = brandName;
        this.type = type;
        this.serialNumber = serialNumber;
        this.origin = origin;
        id = -1;
        fancifulName = "";
        alcoholContent = 0;
        submissionDate = LocalDate.now();
        approvalDate = LocalDate.of(1990, 1, 1);
        status = ApplicationStatus.RECEIVED;
        labelImage = new ProxyLabelImage(DEFAULT_LABEL_IMAGE);
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

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public ILabelImage getLabelImage() {
        return labelImage;
    }

    public void setLabelImage(ILabelImage labelImage) {
        this.labelImage = labelImage;
    }

    public String getLabelImageFilename(){
        if (labelImage == null || labelImage.getFileName() == null){
            return DEFAULT_LABEL_IMAGE;
        }

        return labelImage.getFileName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COLA that = (COLA) o;
        return id == that.id &&
                Double.compare(that.alcoholContent, alcoholContent) == 0 &&
                Objects.equals(brandName, that.brandName) &&
                type == that.type &&
                Objects.equals(serialNumber, that.serialNumber) &&
                origin == that.origin &&
                Objects.equals(fancifulName, that.fancifulName) &&
                Objects.equals(formula, that.formula) &&
                Objects.equals(submissionDate, that.submissionDate) &&
                Objects.equals(approvalDate, that.approvalDate) &&
                status == that.status &&
                Objects.equals(labelImage, that.labelImage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brandName, type, serialNumber, origin, id, fancifulName, alcoholContent, formula, submissionDate, approvalDate, status, labelImage);
    }
}
