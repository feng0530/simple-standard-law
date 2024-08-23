package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Func;

@Mapper
public interface FuncMapper {
    int deleteByPrimaryKey(Integer funcId);

    int insert(Func row);

    int insertSelective(Func row);

    Func selectByPrimaryKey(Integer funcId);

    int updateByPrimaryKeySelective(Func row);

    int updateByPrimaryKey(Func row);
}