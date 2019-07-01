package com.crm.basic.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.vo.basic.DataDictionaryVo;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.basic.mapper.work.DataDictionaryMapper;
import com.crm.basic.mapper.work.PropertyConfigMapper;
import com.crm.basic.model.DictionaryConfigDto;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存数据类
 *
 * @author Administrator
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class CacheDataService {
    public static final Logger log = Logger.getLogger(CacheDataService.class);
    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    private PropertyConfigMapper propertyConfigMapper;

    /**
     * 缓存所有数据字典
     *
     * @return
     */
    @Cacheable(value = "CommonDictronary", key = "'ALL_COMMON_DICTRONARY'")
    public Map<String, Map<String, CommonDictionaryDto>> findAllCommonDictionary() {
        Map<String, Map<String, CommonDictionaryDto>> map = new LinkedHashMap<>();
        List<CommonDictionaryDto> list = dataDictionaryMapper.findAllCommonDictionary();
        if (list != null && list.size() > 0) {
            for (CommonDictionaryDto cd : list) {
                if (!map.containsKey(cd.getDictionaryName())) {
                    Map<String, CommonDictionaryDto> voMap = new LinkedHashMap<>();
                    voMap.put(cd.getPropertyValue(), cd);
                    map.put(cd.getDictionaryName(), voMap);
                } else {
                    Map<String, CommonDictionaryDto> voMap = map.get(cd.getDictionaryName());
                    voMap.put(cd.getPropertyValue(), cd);
                }
            }
        }
        return map;
    }

    /**
     * 缓存数据字典名称信息
     *
     * @return
     */
    @Cacheable(value = "DictionaryConfig", key = "'ALL_DICTIONARY_CONFIG'")
    public List<DictionaryConfigDto> findDictionaryConfigList() {
        return dataDictionaryMapper.findDictionaryConfigList();
    }

    /**
     * 缓存经销商所特有的数据字典下拉数据
     *
     * @param agencyId
     * @return
     */
    // @Cacheable(value = "AgencyAllDictionary", key = "'AGENCY_ALL_DICTIONARY' +
    // #agencyId")
    // public List<AgencyDictionaryDto> findAgencyAllDictionaryList(Long agencyId)
    // {
    // return dataDictionaryMapper.findAgencyAllDictionaryList(agencyId);
    // }

    /**
     * 缓存所有动态配置表数据
     *
     * @return
     */
    @Cacheable(value = "PropertyConfig", key = "'ALL_PROPERTY_CONFIG' + #agencyId")
    public List<PropertyConfigVo> findPropertyConfigList(Long agencyId) {
        return propertyConfigMapper.findPropertyConfigList(agencyId);
    }

    /**
     * 缓存所有固定配置表数据
     *
     * @return
     */
    @Cacheable(value = "PropertyConfig", key = "'ALL_SYSTEMPROPERTY_CONFIG'")
    public List<PropertyConfigVo> findSystemPropertyConfigList() {
        return propertyConfigMapper.findSystemPropertyConfigList();
    }

    /**
     * 缓存所有动态配置表对应的下拉框数据
     *
     * @return
     */
    @Cacheable(value = "DataDisctionary", key = "'ALL_DATA_DISCTIONARY' + #agencyId")
    public List<DataDictionaryVo> findDataDictionaryList(Long agencyId) {
        return propertyConfigMapper.findDataDictionaryList(agencyId);
    }


    /**
     * 清除数据字典缓存
     */
    @CacheEvict(value = "CommonDictronary", key = "'ALL_COMMON_DICTRONARY'")
    public void cacheEvictCommonDictionary() {
    }

    /**
     * 清除数据字典名称缓存
     */
    @CacheEvict(value = "DictionaryConfig", key = "'ALL_DICTIONARY_CONFIG'")
    public void cacheEvictDictionaryConfig() {
    }

    /**
     * 清除经销商字典缓存
     */
    // @CacheEvict(value = "AgencyAllDictionary", key = "'AGENCY_ALL_DICTIONARY' +
    // #agencyId")
    // public void cacheEvictAgencyAllDictionary(Long agencyId) {
    // }

    /**
     * 清除所有经销商下的动态配置缓存数据(厂家同步数据时调用)
     */
    @CacheEvict(value = "PropertyConfig", allEntries = true)
    public void cacheEvictAllPropertyConfig() {
    }

    /**
     * 清除所有经销商下的动态配置的下拉框数据(厂家同步数据时调用)
     */
    @CacheEvict(value = "DataDisctionary", allEntries = true)
    public void cacheEvictAllDataDisctionary() {
    }

    /**
     * 清除所有经销商下的固定配置缓存数据(厂家同步数据时调用)
     */
    @CacheEvict(value = "PropertyConfig", key = "'ALL_SYSTEMPROPERTY_CONFIG'")
    public void cacheEvictFixedPropertyConfig() {
    }

    /**
     * 清除动态配置缓存数据
     */
    @CacheEvict(value = "PropertyConfig", key = "'ALL_PROPERTY_CONFIG' + #agencyId")
    public void cacheEvictPropertyConfig(Long agencyId) {
    }

    /**
     * 清除动态配置的下拉框数据
     */
    @CacheEvict(value = "DataDisctionary", key = "'ALL_DATA_DISCTIONARY' + #agencyId")
    public void cacheEvictDataDisctionary(Long agencyId) {
    }

}
