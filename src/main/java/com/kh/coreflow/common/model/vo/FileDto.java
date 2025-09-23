package com.kh.coreflow.common.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FileDto {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class customFile{
		private Long imgId;
		private String imageCode;
		private String originName;
		private String changeName;
		private Long imgOrder;
		private Date createDate;
		private Long refId;
		private String mimeType;
	}

}
