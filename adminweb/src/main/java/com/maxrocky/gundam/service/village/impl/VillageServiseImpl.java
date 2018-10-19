package com.maxrocky.gundam.service.village.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.domain.map.model.MapInfo;
import com.maxrocky.gundam.domain.map.repository.MapInfoRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.domain.task.repository.TaskInfoRepository;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.user.repository.UserRepository;
import com.maxrocky.gundam.domain.user.repository.UserVillageRepository;
import com.maxrocky.gundam.domain.village.dto.VillageDTO;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.domain.village.repository.VillageAndUserRepository;
import com.maxrocky.gundam.domain.village.repository.VillageRepository;
import com.maxrocky.gundam.service.village.inf.VillageAndUserService;
import com.maxrocky.gundam.service.village.inf.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
@Service
public class VillageServiseImpl implements VillageService {
    @Autowired
    private VillageRepository villageRepository;
    @Autowired
    private MapInfoRepository mapInfoRepository;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private VillageAndUserRepository villageAndUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserVillageRepository userVillageRepository;
    @Autowired
    private VillageAndUserService villageAndUserService;

    @Override
    public ApiResult getVillage(String villageId) {
        int count = villageRepository.getCount(villageId);


        List<Village> allVillageList = villageRepository.getAllVillage();

        List<Village> villageList = villageRepository.getVillage(villageId);
        List<VillageDTO> villageDTOs = new ArrayList<VillageDTO>();
        if (villageList != null && villageList.size() > 0) {
            for (Village village : villageList) {
                VillageDTO villageDTO = new VillageDTO();
                villageDTO.setDistrictId(village.getVillageId());
                villageDTO.setDistrictName(village.getVillageName());
                villageDTO.setAddress(village.getAddress());
                villageDTO.setUser(village.getUserName());
                villageDTO.setPhone(village.getMobile());

                int rTotal = robotRepository.getRobotTotalByVillageId(village.getVillageId());
                if (rTotal > 0) {
                    villageDTO.setRobotCount(rTotal);
                } else {
                    villageDTO.setRobotCount(0);
                }

                int sTotal = strategyRepository.getStrategyTotalByVillageId(village.getVillageId());
                if (sTotal > 0) {
                    villageDTO.setStrategyCount(sTotal);
                } else {
                    villageDTO.setStrategyCount(0);
                }

                int tTotal = taskInfoRepository.totalByVillageId(village.getVillageId());
                if (tTotal > 0) {
                    villageDTO.setTaskCount(tTotal);
                } else {
                    villageDTO.setTaskCount(0);
                }

//                MapInfo mapInfo = mapInfoRepository.findMapByVillageId(village.getVillageId());
//                if (mapInfo != null) {
//                    villageDTO.setMapImg(mapInfo.getMapSrc());
//                }
                List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(village.getVillageId());
                if (mapInfoList != null && mapInfoList.size() > 0) {
                    MapInfo mapInfo = mapInfoList.get(0);
                    if (mapInfo != null) {
                        villageDTO.setMapImg(mapInfo.getMapSrc());

                    }
                }
                villageDTOs.add(villageDTO);
            }
        }

        ModelMap result = new ModelMap();
        result.addAttribute("villageList", villageDTOs);
        result.addAttribute("selectVillage", allVillageList);
        result.addAttribute("count", count);
        return new SuccessApiResult(result);
    }

    @Override
    public List<VillageDTO> getVillageDtoList(String villageId) {
        int count = villageRepository.getCount(villageId);

        List<Village> villageList = villageRepository.getVillage(villageId);
        List<VillageDTO> villageDTOs = new ArrayList<VillageDTO>();
        if (villageList != null && villageList.size() > 0) {
            for (Village village : villageList) {
                VillageDTO villageDTO = new VillageDTO();
                villageDTO.setDistrictId(village.getVillageId());
                villageDTO.setDistrictName(village.getVillageName());
                villageDTO.setAddress(village.getAddress());
                villageDTO.setUser(village.getUserName());
                villageDTO.setPhone(village.getMobile());

                int rTotal = robotRepository.getRobotTotalByVillageId(village.getVillageId());
                if (rTotal > 0) {
                    villageDTO.setRobotCount(rTotal);
                } else {
                    villageDTO.setRobotCount(0);
                }

                int sTotal = strategyRepository.getStrategyTotalByVillageId(village.getVillageId());
                if (sTotal > 0) {
                    villageDTO.setStrategyCount(sTotal);
                } else {
                    villageDTO.setStrategyCount(0);
                }

                int tTotal = taskInfoRepository.totalByVillageId(village.getVillageId());
                if (tTotal > 0) {
                    villageDTO.setTaskCount(tTotal);
                } else {
                    villageDTO.setTaskCount(0);
                }

//                MapInfo mapInfo = mapInfoRepository.findMapByVillageId(village.getVillageId());
//                if (mapInfo != null) {
//                    villageDTO.setMapImg(mapInfo.getMapSrc());
//                }
                List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(village.getVillageId());
                if (mapInfoList != null && mapInfoList.size() > 0) {
                    MapInfo mapInfo = mapInfoList.get(0);
                    if (mapInfo != null) {
                        villageDTO.setMapImg(mapInfo.getMapSrc());

                    }
                }
                villageDTOs.add(villageDTO);
            }
        }
        return villageDTOs;
    }

    @Override
    public ApiResult getVillageByItem(VillageDTO villageDTO) {
        Village village = new Village();
        village.setVillageId(villageDTO.getDistrictId());
        village.setUserName(villageDTO.getUser());
        int count = villageRepository.getCount(village);

//        villageDTO.setIndex(count);
        List<Village> villageList = villageRepository.getVillageByItem(village, villageDTO.getIndex());

        List<VillageDTO> villageDTOs = new ArrayList<VillageDTO>();
        if (villageList != null && villageList.size() > 0) {
            for (Village village1 : villageList) {
                VillageDTO villageDTO1 = new VillageDTO();
                villageDTO1.setDistrictId(village1.getVillageId());
                villageDTO1.setDistrictName(village1.getVillageName());
                villageDTO1.setAddress(village1.getAddress());
                villageDTO1.setUser(village1.getUserName());
                villageDTO1.setPhone(village1.getMobile());

                int rTotal = robotRepository.getRobotTotalByVillageId(village1.getVillageId());
                if (rTotal > 0) {
                    villageDTO1.setRobotCount(rTotal);
                } else {
                    villageDTO1.setRobotCount(0);
                }

                int sTotal = strategyRepository.getStrategyTotalByVillageId(village1.getVillageId());
                if (sTotal > 0) {
                    villageDTO1.setStrategyCount(sTotal);
                } else {
                    villageDTO1.setStrategyCount(0);
                }

                int tTotal = taskInfoRepository.totalByVillageId(village1.getVillageId());
                if (tTotal > 0) {
                    villageDTO1.setTaskCount(tTotal);
                } else {
                    villageDTO1.setTaskCount(0);
                }

//                MapInfo mapInfo = mapInfoRepository.findMapByVillageId(village1.getVillageId());
//                if (mapInfo != null) {
//                    villageDTO1.setMapImg(mapInfo.getMapSrc());
//                }
                List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(village1.getVillageId());
                if (mapInfoList != null && mapInfoList.size() > 0) {
                    MapInfo mapInfo = mapInfoList.get(0);
                    if (mapInfo != null) {
                        villageDTO1.setMapImg(mapInfo.getMapSrc());

                    }
                }
                villageDTOs.add(villageDTO1);
            }
        }

        ModelMap result = new ModelMap();
        result.addAttribute("villageList", villageDTOs);
        result.addAttribute("count", count);
        result.addAttribute("page", villageDTO.getIndex());
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult addVillage(Village village, UserInfo userInfo, String villageId) {
        ModelMap result = new ModelMap();
        if (villageRepository.addVillage(village)) {
            UserVillage userVillage = new UserVillage();
            userVillage.setId(IdGen.uuid());
            userVillage.setVillageId(village.getVillageId());
            userVillage.setUserId(userInfo.getuId());
            userVillageRepository.addUserVillage(userVillage);

            List<UserInfo> list = userRepository.getAdmin("000100000000");
            List<UserVillage> userVillages = new ArrayList<UserVillage>();
            for (UserInfo userInfo1 : list) {
                UserVillage userVillage1 = new UserVillage();
                userVillage1.setId(IdGen.uuid());
                userVillage1.setVillageId(village.getVillageId());
                userVillage1.setUserId(userInfo1.getuId());
                userVillages.add(userVillage1);
            }
            userVillageRepository.addUserVillage(userVillages);

            result.addAttribute("success", "添加成功");
            List<VillageDTO> villageDTOs = this.getVillageDtoList(villageId);
            result.addAttribute("villageList", villageDTOs);

        } else {
            result.addAttribute("error", "添加失败");
        }
        return new SuccessApiResult(result);

    }

    @Override
    public boolean deleteVillage(String vId) {
        if (villageRepository.deleteVillage(vId)) {
            List<UserVillage> userVillageList = villageAndUserRepository.getUserVillageByVillageId(vId);
            if (userVillageList != null && userVillageList.size() > 0) {
                for (UserVillage userVillage : userVillageList) {
                    villageAndUserRepository.deleteUserVillage(userVillage);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Village searchVillageById(String id) {
        Village village = villageRepository.searchVillageById(id);
        return village;
    }

    @Override
    public ApiResult updateVillage(Village village, String roleId) {
        ModelMap result = new ModelMap();
        if (villageRepository.updateVillage(village)) {
            result.addAttribute("success", "修改成功");
            if (roleId.equals("000100000000")) {
                List<VillageDTO> villageDTOs = this.getVillageDtoList("");
                result.addAttribute("villageList", villageDTOs);
            } else {
                List<VillageDTO> villageDTOs = this.getVillageDtoList(village.getVillageId());
                result.addAttribute("villageList", villageDTOs);
            }

        } else {
            result.addAttribute("error", "修改失败");
        }
        return new SuccessApiResult(result);
    }

    @Override
    public VillageDTO findVillageById(String villageId) {
        VillageDTO villageDTO = null;
        Village village = villageRepository.searchVillageById(villageId);
        if (village != null) {
            villageDTO = new VillageDTO();

            villageDTO.setDistrictId(village.getVillageId());
            villageDTO.setDistrictName(village.getVillageName());
            villageDTO.setAddress(village.getAddress());
            villageDTO.setUser(village.getUserName());
            villageDTO.setPhone(village.getMobile());
            int rTotal = robotRepository.getRobotTotalByVillageId(village.getVillageId());
            if (rTotal > 0) {
                villageDTO.setRobotCount(rTotal);
            } else {
                villageDTO.setRobotCount(0);
            }

            int sTotal = strategyRepository.getStrategyTotalByVillageId(village.getVillageId());
            if (sTotal > 0) {
                villageDTO.setStrategyCount(sTotal);
            } else {
                villageDTO.setStrategyCount(0);
            }

            int tTotal = taskInfoRepository.totalByVillageId(village.getVillageId());
            if (tTotal > 0) {
                villageDTO.setTaskCount(tTotal);
            } else {
                villageDTO.setTaskCount(0);
            }

//            MapInfo mapInfo = mapInfoRepository.findMapByVillageId(village.getVillageId());
//            if (mapInfo != null) {
//                villageDTO.setMapImg(mapInfo.getMapSrc());
//            }
            List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(village.getVillageId());
            if (mapInfoList != null && mapInfoList.size() > 0) {
                MapInfo mapInfo = mapInfoList.get(0);
                if (mapInfo != null) {
                    villageDTO.setMapImg(mapInfo.getMapSrc());
                }
            }
        }

        return villageDTO;
    }

    @Override
    public List<Village> searchVillageByUserId(String userId) {
        List<Village> villages = villageAndUserService.getSellerIdByUserId(userId);
        return villages;
    }
}
