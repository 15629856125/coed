package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.ScoreEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.mapper.DiseaseTableMapper;
import com.ruoyi.mapper.ScoreMapper;
import com.ruoyi.service.ScoreService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* @author 15500
* @description 针对表【score】的数据库操作Service实现
* @createDate 2023-01-24 17:47:38
*/
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, ScoreEntity>
implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private DiseaseTableMapper diseaseTableMapper;


    /**
     * 新增评价
     * @param scoreEntity
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    public int insertScore(ScoreEntity scoreEntity) throws Exception {
       try{
              int number=0;
              scoreEntity.setCreateTime(new Date());
              int res= scoreMapper.insert(scoreEntity);
              if(res!=1){
                  throw new Exception("添加失败");
              }
              //修改病情单评价状态改为已评价
              DiseaseTableEntity diseaseTableEntity=diseaseTableMapper.selectById(scoreEntity.getDiseaseId());
              diseaseTableEntity.setScoreStatus("1");
              diseaseTableMapper.updateById(diseaseTableEntity);
              //新增时，通过id查询用户
              SysUser sysUser=userMapper.selectUserById(Long.valueOf(scoreEntity.getUserId()));
              List<ScoreEntity> list=scoreMapper.selectList(new QueryWrapper<ScoreEntity>().eq("user_id",scoreEntity.getUserId()));
              for(int i=0;i< list.size();i++){
                  number+=list.get(i).getFraction();
              }
              number=number/ list.size();
              sysUser.setScore(number);
              return userMapper.updateUser(sysUser);
       }catch (Exception e){
           e.printStackTrace();
           throw e;
       }
    }

    /**
     * 修改评价
     * @param scoreEntity
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    public int updateScore(ScoreEntity scoreEntity) throws Exception {
        try{
            int number=0;
            scoreMapper.updateById(scoreEntity);
            SysUser sysUser=userMapper.selectUserById(Long.valueOf(scoreEntity.getUserId()));
            List<ScoreEntity> list=scoreMapper.selectList(new QueryWrapper<ScoreEntity>().eq("user_id",scoreEntity.getUserId()));
            for(int i=0;i< list.size();i++){
                number+=list.get(i).getFraction();
            }
            number=number/ list.size();
            sysUser.setScore(number);
            return userMapper.updateUser(sysUser);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取评价的列表
     * @param scoreEntity
     * @return
     */
    @Override
    public List<ScoreEntity> selectScoreList(ScoreEntity scoreEntity){
        return scoreMapper.selectScoreList(scoreEntity);
    }

    /**
     * 删除评价
     * @param id
     * @return
     */
    @Override
    public int deleteScore(String id){
        int number=0;
        ScoreEntity scoreEntity=scoreMapper.selectById(id);
        scoreMapper.deleteById(id);
        SysUser sysUser=userMapper.selectUserById(Long.valueOf(scoreEntity.getUserId()));
        List<ScoreEntity> list=scoreMapper.selectList(new QueryWrapper<ScoreEntity>().eq("user_id",scoreEntity.getUserId()));
        for(int i=0;i< list.size();i++){
            number+=list.get(i).getFraction();
        }
        number=number/ list.size();
        sysUser.setScore(number);
        return userMapper.updateUser(sysUser);
    }
}
