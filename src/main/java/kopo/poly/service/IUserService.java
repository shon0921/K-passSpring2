package kopo.poly.service;

import kopo.poly.dto.UserDTO;
import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserService {

    // 아이디 (오타난거 고쳐야함)
    UserDTO getUserIDEmailExists(UserDTO pDTO) throws Exception;

    // 이메일
    UserDTO getUserEmailExists(UserDTO pDTO) throws Exception;


    //회원가입
    int insertUserInfo2(UserDTO pDTO) throws Exception;


    // 비밀번호 변경
    int newPasswordProc2(UserDTO pDTO) throws Exception;

    //비밀번호 아이디 찾기 허용
    UserDTO searchUserIdOrPasswordProc2(UserDTO pDTO) throws Exception;
}
