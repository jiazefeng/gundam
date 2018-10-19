package com.maxrocky.gundam.coreservice.core;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuer5 on 16/6/12.
 */
@Repository
public class CoreCache {

    private Map<String, RobotSnapshot> data = new HashMap<String, RobotSnapshot>();

    private static class CoreCacheHolder{
        private static final CoreCache INSTANCE = new CoreCache();
    }

    private CoreCache(){
    }

    public static final CoreCache getInstance() {

        return CoreCacheHolder.INSTANCE;
    }

    public RobotSnapshot getData(String clientId) {
        if(!this.data.containsKey(clientId))
            return null;
        else
            return this.data.get(clientId);
    }

    public void setData(String clientId, RobotSnapshot value) {

        if(!this.data.containsKey(clientId) || this.data.get(clientId) != value){
            this.data.put(clientId, value);
        }
    }

}
