package kr.or.ddit.board.model;

public class BoardVO {
	private int board_seq;
	private String board_name;
	private int status;		// 1이면 사용중, 0이면 비활성화
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BoardVO [board_seq=" + board_seq + ", board_name=" + board_name + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board_name == null) ? 0 : board_name.hashCode());
		result = prime * result + board_seq;
		result = prime * result + status;
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
		BoardVO other = (BoardVO) obj;
		if (board_name == null) {
			if (other.board_name != null)
				return false;
		} else if (!board_name.equals(other.board_name))
			return false;
		if (board_seq != other.board_seq)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
	
	
}
