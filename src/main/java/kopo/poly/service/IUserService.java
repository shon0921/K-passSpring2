package kopo.poly.service;

import kopo.poly.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserService {

    UserDTO getUserIDEmailExists(UserDTO pDTO) throws Exception;

    // 이메일
    UserDTO getUserEmailExists(UserDTO pDTO) throws Exception;

    int insertUserInfo2(UserDTO pDTO) throws Exception;
}
