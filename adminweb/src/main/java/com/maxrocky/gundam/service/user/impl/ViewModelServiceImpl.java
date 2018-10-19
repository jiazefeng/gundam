package com.maxrocky.gundam.service.user.impl;

import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.role.repository.ViewModelRepository;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;
import com.maxrocky.gundam.service.user.inf.ViewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Service
public class ViewModelServiceImpl implements ViewModelService {
    @Autowired
    private ViewModelRepository viewmodelRepository;
    @Override
    public List<Viewmodel> getViewListByUserId(String userid) {
        List<Viewmodel> viewmodels = new ArrayList<>();
        if(userid==null){
            return null;
        }else{
            viewmodels = viewmodelRepository.getViewListByUserId(userid);
            if(viewmodels!=null && viewmodels.size()>0){
                return viewmodels;
            }
        }
        return null;
    }
    @Override
         public List<SecondViewModel> getViewListOtherByUserId(String userid,String menuId) {
        List<Viewmodel> viewmodels = new ArrayList<>();
        List<SecondViewModel> secondViewModels = new ArrayList<SecondViewModel>();
        if(userid==null){
            return null;
        }else{
            viewmodels = viewmodelRepository.getViewLisOthertByUserId(userid, menuId);
            if(viewmodels!=null && viewmodels.size()>0){
                for (Viewmodel viewmodel : viewmodels){
                    SecondViewModel secondViewModel = new SecondViewModel();
                    secondViewModel.setMenuName(viewmodel.getMenuName());
                    secondViewModel.setRunscript(viewmodel.getRunscript());
                    secondViewModel.setMenuId(viewmodel.getMenuId());
                    secondViewModels.add(secondViewModel);
                }
                return secondViewModels;
            }
        }
        return null;

    }
    @Override
    public List<Viewmodel> getViewListThreeByUserId(String userid,String menuId) {
        List<Viewmodel> viewmodels = new ArrayList<>();
        if(userid==null){
            return null;
        }else{
            viewmodels = viewmodelRepository.getViewLisThreeByUserId(userid,menuId);
            if(viewmodels!=null && viewmodels.size()>0){
                return viewmodels;
            }
        }
        return null;

    }
}
