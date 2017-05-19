package com.gms.gmsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class supportListBean {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_type")
    @Expose
    private String ticketType;
    @SerializedName("ticket_subject")
    @Expose
    private String ticketSubject;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("report_on")
    @Expose
    private String reportOn;
    @SerializedName("ticket_content")
    @Expose
    private String ticketContent;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketSubject() {
        return ticketSubject;
    }

    public void setTicketSubject(String ticketSubject) {
        this.ticketSubject = ticketSubject;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getReportOn() {
        return reportOn;
    }

    public void setReportOn(String reportOn) {
        this.reportOn = reportOn;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

}
