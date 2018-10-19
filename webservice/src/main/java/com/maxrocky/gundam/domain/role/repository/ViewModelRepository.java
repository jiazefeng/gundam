package com.maxrocky.gundam.domain.role.repository;

import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
public interface ViewModelRepository extends BaseRepository<Viewmodel> {
    List<Viewmodel> getViewListByUserId(String userid);
    List<Viewmodel> getViewLisOthertByUserId(String userid, String menuId);
    List<Viewmodel> getViewLisThreeByUserId(String userid, String menuId);
    //查询所有一级菜单
    List<Viewmodel> getViewModel();
    //查询一级菜单下的其他菜单
    List<Viewmodel> getViewLisOthertBymenuId(String menuId);

}
