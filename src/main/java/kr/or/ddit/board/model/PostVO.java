package kr.or.ddit.board.model;

import java.util.Date;

public class PostVO {
	private int post_seq;
	private int board_seq;
	private int p_post_seq;
	private String post_title;
	private String post_content;
	private String userid;
	private Date post_date;
	private int flag_delete; // 1이면 사용중, 0이면 삭제함
	private int level;	// 계층구조 출력을 위한 맴버변수
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public int getP_post_seq() {
		return p_post_seq;
	}
	public void setP_post_seq(int p_post_seq) {
		this.p_post_seq = p_post_seq;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public int getFlag_delete() {
		return flag_delete;
	}
	public void setFlag_delete(int flag_delete) {
		this.flag_delete = flag_delete;
	}
	@Override
	public String toString() {
		return "PostVO [post_seq=" + post_seq + ", board_seq=" + board_seq + ", p_post_seq=" + p_post_seq
				+ ", post_title=" + post_title + ", post_content=" + post_content + ", userid=" + userid
				+ ", post_date=" + post_date + ", flag_delete=" + flag_delete + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + board_seq;
		result = prime * result + flag_delete;
		result = prime * result + p_post_seq;
		result = prime * result + ((post_content == null) ? 0 : post_content.hashCode());
		result = prime * result + ((post_date == null) ? 0 : post_date.hashCode());
		result = prime * result + post_seq;
		result = prime * result + ((post_title == null) ? 0 : post_title.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostVO other = (PostVO) obj;
		if (board_seq != other.board_seq)
			return false;
		if (flag_delete != other.flag_delete)
			return false;
		if (p_post_seq != other.p_post_seq)
			return false;
		if (post_content == null) {
			if (other.post_content != null)
				return false;
		} else if (!post_content.equals(other.post_content))
			return false;
		if (post_date == null) {
			if (other.post_date != null)
				return false;
		} else if (!post_date.equals(other.post_date))
			return false;
		if (post_seq != other.post_seq)
			return false;
		if (post_title == null) {
			if (other.post_title != null)
				return false;
		} else if (!post_title.equals(other.post_title))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	
	
}
