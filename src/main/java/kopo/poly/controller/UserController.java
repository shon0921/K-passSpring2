package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/title")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final IUserService userService;

    // 회원가입 화면 이동
    @GetMapping(value = "register")
    public String register() {
        log.info("{}.title/register",this.getClass().getName());

        return "/title/register";
    }

    @ResponseBody
    @PostMapping(value = "getUserIDEmailExists")
    public UserDTO getUserIDEmailExists(HttpServletRequest request) throws Exception {

        log.info("{}.getUserIdAndEmailExists Start!", this.getClass().getName());

        String userId = CmmUtil.nvl(request.getParameter("userId")); // 회원아이디
        String email = CmmUtil.nvl(request.getParameter("email"));   // 이메일

        log.info("userId : {}", userId);
        log.info("email : {}", email);

        UserDTO rDTO = new UserDTO();  // UserDTO 인스턴스 생성

        // 회원아이디를 통해 중복된 아이디인지 조회
        UserDTO userIdCheckDTO = Optional.ofNullable(userService.getUserIDEmailExists(new UserDTO())).orElseGet(UserDTO::new);
        userIdCheckDTO.setId2(userId); // 조회할 아이디 설정

        // 아이디 중복 여부 확인
        if (userIdCheckDTO.getId2() != null) {
            log.info("아이디가 중복되었습니다: {}", userId);
            rDTO.setExistsYn("Y");  // 중복 아이디 표시
            rDTO.setId2(userId);    // 중복된 아이디 설정
        } else {
            rDTO.setExistsYn("N");  // 중복되지 않은 경우

            // 이메일 중복 체크 수행
            UserDTO emailDTO = new UserDTO();
            emailDTO.setEmail2(EncryptUtil.encAES128CBC(email)); // 암호화된 이메일 설정

            // 입력된 이메일이 중복된 이메일인지 조회
            UserDTO emailCheckDTO = Optional.ofNullable(userService.getUserIDEmailExists(emailDTO)).orElseGet(UserDTO::new);

            // 이메일 중복 체크 결과 설정
            if (emailCheckDTO.getEmail2() != null) {
                rDTO.setExistsYn("Y"); // 이메일도 중복된 경우
            } else {
                rDTO.setExistsYn("N"); // 이메일 중복이 없는 경우

                rDTO.setRedirectUrl("/email-v.jsp");
            }
        }

        log.info("{}.getUserIdAndEmailExists End!", this.getClass().getName());

        return rDTO;  // UserDTO 반환
    }

    @ResponseBody
    @PostMapping(value = "insertUser")
    public MsgDTO insertUser(HttpServletRequest request) {

        log.info("{}.insertUser start!", this.getClass().getName());

        int res=0;   // 회원가입 결과
        String msg = "";    // 회원가입 결과에 대한 메시지를 전달할 변수
        MsgDTO dto; // 결과 메시지 구조

        // 웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserDTO pDTO;

        try {
            /*
                웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!

                무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             */
            String userId = CmmUtil.nvl(request.getParameter("userId"));    // 아이디
            String userName = CmmUtil.nvl(request.getParameter("userName"));    // 이름
            String password = CmmUtil.nvl(request.getParameter("password"));    // 비밀번호
            String email = CmmUtil.nvl(request.getParameter("email"));  // 이메일

            /*
                웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!

                무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             */

            /*
                반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
                반드시 작성할 것
             */
            log.info("userId : "+ userId);
            log.info("password : "+ password);
            log.info("email : "+ email);
            //log.info("addr1 : "+ addr1);
            //log.info("addr2 : "+ addr2);

            /*
                웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!

                무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             */

            //웹
            pDTO = new UserDTO();

            pDTO.setId2(userId);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword2(EncryptUtil.encHashSHA256(password));

            // 민감 정보인 이메일은 AES128-CBC로 암호화함
            pDTO.setEmail2(EncryptUtil.encAES128CBC(email));

            /*
                웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!


                무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             */

            /*
                회원가입
             */
            res = userService.insertUserInfo2(pDTO);

            log.info("회원가입 결과(res) : "+ res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }
        } catch (Exception e) {
            // 저저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실해하였습니다. : " +e;
            log.info(e.toString());

        } finally {
            // 결과 메시지 전달하기
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info("{}.insertUserInfo End!", this.getClass().getName());
        }

        return dto;
    }
}
