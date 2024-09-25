package kopo.poly.mapper;

import kopo.poly.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {

    // 메일 가져오기
    List<MailDTO> getMailList() throws Exception;

    // 메일등록
    void insertMail(MailDTO pDTO) throws Exception;


}
