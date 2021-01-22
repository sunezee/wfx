package com.blb.wfx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blb.wfx.dao.SysModuleMapper;
import com.blb.wfx.entity.ModuleTreeNodes;
import com.blb.wfx.entity.SysModule;
import com.blb.wfx.service.SysModuleService;
import com.blb.wfx.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysModuleServiceImpl extends ServiceImpl<SysModuleMapper, SysModule> implements SysModuleService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    /**
     * 找到每个菜单（url为空）下的子菜单
     * @param nodes
     */
    public void findChildMudule(List<ModuleTreeNodes> nodes){
        if(nodes!=null && nodes.size()>0){
            for (ModuleTreeNodes node:nodes
                 ) {
                //如果节点的url为空，并且没有子菜单
                if(StringUtils.isEmpty(node.getHref())&&
                        (node.getNodes()==null||node.getNodes().size()==0)){
                    //查询菜单
                    List<ModuleTreeNodes> nodes1 = sysModuleMapper.selectModuleTreeNodesByModuleName(node.getText());
                    if(nodes1!=null && nodes1.size()>0){
                        node.setNodes(nodes1.get(0).getNodes());
                    }
                }
                //递归
                findChildMudule(node.getNodes());
            }
        }

    }

    @Override
    public List<ModuleTreeNodes> getModuleTreeNodesByAccount(String account) {
        List<ModuleTreeNodes> nodes = sysModuleMapper.selectModuleTreeNodesByAccount(account);
        findChildMudule(nodes);
        return nodes;
    }

    @Override
    public List<ModuleTreeNodes> getModuleTreeNodesByRoleName(String roleName) {
        return sysModuleMapper.selectModuleTreeNodesByRoleName(roleName);
    }

    @Override
    public List<ModuleTreeNodes> getThree() {
        return sysModuleMapper.selectThree();
    }

    @Override
    @Transactional//事物管理，出错全部回调
    public int addModule(SysModule sysModule) {
        //判断菜单名称是否重复
        SysModule module1 = sysModuleMapper.selectOne(new QueryWrapper<SysModule>().eq("module_name", sysModule.getModuleName()));
        if(module1!=null){
            return 0;
        }
        //随机生成8位数字的菜单编码并判断菜单编码是否重复
        boolean flag=true;
        String moduleId="";
        do{
            moduleId= PublicUtil.getRondomChangeFirst(8);
            SysModule module2= sysModuleMapper.selectOne(new QueryWrapper<SysModule>().eq("module_id", moduleId));
            if(module2==null){
                flag=false;
            }
        }while (flag);
        sysModule.setModuleId(moduleId);
        //根据module_name字段,设置父类code
        SysModule module3 = sysModuleMapper.selectOne(new QueryWrapper<SysModule>().eq("module_name", sysModule.getParentModule()));
        sysModule.setParentModule(module3.getModuleCode());
        //根据父类code查询出所有子类code
        List<SysModule> sysModules = sysModuleMapper.selectList(new QueryWrapper<SysModule>().eq("parent_module", module3.getModuleCode())
                .likeLeft("module_code", module3.getModuleCode() + '_' + '_'));
        //将编码的后两位转换成数字
        List<Integer> ilist=new ArrayList<>();
        for (SysModule s:sysModules
             ) {
            String num="";
            String p = s.getModuleCode();
            String i1 = p.substring(p.length() - 2, p.length() - 1);
            String i2 = p.substring(p.length()-1,p.length());
            if(i1.equals("0")){
                num=i2;
            }else{
                num=i1+i2;
            }
            int i = Integer.parseInt(num);
            ilist.add(i);
        }
        int max=ilist.get(0);
        for (int i = 1; i <ilist.size() ; i++) {
            if(max<ilist.get(i)){
                max=ilist.get(i);
            }
        }
        int nmax=max+1;
        String module_code="";
        if(nmax>99){
            return 0;
        }
        if(nmax<=9){
            module_code=module3.getModuleCode()+'0'+String.valueOf(nmax);
        }else{
            module_code=module3.getModuleCode()+String.valueOf(nmax);
        }
        //设置module_code
        sysModule.setModuleCode(module_code);
        sysModuleMapper.insert(sysModule);

        return 1;
    }


}
