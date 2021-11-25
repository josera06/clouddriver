package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CredentialsMapper {

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username},key=#{key}, #{password},userid=#{userid} WHERE credentialId = #{credentialId}")
    int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredentials(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credentials getCredentials(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> getAllCredentials();

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url}, #{username},#{key},#{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredentials(Credentials credentials);
}
