package com.maxrocky.gundam.coreservice.model.biz;

/**
 * Created by yuer5 on 16/6/26.
 */
public class UpStreamId {
    private String EndpointKeyHash;
    private String robetID;

    public UpStreamId() {
    }

    public String getEndpointKeyHash() {
        return EndpointKeyHash;
    }

    public void setEndpointKeyHash(String endpointKeyHash) {
        EndpointKeyHash = endpointKeyHash;
    }

    public String getRobetID() {
        return robetID;
    }

    public void setRobetID(String robetID) {
        this.robetID = robetID;
    }
}
