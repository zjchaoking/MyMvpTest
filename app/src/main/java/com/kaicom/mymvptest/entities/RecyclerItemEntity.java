package com.kaicom.mymvptest.entities;

import com.kaicom.mymvptest.datasource.entity.TraceRecordsEntity;

/**
 */
public class RecyclerItemEntity {

    int imageRes;
    TraceRecordsEntity entity;


    public RecyclerItemEntity(TraceRecordsEntity entity, int imageRes) {
        this.imageRes = imageRes;
        this.entity = entity;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public TraceRecordsEntity getEntity() {
        return entity;
    }

    public void setEntity(TraceRecordsEntity entity) {
        this.entity = entity;
    }
}
