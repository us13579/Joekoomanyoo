package com.ssafy.heritage.data.dto

data class Member(
    val groupSeq: Int,
    var memberAppeal: String,
    var memberIsEvaluated: String,
    var memberStatus: Int,              // 0:신청자, 1:회원, 2:방장
    val memberSeq: Int,
    val memberNickName: String,
    val userSeq: Int,
    val userId: String,
    var eval1: Int,
    var eval2: Int,
    var eval3: Int,
    var eval4: Int,
    var eval5: Int
)
