package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Role;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role row);

    int insertSelective(Role row);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role row);

    int updateByPrimaryKey(Role row);
}