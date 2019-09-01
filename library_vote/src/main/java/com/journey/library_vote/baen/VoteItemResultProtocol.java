package com.journey.library_vote.baen;


import com.journey.base.customview.BaseCustomViewModel;

public class VoteItemResultProtocol extends BaseCustomViewModel {

	public static final long serialVersionUID = -8736844131481941854L;
	public int id;
	public String category;
	public String title;
	public String statusmsg;
	public String status;
	public long addtime;
	public long lasttime;
	public String samples;
	public String points;
	public String role_view;
	public String view_points;
	public long updatetime;
	public int uid;
	public String replynums;
	public String vote_title;
	public String vote_type;
	public String votetimes;
	public String question_id;
	public String user_head;
	public String user_id;
	public String nickname;

	public VoteItemResultProtocol() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusmsg() {
		return statusmsg;
	}

	public void setStatusmsg(String statusmsg) {
		this.statusmsg = statusmsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public long getLasttime() {
		return lasttime;
	}

	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}

	public String getSamples() {
		return samples;
	}

	public void setSamples(String samples) {
		this.samples = samples;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getRole_view() {
		return role_view;
	}

	public void setRole_view(String role_view) {
		this.role_view = role_view;
	}

	public String getView_points() {
		return view_points;
	}

	public void setView_points(String view_points) {
		this.view_points = view_points;
	}

	public long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getReplynums() {
		return replynums;
	}

	public void setReplynums(String replynums) {
		this.replynums = replynums;
	}

	public String getVote_title() {
		return vote_title;
	}

	public void setVote_title(String vote_title) {
		this.vote_title = vote_title;
	}

	public String getVote_type() {
		return vote_type;
	}

	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}

	public String getVotetimes() {
		return votetimes;
	}

	public void setVotetimes(String votetimes) {
		this.votetimes = votetimes;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String getUser_head() {
		return user_head;
	}

	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
