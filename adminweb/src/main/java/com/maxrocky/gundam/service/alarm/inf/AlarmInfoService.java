package com.maxrocky.gundam.service.alarm.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.alarm.dto.AlarmInfoDto;
import com.maxrocky.gundam.domain.alarm.dto.ExportExcelDto;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface AlarmInfoService {
    /**
     * 检索报警信息
     *
     * @return
     */
    public ApiResult getAlarmInfoList();

    /**
     * 按条件检索报警信息
     *
     * @param alarmInfoDto
     * @return
     */
    public ApiResult getAlarmInfoList(AlarmInfoDto alarmInfoDto);

    /**
     * 检索需要 导出报警信息 EXCEL
     *
     * @param alarmInfoDto
     * @return
     */
    public List<ExportExcelDto> exportAlarmInfo(AlarmInfoDto alarmInfoDto);


    /**
     * 导出Excel
     *
     * @param title        表格标题名
     * @param headers      表格属性列名数组
     * @param alarmInfoDto 检索的条件
     * @param out          与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @throws Exception
     */
    public void exportExcel(String title, String[] headers, AlarmInfoDto alarmInfoDto, OutputStream out) throws Exception;

}
