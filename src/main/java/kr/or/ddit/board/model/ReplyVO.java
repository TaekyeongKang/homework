package kr.or.ddit.board.model;

import java.util.Date;

public class ReplyVO {
	private int reply_seq;
	private String userid;
	private int post_seq;
	private String reply_content;
	private Date reply_date;
	private int flag_delete;
	public int getReply_seq() {
		return reply_seq;
	}
	public void setReply_seq(int reply_seq) {
		this.reply_seq = reply_seq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Date getReply_date() {
		return reply_date;
	}
	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	public int getFlag_delete() {
		return flag_delete;
	}
	public void setFlag_delete(int flag_delete) {
		this.flag_delete = flag_delete;
	}
	@Override
	public String toString() {
		return "ReplyVO [reply_seq=" + reply_seq + ", userid=" + userid + ", post_seq=" + post_seq + ", reply_content="
				+ reply_content + ", reply_date=" + reply_date + ", flag_delete=" + flag_delete + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flag_delete;
		result = prime * result + post_seq;
		result = prime * result + ((reply_content == null) ? 0 : reply_content.hashCode());
		result = prime * result + ((reply_date == null) ? 0 : reply_date.hashCode());
		result = prime * result + reply_seq;
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
		ReplyVO other = (ReplyVO) obj;
		if (flag_delete != other.flag_delete)
			return false;
		if (post_seq != other.post_seq)
			return false;
		if (reply_content == null) {
			if (other.reply_content != null)
				return false;
		} else if (!reply_content.equals(other.reply_content))
			return false;
		if (reply_date == null) {
			if (other.reply_date != null)
				return false;
		} else if (!reply_date.equals(other.reply_date))
			return false;
		if (reply_seq != other.reply_seq)
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	
	
	
}
