package com.maxrocky.gundam.domain.map.repositoryimpl;

import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.model.MapInfo;
import com.maxrocky.gundam.domain.map.repository.MapInfoRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/07/02.
 */
@Repository
public class MapInfoRepositoryImpl extends BaseRepositoryImpl<MapInfo> implements MapInfoRepository {
    @Override
    public boolean uploadMap(MapInfo mapInfo) {
        if (mapInfo != null) {
            this.save(mapInfo);
            return true;
        }
        return false;
    }

    @Override
    public MapInfo findMapByVillageId(String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from MapInfo where status=?";
        params.add(0);
        if (villageId != null && !villageId.equals("")) {
            sql += " AND villageId=?";
            params.add(villageId);
        }
        return (MapInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public List<MapInfo> getMapInfoByVillageId(String villageId) {
        String sql=" from MapInfo where status=0";
        List<Object> params = new ArrayList<Object>();
        if (villageId != null && !villageId.equals("")) {
            sql += " AND villageId=?";
            params.add(villageId);
        }
        sql+=" ORDER BY createOn DESC";
        List<MapInfo> mapInfoList = findByQueryList(sql,params);
        return mapInfoList;
    }

    @Override
    public List<MapInfo> getMapInfoByMapId(int mapId) {
        String sql=" from MapInfo where status=0";
        List<Object> params = new ArrayList<Object>();
        if (mapId >0) {
            sql += " AND mapId=?";
            params.add(mapId);
        }
        sql+=" ORDER BY createOn DESC";
        List<MapInfo> mapInfoList = findByQueryList(sql,params);
        return mapInfoList;
    }

    @Override
    public MapInfo findMapByMapId(int mapId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from MapInfo where status=?";
        params.add(0);
        if (mapId > 0) {
            sql += " AND mapId=?";
            params.add(mapId);
        }
        return (MapInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public boolean deleteMap(MapInfo mapInfo) {
        if (mapInfo != null) {
            this.update(mapInfo);
            return true;
        }
        return false;
    }
    @Override
    public MapInfo getMapById(String id){
        List<Object> params = new ArrayList<Object>();
        String sql = "from MapInfo where status=?";
        params.add(0);
        if (id!=null&&!id.equals("")) {
            sql += " AND villageId=?";
            params.add(id);
        }
        return (MapInfo) this.findObjectByQueryResult(sql, params);
    }
}
