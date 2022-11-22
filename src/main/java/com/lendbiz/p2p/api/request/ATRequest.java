package com.lendbiz.p2p.api.request;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lendbiz.p2p.api.request.accesstrade.Item;

public class ATRequest {
    private String conversionID;
    private String conversionResultID;
    private String trackingID;
    private String transactionID;
    private OffsetDateTime transactionTime;
    private long transactionValue;
    private long transactionDiscount;
    private Item[] items;

    @JsonProperty("conversion_id")
    public String getConversionID() { return conversionID; }
    @JsonProperty("conversion_id")
    public void setConversionID(String value) { this.conversionID = value; }

    @JsonProperty("conversion_result_id")
    public String getConversionResultID() { return conversionResultID; }
    @JsonProperty("conversion_result_id")
    public void setConversionResultID(String value) { this.conversionResultID = value; }

    @JsonProperty("tracking_id")
    public String getTrackingID() { return trackingID; }
    @JsonProperty("tracking_id")
    public void setTrackingID(String value) { this.trackingID = value; }

    @JsonProperty("transaction_id")
    public String getTransactionID() { return transactionID; }
    @JsonProperty("transaction_id")
    public void setTransactionID(String value) { this.transactionID = value; }

    @JsonProperty("transaction_time")
    public OffsetDateTime getTransactionTime() { return transactionTime; }
    @JsonProperty("transaction_time")
    public void setTransactionTime(OffsetDateTime value) { this.transactionTime = value; }

    @JsonProperty("transaction_value")
    public long getTransactionValue() { return transactionValue; }
    @JsonProperty("transaction_value")
    public void setTransactionValue(long value) { this.transactionValue = value; }

    @JsonProperty("transaction_discount")
    public long getTransactionDiscount() { return transactionDiscount; }
    @JsonProperty("transaction_discount")
    public void setTransactionDiscount(long value) { this.transactionDiscount = value; }

    @JsonProperty("items")
    public Item[] getItems() { return items; }
    @JsonProperty("items")
    public void setItems(Item[] value) { this.items = value; }
}
