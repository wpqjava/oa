package wpq.dto;

import java.io.File;

public class AttachDto {
	private File[] attach;
	private String[] attachContentType;
	private String[] attachFileName;
	private boolean hasAttach;

	public File[] getAttach() {
		return attach;
	}

	public void setAttach(File[] attach) {
		this.attach = attach;
	}

	public String[] getAttachContentType() {
		return attachContentType;
	}

	public void setAttachContentType(String[] attachContentType) {
		this.attachContentType = attachContentType;
	}

	public String[] getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String[] attachFileName) {
		this.attachFileName = attachFileName;
	}

	public boolean isHasAttach() {
		return hasAttach;
	}

	public void setHasAttach(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}

	public AttachDto() {
	}

	public AttachDto(File[] attach, String[] attachContentType, String[] attachFileName) {
		super();
		this.attach = attach;
		this.attachContentType = attachContentType;
		this.attachFileName = attachFileName;
		this.hasAttach = true;
	}

	public AttachDto(boolean hasAttach) {
		super();
		this.hasAttach = hasAttach;
	}
	
}
