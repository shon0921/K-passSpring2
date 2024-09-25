package kopo.poly.service;

import kopo.poly.dto.MailDTO;

import java.util.List;

public interface IMailService {

    int doSendMail(MailDTO pDTO);   //메일 발송


    void insertMail(MailDTO pDTO) throws Exception; // 메일 등록

    List<MailDTO> getMailList() throws Exception;   // 메일 리스트 조회
}
