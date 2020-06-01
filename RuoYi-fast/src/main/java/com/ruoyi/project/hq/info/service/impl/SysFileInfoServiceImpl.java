package com.ruoyi.project.hq.info.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.hq.info.mapper.SysFileInfoMapper;
import com.ruoyi.project.hq.info.domain.SysFileInfo;
import com.ruoyi.project.hq.info.service.ISysFileInfoService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 文件信息Service业务层处理
 * 
 * @author lty
 * @date 2020-03-24
 */
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService 
{
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    @Override
    public SysFileInfo selectSysFileInfoById(Long fileId)
    {
        return sysFileInfoMapper.selectSysFileInfoById(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.updateSysFileInfo(sysFileInfo);
    }

    /**
     * 删除文件信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByIds(String ids)
    {
        return sysFileInfoMapper.deleteSysFileInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoById(Long fileId)
    {
        return sysFileInfoMapper.deleteSysFileInfoById(fileId);
    }
}
