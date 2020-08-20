package com.ssafy.waple.review.dto;

import io.swagger.annotations.ApiModelProperty;

public class ReviewPlaceDto {
	@ApiModelProperty(value = "장소 아이디", example = "19781214")
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

	@ApiModelProperty(value = "해당 장소의 리뷰 수", example = "1")
	private int count;

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "전화번호", example = "02-123-1234")
	private String tel;

	@ApiModelProperty(value = "카테고리", example = "음식점")
	private String category;

	@ApiModelProperty(value = "아이콘", example = "gg.ico")
	private  String icon;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "ReviewPlaceDto{" +
			"placeId='" + placeId + '\'' +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", lng='" + lng + '\'' +
			", lat='" + lat + '\'' +
			", url='" + url + '\'' +
			", count=" + count +
			", groupId=" + groupId +
			", tel='" + tel + '\'' +
			", category='" + category + '\'' +
			", icon='" + icon + '\'' +
			'}';
	}
}
