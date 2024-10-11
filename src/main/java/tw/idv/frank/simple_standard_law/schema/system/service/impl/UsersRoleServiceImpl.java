package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.frank.simple_standard_law.common.service.RedisService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersRoleMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleRes;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersRoleService;


@Service
public class UsersRoleServiceImpl implements UsersRoleService {

    @Autowired
    private UsersRoleMapper usersRoleMapper;

    @Autowired
    private RedisService redisService;

    @Transactional
    @Override
    public void updateUsersRole(UsersRoleReq req) {
        usersRoleMapper.deleteByUserId(req.getUserId());
        usersRoleMapper.addUsersRole(req);
        redisService.deleteJwtInOnlineList(String.valueOf(req.getUserId()));
    }

    @Override
    public UsersRoleRes findUsersRoleByUserId(Integer userId) {
        // 待修正回傳值 = null
        // 拿userId查出user資訊，映射到UsersRoleRes
        return usersRoleMapper.findUsersRoleByUserId(userId);
    }
}
