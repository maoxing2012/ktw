package com.core.scpwms.server.service.user.impl;

import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.user.Addressee;
import com.core.scpwms.server.service.user.AddresseeManager;

/**
 * 
 * @description   收件人实现类 
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-7
 * @version        V1.0<br/>
 */
public class AddresseeManagerImpl extends DefaultBaseManager implements AddresseeManager {

    @Override
    public void delete(List<Long> ids) {
        if(!ids.isEmpty()){
            for(Long id : ids){
                delete(this.commonDao.load(Addressee.class, id));
            }
        }

    }

    @Override
    public void disable(List<Long> ids) {
        if(ids.size()>0 && ids != null){
            for(Long id : ids){
                Addressee addressee = commonDao.load(Addressee.class, id);
                addressee.setDisabled(Boolean.TRUE);
                commonDao.store(addressee);
            }
        }

    }

    @Override
    public void enable(List<Long> ids) {
        if(ids.size()>0 && ids != null){
            for(Long id : ids){
                Addressee addressee = commonDao.load(Addressee.class, id);
                addressee.setDisabled(Boolean.FALSE);
                commonDao.store(addressee);
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void store(Addressee addressee) {
//        if(addressee.isNew()){
//            //判断是否有重复记录
//            String hsql = "from Addressee a where a.code=:code";
//            List<Addressee> addresseeList = this.commonDao.findByQuery(hsql, 
//                    new String[]{"code"}, 
//                    new Object[]{addressee.getCode()});
//            
//            if(!addresseeList.isEmpty()){
//                throw new BusinessException ("ExistAddresseeCodeException");
//            }
//            InboundArea area = commonDao.load(InboundArea.class, addressee.getArea().getId());
//            if(area.getZipCodeStart()!=null && addressee.getDetails()!= null && addressee.getDetails().getPostcode()!=null && area.getZipCodeEnd()!=null){
//                if(!(area.getZipCodeStart()<=addressee.getDetails().getPostcode()&&addressee.getDetails().getPostcode()<=area.getZipCodeEnd())){
//                    throw new BusinessException ("AddresseeCodePostcodeException");
//                }
//            }
//            addressee.setCreateInfo(new CreateInfo(UserHolder.getUser()));
//            AddressDetail ad = new AddressDetail(
//                    addressee.getDetails().getAddress(),
//                    area.getCity(),
//                    area.getProvince(),
//                    addressee.getDetails().getPostcode(),
//                    area.getCountry());
//            addressee.setDetails(ad);
//        }else{
//            InboundArea area = commonDao.load(InboundArea.class, addressee.getArea().getId());
//            if (area.getZipCodeStart() != null && addressee.getDetails()!= null && addressee.getDetails().getPostcode() != null
//                    && area.getZipCodeEnd() != null) {
//                if(!(area.getZipCodeStart()<=addressee.getDetails().getPostcode()&&addressee.getDetails().getPostcode()<=area.getZipCodeEnd())){
//                    throw new BusinessException ("AddresseeCodePostcodeException");
//                }
//            }
//            addressee.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
//        }
//        this.commonDao.store(addressee);

    }

}
