package com.project.common.dto.Feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.entity.User.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResFeedDto {

	private int feedSeq;  
	
	// 모임 기본 정보 //
    private String feedImgUrl;
   	private String feedTitle;
   	private String feedContent;
   	private char feedOpen;

   	private String userImgUrl;
   	private String userNickname;
   
   	private List<String> hashtag;
   	// 모임 설정 정보 //

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;

	@Builder
	public ResFeedDto(FeedEntity feed,UserEntity user) {
		this.feedSeq = feed.getFeedSeq();
		this.feedImgUrl=feed.getFeedImgUrl();
		this.feedTitle=feed.getFeedTitle();
		this.feedContent=feed.getFeedContent();
		this.feedOpen=feed.getFeedOpen();
		this.userImgUrl=user.getProfileImgUrl();
		this.userNickname=user.getUserNickname();
		this.createdTime=feed.getCreatedTime();
		System.out.println(this.createdTime);
		this.hashtag=new ArrayList<>();
		for(FeedHashtagEntity entity :feed.getHashtags()) {
			this.hashtag.add(entity.getFhTag());
		}
	}
	
    



}
