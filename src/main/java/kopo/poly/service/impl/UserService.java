package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.mapper.IUserMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final IUserMapper userMapper; // 회원 mapper 가져오기

    private final IMailService mailService; // 메일발송 객체 가져오기

    //아이디 중복체크
    @Override
    public UserDTO getUserIDEmailExists(UserDTO pDTO) throws Exception {

        log.info("{}.getUserIdExists Start!", this.getClass().getName());

        // DB 아이디가 존재하는지 SQL 쿼리 실행
        UserDTO rDTO = userMapper.getUserIdExists(pDTO);

        log.info("{}.getUserIdExists End!", this.getClass().getName());

        return rDTO;

        }


    @Override
    public UserDTO getUserEmailExists(UserDTO pDTO) throws Exception {
        log.info("{}.emailAuth Start!", this.getClass().getName());

        // DB 이메일이 존재하는지 SQL 쿼리 실행
        UserDTO rDTO = Optional.ofNullable(userMapper.getUserEmailExists(pDTO)).orElseGet(UserDTO::new);

        log.info("rDTO : {}", rDTO);

        // 이메일 주소가 중복되지 않는다면 메일 발송
        if (CmmUtil.nvl(rDTO.getExistsYn()).equals("N")) {

            // 6 자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000,999999);

            log.info("authNumber : {}", authNumber);

            // 인증번호 발송 로직
            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 "+authNumber+" 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail2())));

            mailService.doSendMail(dto);    // 이메일 발송

            dto = null;

            rDTO.setAuthNumber(authNumber); // 인증번호를 결과값에 넣어주기

        }

        log.info("{}.emailAuth End!",this.getClass().getName());

        return rDTO;
    }

    //회원가입하기
    @Override
    public int insertUserInfo2(UserDTO pDTD) throws Exception {
        log.info("{}.insertUserInfo Start!", this.getClass().getName());

        // 회원가입 성공 : 1, 아이디 중복으로 인한 가입 취소 : 2, 기타 에러 발생 : 0
        int res;

        // 회원가입
        int success = userMapper.insertUserInfo2(pDTD);

        // db에 데이터가 등록되었다면(회원가입 성공했다면 ....
        if(success > 0) {
            res = 1;

            /*
            메일 발송 로직 추가 시작!
             */
            MailDTO mDTO = new MailDTO();

            // 회원정보화면에서 입력받은 이메일 변수(아직 암호화되어 넘어오기 때문에 복호화 수행함)
            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTD.getEmail2())));

            mDTO.setTitle("회원가입을 축하드립니다."); // 제목

            // 메일 내용에 가입자 이름넣어서 내용 발송
            mDTO.setContents(CmmUtil.nvl(pDTD.getId2()) + "님의 회원가입을 진심으로 축하드립니다.");

            // 회원 가입이 성공했기 때문에 메일을 발송함
            mailService.doSendMail(mDTO);

            /*
                메일 발송 로직 추가 끝
             */
        } else {
            res = 0;
        }

        log.info("{}.insertUserInfo End!",this.getClass().getName());

        return res;
    }

    // 비밀번호 변경
    @Override
    public int newPasswordProc2(UserDTO pDTO) throws Exception {

        log.info("{}.newPasswordProc Start!",this.getClass().getName());

        // 비밀번호 재설정
        int success = userMapper.updatePassword2(pDTO);

        log.info("{}.newPasswordProc End!", this.getClass().getName());

        return success;
    }

    // 아이디 비밀번호 찾기 로직
    @Override
    public UserDTO searchUserIdOrPasswordProc2(UserDTO pDTO) throws Exception {

        log.info("{}.searchUserIdOrPasswordProc Start!",this.getClass().getName());

        UserDTO rDTO = userMapper.getUserId2(pDTO);

        log.info("{}.searchUserIdOrPasswordProc End!",this.getClass().getName());

        return rDTO;

    }
}
