package com.ssafy.waple.bookmark.dto;

import io.swagger.annotations.ApiModelProperty;

public class BookmarkDto {
	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "테마 아이디", example = "1")
	private int themeId;

	@ApiModelProperty(value = "장소 아이디", example = "19781214")
	private String placeId;

	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	private long userId;

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

	@ApiModelProperty(value = "전화번호", example = "02-123-1234")
	private String tel;

	@ApiModelProperty(value = "매장 사진", example = "매장.jpg")
	private  String img;


	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "BookmarkDto{" +
			"groupId=" + groupId +
			", themeId=" + themeId +
			", placeId='" + placeId + '\'' +
			", userId=" + userId +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", lng='" + lng + '\'' +
			", lat='" + lat + '\'' +
			", url='" + url + '\'' +
			", tel='" + tel + '\'' +
			", img='" + img + '\'' +
			'}';
	}
}
