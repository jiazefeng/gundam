package com.maxrocky.gundam.domain.role.repository;

import com.maxrocky.gundam.domain.role.model.Rolebuttonmap;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
public interface RoleButtonMapRepository extends BaseRepository<Rolebuttonmap> {
    public boolean addRoleButtonMap(List<Rolebuttonmap> list);
    //ɾ����ɫ����
    public boolean deleteRoleButtonMap(String roleId);
    //ͨ����ɫID��ѯbutton
    public List<Rolebuttonmap> getButtonList(String roleId);
}
