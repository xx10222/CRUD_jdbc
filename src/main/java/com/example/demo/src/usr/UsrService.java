package com.example.demo.src.usr;

import com.example.demo.src.config.BaseException;
import com.example.demo.src.usr.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.src.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UsrService {
    private final UsrDao usrDao;

    public LoginRes doLogin(ReqLogin reqLogin) throws BaseException {
        try {
            if(usrDao.checkId(reqLogin.getLoginId())==0) {
                throw new BaseException(NO_EXIST_ID);
            }
            if(!reqLogin.getLoginPw().equals(usrDao.getPw(reqLogin.getLoginId()))) {
                throw new BaseException(WRONG_PASSWORD);
            }

            return usrDao.getUserById(reqLogin.getLoginId());
        } catch (Exception exception) {
            throw new BaseException(FAIL_LOGIN);
        }
    }

    public List<GetPostRes> getMain() {
        return usrDao.getMain();
    }

    public List<GetPostRes> getBoard(int board, int page, String keyword) {
        if(page==0) {
            page++;
        }
        if(keyword == "") {
            return usrDao.getBoard(board, (page-1)*10);
        }
        return usrDao.getBoardSearch(board, (page-1)*10, keyword);
    }

    public GetPostDetail getPostDetail(int id) {
        return usrDao.getPostDetail(id);
    }

    public void patchPost(ReqPostDetail reqPostDetail) {
        usrDao.patchPost(reqPostDetail);
    }

    public int postWrite(ReqWritePost reqPost) {
        if(reqPost.getBoardIdx() == 1) {
            return usrDao.postWrite(reqPost, "관리자");
        }
        return usrDao.postWrite(reqPost, "user1");
    }

    public boolean checkPerm(int userIdx, int postIdx){
        int boardId = usrDao.getBoardIdx(postIdx);
        return userIdx == boardId;
    }

    public void deletePost(int postIdx) {
        usrDao.deletePost(postIdx);
    }
}
