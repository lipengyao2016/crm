package com.crm.basic.mapper.work;

import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import org.apache.ibatis.annotations.Param;

import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;

public class PropertyConfigProvider {
	public String findPropertyConfigList(@Param("partitionNo") Long partitionNo) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	pc.property_config_seq_id propertyConfigSeqId,");
		sql.append("	pc.property_name propertyName,");
		sql.append("	pc.property_desc propertyDesc,");
		sql.append("	pc.property_type propertyType,");
		sql.append("	pc.permission_id permissionId,");
		sql.append("	pc.input_col inputCol,");
		sql.append("	pc.is_necessary isNecessary,");
		sql.append("	pc.point_out pointOut,");
		sql.append("	pc.sequence_num sequenceNum,");
		sql.append("	pc.property_unit propertyUnit,");
		sql.append("	pc.permission_type permissionType,");
		sql.append("	pc.status,");
		sql.append("	pc.if_forbidden ifForbidden,");
		sql.append(" pc.text_max_length textMaxLength,");
		sql.append(" pc.text_min_length textMinLength,");
		sql.append(" pc.text_row textRow");
		sql.append(" FROM");
		sql.append("	property_config pc");
		sql.append(" WHERE");
		if (partitionNo != null && !"".equals(partitionNo)) {
			sql.append(" (pc.partition_no = #{partitionNo} OR (pc.partition_no is null or pc.partition_no = 0))");
		} else {
			sql.append(" pc.partition_no is null or pc.partition_no = 0");
		}

		return sql.toString();
	}

	public String findDataDictionaryList(@Param("partitionNo") Long partitionNo) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	dd.data_dictionary_seq_id dataDictionarySeqId,");
		sql.append("	dd.data_name dataName,");
		sql.append("	dd.data_value dataValue,");
		sql.append("	dd.property_config_seq_id propertyConfigSeqId,");
		sql.append("	dd.sequence_num sequenceNum,");
		sql.append("	dd.status,");
		sql.append("	dd.agency_id agencyId");
		sql.append(" FROM");
		sql.append("	data_dictionary dd");
		sql.append(" WHERE");
		if (partitionNo != null && !"".equals(partitionNo)) {
			sql.append(" (dd.partition_no = #{partitionNo} or dd.partition_no is null)");
		} else {
			sql.append(" dd.partition_no is null");
		}

		return sql.toString();
	}

	public String insertPropertyConfig(PropertyConfigDto dto) {
		StringBuffer sql = new StringBuffer("INSERT INTO property_config (");
		if (dto.getPermissionType() != null && !"".equals(dto.getPermissionType())) {
			sql.append("	permission_type,");
		}
		if (dto.getPermissionId() != null && !"".equals(dto.getPermissionId())) {
			sql.append("	permission_id,");
		}
		sql.append("	property_name,");
		sql.append("	property_desc,");
		sql.append("	property_type,");
		if (dto.getPropertyUnit() != null && !"".equals(dto.getPropertyUnit())) {
			sql.append("	property_unit,");
		}
		if (dto.getPointOut() != null && !"".equals(dto.getPointOut())) {
			sql.append("	point_out,");
		}
		if (dto.getTextMinLength() != null && dto.getTextMinLength() > 0) {
			sql.append("	text_min_length,");
		}
		if (dto.getTextMaxLength() != null && dto.getTextMaxLength() > 0) {
			sql.append("	text_max_length,");
		}
		if (dto.getTextRow() != null && dto.getTextRow() > 0) {
			sql.append("	text_row,");
		}
		if (dto.getInputCol() != null && dto.getInputCol() > 0) {
			sql.append("	input_col,");
		}
		if (dto.getIfForbidden() != null && !"".equals(dto.getIfForbidden())) {
			sql.append("	if_forbidden,");
		}
		if (dto.getIsNecessary() != null && !"".equals(dto.getIsNecessary())) {
			sql.append("	is_necessary,");
		}
		if (dto.getStatus() != null && !"".equals(dto.getStatus())) {
			sql.append("	status,");
		}
		if (dto.getPartitionNo() != null && !"".equals(dto.getPartitionNo())) {
			sql.append("	`partition_no`,");
		}
		sql.append("	sequence_num");
		sql.append(")");
		sql.append(" VALUES");
		sql.append("	(");
		if (dto.getPermissionType() != null && !"".equals(dto.getPermissionType())) {
			sql.append("#{permissionType},");
		}
		if (dto.getPermissionId() != null && !"".equals(dto.getPermissionId())) {
			sql.append("#{permissionId}, ");
		}
		sql.append("#{propertyName}, ");
		sql.append("#{propertyDesc}, ");
		sql.append("#{propertyType}, ");
		if (dto.getPropertyUnit() != null && !"".equals(dto.getPropertyUnit())) {
			sql.append("#{propertyUnit}, ");
		}
		if (dto.getPointOut() != null && !"".equals(dto.getPointOut())) {
			sql.append("#{pointOut}, ");
		}
		if (dto.getTextMinLength() != null && dto.getTextMinLength() > 0) {
			sql.append("#{textMinLength}, ");
		}
		if (dto.getTextMaxLength() != null && dto.getTextMaxLength() > 0) {
			sql.append("#{textMaxLength}, ");
		}
		if (dto.getTextRow() != null && dto.getTextRow() > 0) {
			sql.append("#{textRow}, ");
		}
		if (dto.getInputCol() != null && dto.getInputCol() > 0) {
			sql.append("#{inputCol}, ");
		}
		if (dto.getIfForbidden() != null && !"".equals(dto.getIfForbidden())) {
			sql.append("#{ifForbidden}, ");
		}
		if (dto.getIsNecessary() != null && !"".equals(dto.getIsNecessary())) {
			sql.append("#{isNecessary}, ");
		}
		if (dto.getStatus() != null && !"".equals(dto.getStatus())) {
			sql.append("#{status}, ");
		}
		if (dto.getPartitionNo() != null && !"".equals(dto.getPartitionNo())) {
			sql.append("#{partitionNo}, ");
		}
		sql.append("#{sequenceNum})");
		return sql.toString();
	}

	public String insertDataDictionary(DataDictionaryDto dto) {
		StringBuffer sql = new StringBuffer("INSERT INTO data_dictionary (");
		sql.append("	property_config_seq_id,");
		sql.append("	data_name,");
		sql.append("	data_value,");
		sql.append("	status,");
		sql.append("	sequence_num,");
		sql.append("	partition_no");
		sql.append(")");
		sql.append(" VALUES");
		sql.append("	(");
		sql.append("		#{propertyConfigSeqId}, ");
		sql.append("		#{dataName}, #{dataValue}, #{status}, #{sequenceNum}, #{partitionNo}");
		sql.append("	)");

		return sql.toString();
	}

	public String updatePropertyConfig(PropertyConfigDto dto) {
		StringBuffer sql = new StringBuffer("UPDATE property_config pc");
		sql.append(" SET pc.status = #{status}");
		if (dto.getPermissionType() != null && !"".equals(dto.getPermissionType())) {
			sql.append(",pc.permission_type = #{permissionType}");
		}
		if (dto.getPermissionId() != null && !"".equals(dto.getPermissionId())) {
			sql.append(",pc.permission_id = #{permissionId}");
		}
		if (dto.getPropertyName() != null && !"".equals(dto.getPropertyName())) {
			sql.append(",pc.property_name =#{propertyName}");
		}
		if (dto.getPropertyDesc() != null && !"".equals(dto.getPropertyDesc())) {
			sql.append(",pc.property_desc =#{propertyDesc}");
		}
		if (dto.getPropertyType() != null && !"".equals(dto.getPropertyType())) {
			sql.append(",pc.property_type = #{propertyType}");
		}
		if (dto.getPropertyUnit() != null && !"".equals(dto.getPropertyUnit())) {
			sql.append(",pc.property_unit = #{propertyUnit}");
		}
		if (dto.getSequenceNum() != null && 0 != dto.getSequenceNum()) {
			sql.append(",pc.sequence_num = #{sequenceNum}");
		}
		if (dto.getPointOut() != null && !"".equals(dto.getPointOut())) {
			sql.append(",pc.point_out = #{pointOut}");
		}
		if (dto.getTextMinLength() != null && dto.getTextMinLength() > 0) {
			sql.append(",pc.text_min_length = #{textMinLength}");
		}
		if (dto.getTextMaxLength() != null && dto.getTextMaxLength() > 0) {
			sql.append(",pc.text_max_length = #{textMaxLength}");
		}
		if (dto.getTextRow() != null && dto.getTextRow() > 0) {
			sql.append(",pc.text_row = #{textRow}");
		}
		if (dto.getInputCol() != null && dto.getInputCol() > 0) {
			sql.append(",pc.input_col = #{inputCol}");
		}
		if (dto.getIfForbidden() != null && !"".equals(dto.getIfForbidden())) {
			sql.append(",pc.if_forbidden = #{ifForbidden}");
		}
		if (dto.getIsNecessary() != null && !"".equals(dto.getIsNecessary())) {
			sql.append(",pc.is_necessary = #{isNecessary}");
		}
		if (dto.getSequenceNum() != null && dto.getSequenceNum() > 0) {
			sql.append(",pc.status = #{status}");
		}
		if (dto.getPartitionNo() != null && !"".equals(dto.getPartitionNo())) {
			sql.append(",pc.`partition_no` = #{partitionNo}");
		}
		sql.append(" WHERE");
		sql.append("	pc.property_config_seq_id = #{propertyConfigSeqId}");

		return sql.toString();
	}

	public String updateDataDictionary(DataDictionaryDto dto) {
		StringBuffer sql = new StringBuffer("UPDATE data_dictionary dd");
		sql.append("SET dd.status = #{status}");
		if (dto.getDataName() != null && !"".equals(dto.getDataName())) {
			sql.append(",dd.data_name = {dataName}");
		}
		if (dto.getDataValue() != null && !"".equals(dto.getDataValue())) {
			sql.append(",dd.data_value = {dataValue}");
		}
		if (dto.getSequenceNum() != null && dto.getSequenceNum() > 0) {
			sql.append(",dd.sequenceNum = {sequenceNum}");
		}
		sql.append(" WHERE");
		sql.append("	dd.data_dictionary_id = #{dataDictionaryId}");

		return sql.toString();
	}

	public String findMaxPropertyName(@Param("propertyName") String propertyName) {
		StringBuffer sql = new StringBuffer("SELECT");
		sql.append("	max(c.property_name)");
		sql.append(" FROM");
		sql.append("	property_config c");
		sql.append(" WHERE");
		sql.append("	c.property_name LIKE CONCAT(#{propertyName},'-%')");

		return sql.toString();
	}
}
