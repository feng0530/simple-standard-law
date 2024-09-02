package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleFuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncRes;
import tw.idv.frank.simple_standard_law.schema.system.service.RoleFuncService;

import java.util.List;

@Service
public class RoleFuncServiceImpl implements RoleFuncService {

    @Autowired
    private RoleFuncMapper roleFuncMapper;

    @Transactional
    @Override
    public void updateRoleFunc(RoleFuncAddReq req) {
        roleFuncMapper.deleteByRoleId(req.getRoleId());
        roleFuncMapper.addRoleFunc(req);
    }

    @Override
    public List<RoleFuncRes> findRoleFuncByRoleId(Integer roleId) {
        return roleFuncMapper.findRoleFuncByRoleId(roleId);
    }
}
