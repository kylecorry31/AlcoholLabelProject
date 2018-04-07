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
    public static final String DB_FORMULA = "formula";
    public static final String DB_STATUS = "status";
    public static final String DB_LABEL_IMAGE = "label_image";
    public static final String DB_APPLICANT_ID = "applicant_id";
    public static final String DB_ASSIGNED_TO = "assigned_to";
    public static final String DB_WINE_PH = "wine_ph";
    public static final String DB_WINE_VINTAGE_YEAR = "wine_vintage_year";
    public static final String DB_LAST_UPDATED = "last_updated";
    public static final String DB_EXPIRATION_DATE = "expiration_date";
    public static final String DB_ID = "id";

    public static final String DEFAULT_LABEL_IMAGE = "/images/noImage.png";

    private String brandName;
    private AlcoholType type;
    private String serialNumber;
    private ProductSource origin;

    private long id;
    private String fancifulName;
    private double alcoholContent;
    private long formula;
    private LocalDate submissionDate;
    private LocalDate approvalDate;
    private ApplicationStatus status;
    private ILabelImage labelImage;
    private long applicantID;
    private long assignedTo;

    private LocalDate expirationDate;
    private LocalDate lastUpdated;

    private int vintageYear;
    private double winePH;

    public COLA(long id, String brandName, AlcoholType type, String serialNumber, ProductSource origin) {
        this.brandName = brandName;
        this.type = type;
        this.serialNumber = serialNumber;
        this.origin = origin;
        this.id = id;
        fancifulName = "";
        formula = -1;
        alcoholContent = 0;
        applicantID = -1;
        assignedTo = -1;
        submissionDate = LocalDate.now();
        approvalDate = LocalDate.of(1990, 1, 1);
        status = ApplicationStatus.RECEIVED;
        labelImage = new ProxyLabelImage(DEFAULT_LABEL_IMAGE);
        vintageYear = -1;
        winePH = -1;
        lastUpdated = submissionDate;
        expirationDate = LocalDate.now().plusYears(1);
    }

    public int getVintageYear() {
        return vintageYear;
    }

    public void setVintageYear(int vintageYear) {
        this.vintageYear = vintageYear;
    }

    public double getWinePH() {
        return winePH;
    }

    public void setWinePH(double winePH) {
        this.winePH = winePH;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
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

    public long getFormula() {
        return formula;
    }

    public void setFormula(long formula) {
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

    public void setProof(double proof){
        setAlcoholContent(proof / 2);
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

    public long getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(long applicantID) {
        this.applicantID = applicantID;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "COLA{" +
                "brandName='" + brandName + '\'' +
                ", type=" + type +
                ", serialNumber='" + serialNumber + '\'' +
                ", origin=" + origin +
                ", id=" + id +
                ", fancifulName='" + fancifulName + '\'' +
                ", alcoholContent=" + alcoholContent +
                ", formula=" + formula +
                ", submissionDate=" + submissionDate +
                ", approvalDate=" + approvalDate +
                ", status=" + status +
                ", labelImage=" + labelImage +
                ", applicantID=" + applicantID +
                ", assignedTo=" + assignedTo +
                ", expirationDate=" + expirationDate +
                ", lastUpdated=" + lastUpdated +
                ", vintageYear=" + vintageYear +
                ", winePH=" + winePH +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COLA cola = (COLA) o;
        return id == cola.id &&
                Double.compare(cola.alcoholContent, alcoholContent) == 0 &&
                formula == cola.formula &&
                applicantID == cola.applicantID &&
                assignedTo == cola.assignedTo &&
                vintageYear == cola.vintageYear &&
                Double.compare(cola.winePH, winePH) == 0 &&
                Objects.equals(brandName, cola.brandName) &&
                type == cola.type &&
                Objects.equals(serialNumber, cola.serialNumber) &&
                origin == cola.origin &&
                Objects.equals(fancifulName, cola.fancifulName) &&
                Objects.equals(submissionDate, cola.submissionDate) &&
                Objects.equals(approvalDate, cola.approvalDate) &&
                status == cola.status &&
                Objects.equals(labelImage, cola.labelImage) &&
                Objects.equals(expirationDate, cola.expirationDate) &&
                Objects.equals(lastUpdated, cola.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandName, type, serialNumber, origin, id, fancifulName, alcoholContent, formula, submissionDate, approvalDate, status, labelImage, applicantID, assignedTo, expirationDate, lastUpdated, vintageYear, winePH);
    }

}
