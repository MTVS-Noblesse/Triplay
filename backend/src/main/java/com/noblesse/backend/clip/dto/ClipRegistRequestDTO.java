package com.noblesse.backend.clip.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClipRegistRequestDTO {
    private String clipTitle;
    private String clipUrl;
    private Boolean isOpened;
    private Long userId;
    private Long tripId;

}
