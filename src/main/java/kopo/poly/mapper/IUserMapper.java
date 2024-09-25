package kopo.poly.mapper;

import kopo.poly.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {

    // 회원가입하기
    int insertUserInfo2(UserDTO pDTO) throws Exception;

    // 회원 가입전 아이디 이메일 둘다 중복체크하기(DB조회)
    UserDTO getUserIDEmailExists(UserDTO pDTO) throws Exception;
}
