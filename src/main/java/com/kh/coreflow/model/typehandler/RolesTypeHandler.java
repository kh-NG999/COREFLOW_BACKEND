package com.kh.coreflow.model.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(List.class)
public class RolesTypeHandler extends BaseTypeHandler<List<String>>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, String.join(",", parameter));
		
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String roles = rs.getString(columnName);
        return parseRoles(roles);
	}

	private List<String> parseRoles(String roles) {
		if (roles == null || roles.isEmpty()) return null;
        return Arrays.stream(roles.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String roles = rs.getString(columnIndex);
        return parseRoles(roles);
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String roles = cs.getString(columnIndex);
        return parseRoles(roles);
	}

}
