package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersFunc;

import java.util.List;

@Mapper
public interface RoleFuncMapper {
    List<UsersFunc> findUsersFuncByUsersId(Integer usersId);
}