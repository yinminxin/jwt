package com.course.ymx.jwt.mapper;

import com.course.ymx.jwt.common.base.MyMapper;
import com.course.ymx.jwt.entity.Test;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper extends MyMapper<Test,String> {

    /**
     * ----api层使用分页；栗子
     *         PageInfo pageInfo;
     *         Page page = PageHelper.startPage(pageNum, pageSize);
     *         //根据关键字查找标题符合的草稿和已发布的公告(置顶时间和更新时间排序)
     *         List<TeacherTeamNotice> all = teacherTeamNoticeService.findTeacherTeamNoticeByPageOfBack(searchKey,id);
     *         //用PageInfo对结果进行包装
     *         pageInfo = new PageInfo(page.getResult());
     *         pageInfo.setList(all);
     *
     *
     */

//    @Select({"<script>" ,
//            "SELECT id,title,author,intro,publish_date,top_date,status,created_time,updated_time FROM test " +
//                    " WHERE deleted = 0 AND status in(0,1) AND teacher_team_id = #{id}" +
//                    "<if test='searchKey!=null'>"+
//                    "AND title LIKE CONCAT('%',#{searchKey},'%')" +
//                    "</if>"+
//                    " ORDER BY top_date DESC,updated_time DESC " +
//                    "</script>"})
//        //根据关键字查找标题符合的草稿和已发布的公告(置顶时间和更新时间排序)
//    List<Test> findTest(@Param("searchKey") String searchKey, @Param("id") Long id);

//    @Delete({"<script>",
//            "DELETE FROM test WHERE id in" +
//                    "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>" +
//                    "#{id}" +
//                    "</foreach>" +
//                    "</script>"})
//    //根据ID集合删除
//    List<Test> deleteTest(@Param("ids") List<Long> ids);
}
