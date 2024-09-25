package kopo.poly.service;

import kopo.poly.dto.UserDTO;

public interface IUserService {

    UserDTO getUserIDEmailExists(UserDTO pDTO) throws Exception;

    int insertUserInfo2(UserDTO pDTO) throws Exception;
}
