package com.maxrocky.gundam.domain.picture.repositoryImpl;

import com.maxrocky.gundam.domain.picture.model.PictureInfo;
import com.maxrocky.gundam.domain.picture.repository.PictureRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/27.
 */
@Repository
public class PictureRepositoryImpl extends BaseRepositoryImpl<PictureInfo> implements PictureRepository {


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<PictureInfo> getPictureList() {
        List<Object> params = new ArrayList<Object>();
        String hql = "from PictureInfo where status = ?";
        params.add("1");
        hql += " ORDER BY uplodDate DESC";
        return this.findByQueryList(hql, new PageInfoTools(), params);
    }

    @Override
    public int getCount() {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", "1"));
        return this.findByCriteriaForPageCount(sql);

    }

    @Override
    public List<PictureInfo> getPictureList(PictureInfo pictureInfo, int startRow) {
        List<Object> params = new ArrayList<Object>();
        String hql = "from PictureInfo as p where p.status = ?";
        params.add("1");

        if (pictureInfo.getRobotId() != null && !pictureInfo.getRobotId().equals("")) {
            hql += " and p.robotId= '" + pictureInfo.getRobotId() + "'";
        }
        if (pictureInfo.getUplodDate() != null && !pictureInfo.getUplodDate().equals("")) {
            hql += " and p.uplodDate >= '" + sdf.format(pictureInfo.getUplodDate()) + "'";
        }
        if (pictureInfo.getPictureId() != null && !pictureInfo.getPictureId().equals("")) {
            hql += " and p.pictureId ='" + pictureInfo.getPictureId() + "'";
        }

        hql += " ORDER BY  p.uplodDate DESC";

        List<PictureInfo> pictureInfos = this.findByQueryList(hql, new PageInfoTools(startRow, 10), params);

        return pictureInfos;
    }

    @Override
    public int getCount(PictureInfo pictureInfo) {
        List<Criterion> sql = new ArrayList<Criterion>();

        sql.add(Restrictions.eq("status", "1"));
        if (pictureInfo.getRobotId() != null && !pictureInfo.getRobotId().equals("")) {
            sql.add(Restrictions.eq("robotId", pictureInfo.getRobotId()));
        }
        if (pictureInfo.getUplodDate() != null && !pictureInfo.getUplodDate().equals("")) {
            sql.add(Restrictions.ge("uplodDate", pictureInfo.getUplodDate()));
        }
        if (pictureInfo.getPictureId() != null && !pictureInfo.getPictureId().equals("")) {
            sql.add(Restrictions.eq("pictureId", pictureInfo.getPictureId()));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public PictureInfo findPictureInfoByID(String id) {
        List<Object> params = new ArrayList<Object>();
        String hql = null;
        if (id != null && !id.equals("")) {
            hql = " from PictureInfo where pictureId = ?";
        }
        params.add(id);
        return this.findByQueryResult(hql, params);
    }

    @Override
    public boolean deletePicture(String pictureId) {
        PictureInfo pictureInfo = new PictureInfo();
        if (pictureId != null && !pictureId.equals("")) {
            pictureInfo = findPictureInfoByID(pictureId);
        }
        if (pictureInfo != null) {
            pictureInfo.setStatus("0");
            this.update(pictureInfo);
            return true;
        } else {
            return false;
        }
    }

}
