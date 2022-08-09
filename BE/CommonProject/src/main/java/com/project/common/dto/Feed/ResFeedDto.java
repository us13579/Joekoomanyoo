package com.project.common.dto.Feed;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Feed.FeedEntity;

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

   
   	// 모임 설정 정보 //

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedTime;

	
    



}