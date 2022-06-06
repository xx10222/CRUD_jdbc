package com.example.demo.src.usr;

import com.example.demo.src.usr.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsrDao {
    private final JdbcTemplate jdbcTemplate;

    public int checkId(String id) {
        String query = "select exists(select * from user where id=?)";
        return this.jdbcTemplate.queryForObject(query,int.class, id);
    }

    public String getPw(String id) {
        String query = "select password from user where id=?";
        return this.jdbcTemplate.queryForObject(query,String.class, id);
    }

    public LoginRes getUserById(String id) {
        String query = "select * from user where id = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new LoginRes(
                        rs.getInt("userIdx"),
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ), id);
    }

    public List<GetPostRes> getMain() {
        List<GetPostRes> posts = new ArrayList<>();

        String Query = "select * from post where boardIdx=? order by createAt desc limit 10";
        List<GetPostRes> notices = this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getString("writer"),
                        rs.getString("title")
                ), 1);
        for(GetPostRes p : notices) {
            posts.add(p);
        }

        List<GetPostRes> freePosts = this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getString("writer"),
                        rs.getString("title")
                ), 2);
        for(GetPostRes p : freePosts) {
            posts.add(p);
        }

        return posts;
    }

    public List<GetPostRes> getBoard(int board, int offset) {
        String query = "select * from post where boardIdx=? order by postIdx desc limit 10 offset ?";
        Object[] params = new Object[]{board, offset};
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getString("writer"),
                        rs.getString("title")
                ), params);
    }

    public List<GetPostRes> getBoardSearch(int board, int offset, String keyword) {
        String query = "select * from post orders where boardIdx=? and content like '%" + keyword
                + "%' order by postIdx desc limit 10 offset ?;";

        Object[] params = new Object[]{board, offset};
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getString("writer"),
                        rs.getString("title")
                ), params);
    }

    public GetPostDetail getPostDetail(int id) {
        String query = "select * from post where postIdx = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs,rowNum) -> new GetPostDetail(
                        rs.getInt("postIdx"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("createAt"),
                        rs.getString("updateAt")), id);
    }

    public void patchPost(ReqPostDetail reqPostDetail) {
        String query = "update post set title = ?, content = ? where postIdx = ?";
        Object[] params = new Object[]{reqPostDetail.getTitle(), reqPostDetail.getContent(), reqPostDetail.getId()};
        this.jdbcTemplate.update(query, params);
    }

    public int postWrite(ReqWritePost reqPost, String writer) {
        String query = "insert into post(title, content, writer, boardIdx) values(?,?,?,?)";
        Object[] params = new Object[]{reqPost.getTitle(), reqPost.getContent(),
                writer, reqPost.getBoardIdx()};
        this.jdbcTemplate.update(query, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int getBoardIdx(int postIdx) {
        String query = "select boardIdx from post where postIdx=?";
        return this.jdbcTemplate.queryForObject(query, int.class, postIdx);
    }

    public void deletePost(int postIdx) {
        String query = "delete from post where postIdx=?";
        this.jdbcTemplate.update(query, postIdx);
    }
}
