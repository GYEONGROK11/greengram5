package com.greengram.greengram4.feed;

import com.greengram.greengram4.entity.FeedCommentEntity;
import com.greengram.greengram4.entity.FeedEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedCommentEntity, Long> {
    //피드사진처럼 해도 되지만 댓글이 3개만 있으면 되는데 굳이 모든 댓글을 가져와야되니 레파지토리로 TOP4개만 들고왔음
    @EntityGraph(attributePaths = {"userEntity"})
    List<FeedCommentEntity> findAllTop4ByFeedEntity(FeedEntity feedEntity);
    //파라미터는 피드기준으로 댓글을 가져와야되서  //유저였으면 피드상관없이 그 유저가 단 댓글
}
