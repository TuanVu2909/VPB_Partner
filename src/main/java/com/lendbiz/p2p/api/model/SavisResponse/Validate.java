package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Validate {
    private double[] idconf;
    private long idLogic;
    private String idLogicMessage;
    private String idCheck;

    @JsonProperty("idconf")
    public double[] getIdconf() {
        return idconf;
    }

    @JsonProperty("idconf")
    public void setIdconf(double[] value) {
        this.idconf = value;
    }

    @JsonProperty("id_logic")
    public long getIDLogic() {
        return idLogic;
    }

    @JsonProperty("id_logic")
    public void setIDLogic(long value) {
        this.idLogic = value;
    }

    @JsonProperty("id_logic_message")
    public String getIDLogicMessage() {
        return idLogicMessage;
    }

    @JsonProperty("id_logic_message")
    public void setIDLogicMessage(String value) {
        this.idLogicMessage = value;
    }

    @JsonProperty("id_check")
    public String getIDCheck() {
        return idCheck;
    }

    @JsonProperty("id_check")
    public void setIDCheck(String value) {
        this.idCheck = value;
    }
}