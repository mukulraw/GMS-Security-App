package com.gms.gmsapp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class chatBean {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_content")
    @Expose
    private String ticketContent;
    @SerializedName("comment_owner_id")
    @Expose
    private String commentOwnerId;
    @SerializedName("comment_owner_role")
    @Expose
    private String commentOwnerRole;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public String getCommentOwnerId() {
        return commentOwnerId;
    }

    public void setCommentOwnerId(String commentOwnerId) {
        this.commentOwnerId = commentOwnerId;
    }

    public String getCommentOwnerRole() {
        return commentOwnerRole;
    }

    public void setCommentOwnerRole(String commentOwnerRole) {
        this.commentOwnerRole = commentOwnerRole;
    }

}
