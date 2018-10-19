package com.maxrocky.gundam.coreservice.model.biz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/6/28.
 */
public class DownStreamTasks {

    private int type;
//    private int taskCategory;
    private List<DownStreamMetadata> positions;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public int getTaskCategory() {
//        return taskCategory;
//    }
//
//    public void setTaskCategory(int taskCategory) {
//        this.taskCategory = taskCategory;
//    }

    public List<DownStreamMetadata> getPositions() {
        if(positions == null)
            positions = new ArrayList<>();
        return positions;
    }

    public void setPositions(List<DownStreamMetadata> positions) {
        this.positions = positions;
    }
}
