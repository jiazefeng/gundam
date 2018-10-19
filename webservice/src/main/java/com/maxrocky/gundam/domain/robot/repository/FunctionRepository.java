package com.maxrocky.gundam.domain.robot.repository;


import com.maxrocky.gundam.domain.robot.model.Function;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by jiazefeng on 2016/7/11.
 */
public interface FunctionRepository extends BaseRepository<Function> {

    /**
     * 检索全部功能信息
     *
     * @return
     */
    public List<Function> getFunctionList();
    /**
     * 检索全部功能信息
     *
     * @return
     */
    public List<Function> getFunctionList(String id);

    /**
     * 按id 检索功能信息
     *
     * @param id
     * @return
     */
    public Function getFunctionById(String id);

}
