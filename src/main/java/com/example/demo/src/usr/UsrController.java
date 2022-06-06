package com.example.demo.src.usr;

import com.example.demo.src.config.BaseException;
import com.example.demo.src.config.BaseResponse;
import com.example.demo.src.config.BaseResponseStatus;
import com.example.demo.src.usr.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.src.config.BaseResponseStatus.REQUEST_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/usr")
public class UsrController {
    private final UsrService usrService;

    @ResponseBody
    @GetMapping("/member/login")
    public String getLogin() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/member/doLogin")
    public BaseResponse<LoginRes> doLogin(@RequestBody ReqLogin reqLogin) throws BaseException {
        try{
            LoginRes loginRes = usrService.doLogin(reqLogin);
            return new BaseResponse<>(loginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/home/main")
    public BaseResponse<List<GetPostRes>> getMain() {
        return new BaseResponse<>(usrService.getMain());
    }

    @ResponseBody
    @GetMapping("/article/list")
    public BaseResponse<List<GetPostRes>> getBoard(@RequestParam(value="board") int board,
                                     @RequestParam(value="page", required = false, defaultValue = "1") int page,
                                     @RequestParam(value = "searchKeyword", required = false) String keyword) {
        return new BaseResponse<>(usrService.getBoard(board, page, keyword));
    }

    @ResponseBody
    @GetMapping("/article/detail")
    public BaseResponse<GetPostDetail> getPostDetail(@RequestParam(value = "boardId") int id) {
        return new BaseResponse<>(usrService.getPostDetail(id));
    }

    @ResponseBody
    @GetMapping("/article/modify")
    public BaseResponse<GetPostDetail> getModify(@RequestParam(value = "id") int id,
                                                 @RequestParam(value = "userIdx") int userIdx)
            throws BaseException {
        if(!usrService.checkPerm(userIdx, id)) {
            throw new BaseException(BaseResponseStatus.NO_PERMISSION);
        }
        return new BaseResponse<>(usrService.getPostDetail(id));
    }

    @ResponseBody
    @PatchMapping("/article/doModify")
    public BaseResponse<GetPostDetail> patchPost(@RequestBody ReqPostDetail reqPostDetail) {
        usrService.patchPost(reqPostDetail);
        return new BaseResponse<>(usrService.getPostDetail(reqPostDetail.getId()));
    }

    @ResponseBody
    @GetMapping("/article/write")
    public BaseResponse<String> getWrite(@RequestParam(value = "boardId") int boardId,
                                         @RequestParam(value = "userIdx") int userIdx) throws BaseException {
        if(boardId != userIdx) {
            return new BaseResponse<>("게시글 작성 권한이 없습니다.");
        }
        if(boardId == 1) {
            return new BaseResponse<>("공지사항 게시판입니다.");
        }
        if(boardId == 2) {
            return new BaseResponse<>("자유게시판입니다.");
        }
        throw new BaseException(REQUEST_ERROR);
    }

    @ResponseBody
    @PostMapping("/article/doWrite")
    public BaseResponse<GetPostDetail> postWrite(@RequestBody ReqWritePost reqPost) {
        int postIdx = usrService.postWrite(reqPost);
        return new BaseResponse<>(usrService.getPostDetail(postIdx));
    }

    @ResponseBody
    @DeleteMapping("/article/delete")
    public BaseResponse<String> deletePost(@RequestParam(value = "id") int id,
                                           @RequestBody GetUserIdx getUserIdx) throws BaseException{
        if(!usrService.checkPerm(getUserIdx.getUserIdx(), id) && id != 1) {
            throw new BaseException(BaseResponseStatus.NO_PERMISSION);
        }
        usrService.deletePost(id);
        return new BaseResponse<>("success delete post!");
    }
}
