package com.anxi.activiti.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LJ on 2018/5/29
 */
@Slf4j
public class MyBlobTypeHandler extends BaseTypeHandler<String> {

    // 指定字符集
    private static final String DEFAULT_CHARSET = "utf-8";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ByteArrayInputStream bis;
        try {
            // 把String转化成byte流
            bis = new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        ps.setBinaryStream(i, bis, parameter.length());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.bytesToStr(rs.getBytes(columnName));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.bytesToStr(cs.getBytes(columnIndex));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnName) throws SQLException {
        return this.bytesToStr(rs.getBytes(columnName));
    }

    private String bytesToStr(byte[] bytes){
        String result = null;
        try {
            if (null != bytes) {
                // 把byte转化成string
                result = new String(bytes, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        return result;
    }
}
