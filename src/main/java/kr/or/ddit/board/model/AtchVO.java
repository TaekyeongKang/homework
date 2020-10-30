package kr.or.ddit.board.model;

public class AtchVO {
	private int atch_seq;
	private int post_seq;
	private String atch_realPath;
	private String atch_uploadName;
	public int getAtch_seq() {
		return atch_seq;
	}
	public void setAtch_seq(int atch_seq) {
		this.atch_seq = atch_seq;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public String getAtch_realPath() {
		return atch_realPath;
	}
	public void setAtch_realPath(String atch_realPath) {
		this.atch_realPath = atch_realPath;
	}
	public String getAtch_uploadName() {
		return atch_uploadName;
	}
	public void setAtch_uploadName(String atch_uploadName) {
		this.atch_uploadName = atch_uploadName;
	}
	@Override
	public String toString() {
		return "AtchVO [atch_seq=" + atch_seq + ", post_seq=" + post_seq + ", atch_realPath=" + atch_realPath
				+ ", atch_uploadName=" + atch_uploadName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atch_realPath == null) ? 0 : atch_realPath.hashCode());
		result = prime * result + atch_seq;
		result = prime * result + ((atch_uploadName == null) ? 0 : atch_uploadName.hashCode());
		result = prime * result + post_seq;
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
		AtchVO other = (AtchVO) obj;
		if (atch_realPath == null) {
			if (other.atch_realPath != null)
				return false;
		} else if (!atch_realPath.equals(other.atch_realPath))
			return false;
		if (atch_seq != other.atch_seq)
			return false;
		if (atch_uploadName == null) {
			if (other.atch_uploadName != null)
				return false;
		} else if (!atch_uploadName.equals(other.atch_uploadName))
			return false;
		if (post_seq != other.post_seq)
			return false;
		return true;
	}
	
	
	
}
