package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.UsersRole;

@Mapper
public interface UsersRoleMapper {
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UsersRole row);

    int insertSelective(UsersRole row);

    UsersRole selectByPrimaryKey(Integer userRoleId);

    int updateByPrimaryKeySelective(UsersRole row);

    int updateByPrimaryKey(UsersRole row);
}