package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    //비밀번호 찾기 화면
    @GetMapping(value = "forgotPassword" )
    public String forgotPassword(HttpSession session) {
        log.info("{}.searchPassword Start!",this.getClass().getName());

        session.setAttribute("NEW_PASSWORD","");
        session.removeAttribute("NEW_PASSWORD");

        log.info("{}.searchPassword End!",this.getClass().getName());

        return "title/forgotPassword";

    }

    // 비밀번호 찾기 로직 수행
    @PostMapping(value = "forgotPasswordProc2")
    public String forgotPassword(HttpServletRequest request, ModelMap model,HttpSession session) throws Exception {
        log.info("{}.forgotPasswordProc Start!", this.getClass().getName());

        // 웹에서 받는 정보를 String 변수에 저장
        String userId = CmmUtil.nvl(request.getParameter("userId"));    // 아이디
        String email = CmmUtil.nvl(request.getParameter("email"));  // 이메일

        // 확인용 로그
        log.info("userId : {} / email : {}",userId,email);

        UserDTO pDTO = new UserDTO();
        pDTO.setId2(userId);
        pDTO.setEmail2(EncryptUtil.encAES128CBC(email));

        // 비밀번호 찾기 가능한지 확인하기
        UserDTO rDTO = Optional.ofNullable(userService.searchUserIdOrPasswordProc2(pDTO)).orElseGet(UserDTO::new);

        model.addAttribute("rDTO",rDTO);

        // 비밀번호 재생성하는 화면은 보안을 위해 반드시 NEW_PASSWORD 세션이 존재해야 접속 가능하도록 구현
        // userId 값을 넣은 이유는 비밀번호 재설정하는 newPasswordProc 함수에서 사용하기 위함
        session.setAttribute("NEW_PASSWORD",userId);

        log.info("{}.searchPasswordProc2 End!",this.getClass().getName());

        return "title/forgotPasswordChange";
    }

    /*
        비밀번호 찾기 로직 수행
        <p>
        아이디, 이름, 이메일 일치하면, 비밀번호 재발급 화면 이동
     */
    @PostMapping(value = "newPasswordProc")
    public String newPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

        log.info("{}.user/newPasswordProc Start!", this.getClass().getName());

        String msg; //웹에 보여줄 메시지

        // 정상적인 접근인지 체크
        String newPassword = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

        if (!newPassword.isEmpty()) { // 정상 접근

            /*
                웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장!!

                무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             */

            String password = CmmUtil.nvl(request.getParameter("password")); // 신규 비밀번호

            /*
                반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야한
                반드시 작성할 것
             */
            log.info("password : {}",password);

            /*
                웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기!!

                무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             */

            UserDTO pDTO = new UserDTO();
            pDTO.setId2(newPassword);
            pDTO.setPassword2(EncryptUtil.encHashSHA256(password));

            userService.newPasswordProc2(pDTO);

            // 비밀번호 재생성하는 화면은 보안을 위해 생성한 NEW_PASSWORD 세션 삭제
            session.setAttribute("NEW_PASSWORD","");
            session.removeAttribute("NEW_PASSWORD");

            msg = "비밀번호가 재설정되었습니다,";

        } else {    // 비정상 접근
            msg = "비정상 접근입니다.";
        }

        model.addAttribute("msg", msg);

        log.info("{}.user/newPasswordProc2 End!", this.getClass().getName());

        return "title/newPasswordResult2";
    }
}
