package com.noblesse.backend.clip.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClipReportRegistRequestDTO {
    private Long reportCategoryId;
    private String clipReportTitle;
    private String clipReportContent;
    private Long userId;
    private Long clipId;
}
