package com.noblesse.backend.clip.service;

import com.noblesse.backend.clip.domain.Clip;
import com.noblesse.backend.clip.dto.ClipRegistRequestDTO;
import com.noblesse.backend.clip.repository.ClipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClipService {

    private final ClipRepository clipRepository;

    public ClipService(ClipRepository clipRepository) {
        this.clipRepository = clipRepository;
    }

    public Clip findClipByClipId(Long ClipId) {
        return clipRepository.findClipByClipId(ClipId);
    }

    public List<Clip> findAll() {
        return clipRepository.findAll();
    }

    @Transactional
    public void insertClip(ClipRegistRequestDTO clipRegistRequestDTO) {
        clipRepository.save(new Clip(
                clipRegistRequestDTO.getClipTitle(),
                clipRegistRequestDTO.getClipUrl(),
                clipRegistRequestDTO.getIsOpened(),
                clipRegistRequestDTO.getUserId(),
                clipRegistRequestDTO.getTripId()
        ));
    }

    @Transactional
    public void updateClipByClipIdForExposeYN(Long ClipId) {
        clipRepository.updateClipIsOpendByClipId(ClipId);
    }

    @Transactional
    public void deleteClipByClipId(Long ClipId) {
        clipRepository.deleteById(ClipId);
    }
}
