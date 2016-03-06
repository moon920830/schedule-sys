package com.rj.sys.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionViewModel {
	
	private Long id;
	private String positionName;
	private String positionType;
	
}
