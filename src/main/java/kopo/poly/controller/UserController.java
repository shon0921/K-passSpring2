package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserDTO;
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

    @GetMapping(value = "register2")
    public String register2() {
        log.info("{}.title/register2",this.getClass().getName());

        return "title/register2";
    }

    // 아이디 중복체크
    @ResponseBody
    @PostMapping(value = "getUserIDEmailExists")
    public UserDTO getUserIDEmailExists(HttpServletRequest request) throws Exception {

        log.info("{}.getUserIdExists Start!",this.getClass().getName());

        String userId = CmmUtil.nvl(request.getParameter("userId")); // 회원아이디

        log.info("userId : {}", userId);

        UserDTO pDTO = new UserDTO();
        pDTO.setId2(userId);

        // 회원아이디를 통해 중복된 아이디인지 조회
        UserDTO rDTO = Optional.ofNullable(userService.getUserIDEmailExists(pDTO)).orElseGet(UserDTO::new);

        log.info("{}.getUserIdExists End!", this.getClass().getName());

        return rDTO;
    }

    // 이메일 중복 조회
    @ResponseBody
    @PostMapping(value = "getUserEmailExists")
    public UserDTO getUserEmailExists(HttpServletRequest request) throws Exception {

        log.info("{}.getUserEmailExists Start!", this.getClass().getName());

        String email = CmmUtil.nvl(request.getParameter("email"));  // 회원아이디

        log.info("email : {}", email);

        UserDTO pDTO = new UserDTO();
        pDTO.setEmail2(EncryptUtil.encAES128CBC(email));

        // 입력된 이메일이 중복된 이메일인지 조회
        UserDTO rDTO = Optional.ofNullable(userService.getUserEmailExists(pDTO)).orElseGet(UserDTO::new);

        log.info("{}.getUserEmailExists End!", this.getClass().getName());

        return rDTO;
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
