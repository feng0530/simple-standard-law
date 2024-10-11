package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.FuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Func;
import tw.idv.frank.simple_standard_law.schema.system.service.FuncService;

import java.util.List;

@Service
public class FuncServiceImpl implements FuncService {

    @Autowired
    private FuncMapper funcMapper;

    @Override
    public List<Func> findFuncList() {
        return funcMapper.findFuncList();
    }
}
