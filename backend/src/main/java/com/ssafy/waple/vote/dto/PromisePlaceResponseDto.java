package com.ssafy.waple.vote.dto;

import io.swagger.annotations.ApiModelProperty;

public class PromisePlaceResponseDto {
	@ApiModelProperty(value = "투표 장소 Id", example = "19781214")
	private String placeId;

	@ApiModelProperty(value = "장소 이름", example = "콩두")
	private String name;

	@ApiModelProperty(value = "주소", example = "서울 중구 덕수궁길 116-1")
	private String address;

	@ApiModelProperty(value = "경도", example = "126.974060927964")
	private String lng;

	@ApiModelProperty(value = "위도", example = "37.5677513088738")
	private String lat;

	@ApiModelProperty(value = "장소 상세페이지 URL", example = "http://place.map.kakao.com/19781214")
	private String url;

	@ApiModelProperty(value = "장소 추가한 유저 이름", example = "바나나먹는몽키")
	private String pickedUserName;

	@ApiModelProperty(value = "현재 투표 수", example = "3")
	private int voteNum;

	@ApiModelProperty(value = "현재 유저의 투표 여부", example = "false")
	private boolean isVoted;

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPickedUserName() {
		return pickedUserName;
	}

	public void setPickedUserName(String pickedUserName) {
		this.pickedUserName = pickedUserName;
	}

	public int getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(int voteNum) {
		this.voteNum = voteNum;
	}

	public boolean isVoted() {
		return isVoted;
	}

	public void setVoted(boolean voted) {
		isVoted = voted;
	}

	@Override
	public String toString() {
		return "PromisePlaceResponseDto{" +
			"placeId='" + placeId + '\'' +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", lng='" + lng + '\'' +
			", lat='" + lat + '\'' +
			", url='" + url + '\'' +
			", pickedUserName='" + pickedUserName + '\'' +
			", voteNum=" + voteNum +
			", isVoted=" + isVoted +
			'}';
	}
}
