package com.crm.basic.mapper.work;

import com.crm.basic.model.DictionaryConfigDto;
import com.crm.basic.model.CommonDictionaryCondition;
import com.crm.dto.basic.CommonDictionaryDto;
import org.apache.ibatis.annotations.Param;

import com.crm.dto.basic.CommonDictionaryDto;

public class DataDictionaryProvider {
	public String findCommonDictionaryVoList(CommonDictionaryCondition cond) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	dc.dictionary_name dictionaryName,");
		sql.append("	dc.dictionary_desc dictionaryDesc,");
		sql.append("	dc.status status");
		sql.append(" FROM");
		sql.append("	dictionary_config dc");
		sql.append(" WHERE 1 = 1");
		if (cond.getDictionaryName() != null && !"".equals(cond.getDictionaryName())) {
			sql.append(" AND UPPER(dc.dictionary_name) LIKE CONCAT('%',UPPER(#{dictionaryName}),'%')");
		}
		if (cond.getDictionaryDesc() != null && !"".equals(cond.getDictionaryDesc())) {
			sql.append(" AND dc.dictionary_desc LIKE CONCAT('%',#{dictionaryDesc},'%')");
		}
		if (cond.getAvailable() != null && !"".equals(cond.getAvailable())) {
			sql.append(" AND dc.status = #{available}");
		}

		sql.append(" ORDER BY");
		sql.append("	dc.dictionary_name");

		return sql.toString();
	}

	public String findCommonDictionaryList(@Param("dictionaryNames") String dictionaryNames,
			@Param("agencyId") Long agencyId) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	cd.dictionary_name dictionaryName,");
		sql.append("	cd.property_text propertyText,");
		sql.append("	cd.property_value propertyValue,");
		sql.append("	cd.sequence_num");
		sql.append(" FROM");
		sql.append("	common_dictionary cd");
		sql.append(" WHERE");
		sql.append("	UPPER(cd.dictionary_name) IN (UPPER(").append(dictionaryNames).append("))");
		sql.append(" AND cd.status = 'Y'");
		sql.append(" AND (cd.is_common = 'Y'");
		sql.append(" OR (cd.is_common = 'N'");
		sql.append(" AND cd.agency_id = #{agencyId})");
		sql.append(" )");
		// sql.append(" UNION");
		// sql.append(" SELECT");
		// sql.append(" cd.dictionary_name dictionaryName,");
		// sql.append(" cd.property_text propertyText,");
		// sql.append(" cd.property_value propertyValue,");
		// sql.append(" cd.sequence_num");
		// sql.append(" FROM");
		// sql.append(" common_dictionary cd,");
		// sql.append(" agency_dictionary ac");
		// sql.append(" WHERE");
		// sql.append(" UPPER(cd.dictionary_name) IN
		// (UPPER(").append(dictionaryNames).append("))");
		// sql.append(" AND cd.status = 'Y'");
		// sql.append(" AND cd.is_common = 'N'");
		// sql.append(" AND cd.dictionary_name = ac.dictionary_name");
		// sql.append(" AND cd.property_value = ac.property_value");
		sql.append(" ORDER BY");
		sql.append("	dictionaryName,");
		sql.append("	sequence_num");

		return sql.toString();
	}
	
	public String findCommonDictionary(@Param("dictionaryName") String dictionaryName,
			@Param("agencyId") Long agencyId) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	cd.dictionary_name dictionaryName,");
		sql.append("	cd.property_text propertyText,");
		sql.append("	cd.property_value propertyValue");
		sql.append(" FROM");
		sql.append("	common_dictionary cd");
		sql.append(" WHERE");
		sql.append("	UPPER(cd.dictionary_name) = (UPPER(#{dictionaryName}))");
		if (agencyId == null || "".equals(agencyId)) {
			sql.append(" AND cd.is_common = 'Y'");
		} else {
			sql.append(" AND (cd.is_common = 'Y'");
			sql.append(" OR (cd.is_common = 'N'");
			sql.append(" AND cd.agency_id = #{agencyId})");
			sql.append(" )");
		}

		return sql.toString();
	}

	public String findAllCommonDictionary() {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	cd.dictionary_name dictionaryName,");
		sql.append("	cd.property_text propertyText,");
		sql.append("	cd.property_value propertyValue,");
		sql.append("	cd.status,");
		sql.append("	cd.is_common isCommon,");
		sql.append("	cd.agency_id agencyId,");
		sql.append("	cd.sequence_num sequenceNum");
		sql.append(" FROM");
		sql.append("	common_dictionary cd");
		sql.append(" WHERE 1=1");
		sql.append(" ORDER BY");
		sql.append("	cd.dictionary_name,");
		sql.append("	cd.sequence_num");

		return sql.toString();
	}

	public String insertCommonDictionary(CommonDictionaryDto dictionary) {
		StringBuffer sql = new StringBuffer("INSERT INTO common_dictionary (");
		sql.append("	dictionary_name,");
		sql.append("	property_value,");
		sql.append("	property_text,");
		sql.append("	status,");
		sql.append("	sequence_num,");
		sql.append("	agency_id,");
		sql.append("	is_common");
		sql.append(")");
		sql.append(" VALUES");
		sql.append(
				"	(#{dictionaryName}, #{propertyValue}, #{propertyText}, #{status}, #{sequenceNum},#{agencyId}, #{isCommon})");

		return sql.toString();
	}

	public String updateCommonDictionary(CommonDictionaryDto dictionary) {
		StringBuffer sql = new StringBuffer("UPDATE common_dictionary cd");
		sql.append(" SET cd.status = #{status}");
		sql.append(" ,cd.dictionary_name = #{dictionaryName}");
		sql.append(" ,cd.property_value = #{propertyValue}");
		if (dictionary.getPropertyText() != null && !"".equals(dictionary.getPropertyText())) {
			sql.append(" ,cd.property_text = #{propertyText}");
		}
		if (dictionary.getSequenceNum() >= 0) {
			sql.append(" ,cd.sequence_num = #{sequenceNum}");
		}
		if (dictionary.getIsCommon() != null && !"".equals(dictionary.getIsCommon())) {
			sql.append(" ,cd.is_common = #{isCommon}");
		}
		if (dictionary.getAgencyId() != null && !"".equals(dictionary.getAgencyId())) {
			sql.append(" ,cd.agency_id = #{agencyId}");
		}

		sql.append(" WHERE");
		sql.append("	UPPER(cd.dictionary_name) = UPPER(#{dictionaryName})");
		sql.append("AND cd.property_value = #{propertyValue}");

		return sql.toString();
	}

	public String deleteCommonDictionary(@Param("dictionaryName") String dictionaryName) {
		StringBuffer sql = new StringBuffer("DELETE");
		sql.append(" FROM");
		sql.append("	common_dictionary");
		sql.append(" WHERE");
		sql.append("	UPPER(dictionary_name) = UPPER(#{dictionaryName})");

		return sql.toString();
	}

	public String findDictionaryConfigList() {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	dictionary_name dictionaryName,");
		sql.append("	dictionary_desc dictionaryDesc,");
		sql.append("	status");
		sql.append(" FROM");
		sql.append("	dictionary_config");

		return sql.toString();
	}

	public String insertDictionaryConfig(DictionaryConfigDto dto) {
		StringBuffer sql = new StringBuffer("INSERT INTO dictionary_config (");
		sql.append("	dictionary_name,");
		sql.append("	dictionary_desc,");
		sql.append("	STATUS");
		sql.append(")");
		sql.append(" VALUES");
		sql.append("	(#{dictionaryName},#{dictionaryDesc},#{status})");

		return sql.toString();
	}

	public String updateDictionaryConfig(DictionaryConfigDto dto) {
		StringBuffer sql = new StringBuffer("UPDATE dictionary_config");
		sql.append(" SET status = #{status}");
		if (dto.getDictionaryDesc() != null && !"".equals(dto.getDictionaryDesc())) {
			sql.append(",dictionary_desc = #{dictionaryDesc}");
		}
		sql.append(" WHERE");
		sql.append("	UPPER(dictionary_name) = UPPER(#{dictionaryName})");

		return sql.toString();
	}

//	public String insertAgencyDictionary(AgencyDictionaryDto dto) {
//		StringBuffer sql = new StringBuffer("INSERT INTO agency_dictionary (");
//		sql.append("	dictionary_name,");
//		sql.append("	agency_id,");
//		sql.append("	partition_no,");
//		sql.append("	property_value");
//		sql.append(")");
//		sql.append(" VALUES");
//		sql.append("	(#{dictionaryName},#{agencyId},#{partitionNo},#{propertyValue})");
//
//		return sql.toString();
//	}

//	public String deleteAgencyDictionary(AgencyDictionaryDto dto) {
//		StringBuffer sql = new StringBuffer("DELETE");
//		sql.append(" FROM");
//		sql.append("	agency_dictionary");
//		sql.append(" WHERE");
//		sql.append("	UPPER(dictionary_name) = UPPER(#{dictionaryName})");
//		sql.append("	AND property_value = #{propertyValue}");
//		if (dto.getPartitionNo() != null && !"".equals(dto.getPartitionNo())) {
//			sql.append("	AND partition_no = #{partitionNo}");
//		}
//
//		return sql.toString();
//	}

//	public String findAgencyAllDictionaryList(@Param("agencyId") Long agencyId) {
//		StringBuffer sql = new StringBuffer("SELECT");
//		sql.append("	dictionary_name dictionaryName,");
//		sql.append("	agency_id agencyId,");
//		sql.append("	property_value propertyValue");
//		sql.append(" FROM");
//		sql.append("	agency_dictionary");
//		sql.append(" WHERE");
//		sql.append("	agency_id = #{agencyId}");
//
//		return sql.toString();
//	}

//	public String findAgencyDictionaryList(@Param("agencyId") Long agencyId,
//			@Param("dictionaryName") String dictionaryName) {
//		StringBuffer sql = new StringBuffer("SELECT");
//		sql.append("	dictionary_name dictionaryName,");
//		sql.append("	agency_id agencyId,");
//		sql.append("	property_value propertyValue");
//		sql.append(" FROM");
//		sql.append("	agency_dictionary");
//		sql.append(" WHERE");
//		sql.append("	agency_id = #{agencyId}");
//		sql.append("	AND dictionary_name = #{dictionaryName}");
//
//		return sql.toString();
//	}

//	public String findAgencyIdList(@Param("dictionaryName") String dictionaryName,
//			@Param("propertyValue") String propertyValue) {
//		StringBuffer sql = new StringBuffer("SELECT");
//		sql.append("	agency_id agencyId");
//		sql.append(" FROM");
//		sql.append("	agency_dictionary");
//		sql.append(" WHERE");
//		sql.append("	dictionary_name = #{dictionaryName}");
//		sql.append("	AND property_value = #{propertyValue}");
//
//		return sql.toString();
//	}

	public String findMaxPropertyValue(@Param("dictionaryName") String dictionaryName,
			@Param("propertyValue") String propertyValue) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	max(cd.property_value)");
		sql.append(" FROM");
		sql.append("	common_dictionary cd");
		sql.append(" WHERE");
		sql.append("	cd.dictionary_name = #{dictionaryName}");
		sql.append(" AND cd.property_value LIKE CONCAT(#{propertyValue},'-%')");

		return sql.toString();
	}
}
