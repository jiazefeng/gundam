package com.maxrocky.gundam.domain.role.repositoryImpl;

import com.maxrocky.gundam.domain.role.model.Role;
import com.maxrocky.gundam.domain.role.repository.RoleRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/2.
 */
@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<Role> implements RoleRepository {
    @Override
    public List<Role> getRoleList() {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Role where state = ?";
        params.add(1);
        sql += " ORDER BY degree ASC";
        return this.findByQueryList(sql, params);
    }

    @Override
    public Role getRoleById(String roleId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Role where roleId = ?";
        params.add(roleId);
        return (Role) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public List<Role> getRole() {
        String sql = "from Role where 1=? ";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        sql += " ORDER BY modifyDate DESC";
        return this.findByQueryList(sql, new PageInfoTools(0, 10), params);
    }

    @Override
    public int getRoleCount() {
        List<Object> params = new ArrayList<Object>();
        String sql = "select count(1) from Role where 1=1";
        return this.findByCriteriaForPageCount(sql, params);
//        String sql = "select count(1) from Role where 1 = 1 ";
//        this.findUniqueResult(sql);
//        return count;
    }

    @Override
    public boolean addRole(Role role) {
        if (role != null) {
            this.save(role);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRole(Role role) {
        if (role != null) {
            this.update(role);
            return true;
        }
        return false;
    }

    @Override
    public List<Role> searchRole(int startRow) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Role where 1 = ?";
        params.add(1);
        sql += " ORDER BY modifyDate DESC";
        return this.findByQueryList(sql, new PageInfoTools(startRow, 10), params);
    }

    @Override
    public List<Role> inquireRole(int degree) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Role where state = ? AND degree >= ?";
        params.add(1);
        params.add(degree);
        sql += " ORDER BY degree ASC";
        return this.findByQueryList(sql, params);
    }

    @Override
    public Role searRoleInfo(String userId) {
        List<Object> params = new ArrayList<Object>();
        String sql = " FROM Role as r WHERE r.roleSetId = (SELECT rs.setId FROM Roleset as rs WHERE rs.setId = (SELECT rt.roleId FROM Userroleauthority as rt WHERE rt.userId = ? ))";
        params.add(userId);
        return (Role) this.findObjectByQueryResult(sql, params);
    }
}
