package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Entity.VisitTableEntity;
import com.ruoyi.mapper.VisitTableMapper;
import com.ruoyi.service.VisitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15500
* &#064;description  针对表【visit_table(就诊人信息表)】的数据库操作Service实现
* &#064;createDate  2023-01-08 15:26:59
 */
@Service
public class VisitTableServiceImpl extends ServiceImpl<VisitTableMapper, VisitTableEntity>
implements VisitTableService {
    @Autowired
    private VisitTableMapper visitTableMapper;

    /**
     * 新增就诊人信息
     * @param visitTableEntity
     * @return
     */
    @Override
    public VisitTableEntity insertvisit(VisitTableEntity visitTableEntity){
        if(visitTableEntity.getId()==0){
            visitTableEntity.setF1("1");
            visitTableMapper.insert(visitTableEntity);
        }else {
            List<VisitTableEntity> list=visitTableMapper.selectList( new QueryWrapper<VisitTableEntity>()
                    .eq("user_id",visitTableEntity.getUserId()));
            for (int i=0;i<list.size();i++){
                list.get(i).setF1("1");
                visitTableMapper.updateById(list.get(i));
            }
            visitTableEntity.setF1("0");
            visitTableMapper.updateById(visitTableEntity);

        }
        return visitTableEntity;

    }

    /**
     * 删除就诊人信息
     * @param id
     * @return
     */
    @Override
    public int removevisit(String id){
        return visitTableMapper.deleteById(id);
    }

    /**
     * 查询列表
     */
    @Override
    public List<VisitTableEntity> selectvisitList(VisitTableEntity visitTableEntity)
    {
        return visitTableMapper.selectVisitTableList(visitTableEntity);
    }

    /**
     * 通过id获取就诊人详情
     * @param id
     * @return
     */
    @Override
    public VisitTableEntity selectByid(String id){
        return visitTableMapper.selectById(id);
    }
}
