package com.maxrocky.gundam.service.user.inf;

import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
public interface ViewModelService {
    List<Viewmodel> getViewListByUserId(String userid);
    List<SecondViewModel> getViewListOtherByUserId(String userid, String meunId);
    List<Viewmodel> getViewListThreeByUserId(String userid, String meunId);
}
