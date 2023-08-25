package com.example.demo.note;

import com.example.demo.utils.Mapper;
import org.springframework.stereotype.Component;


@Component
public class NoteMapper implements Mapper<NoteDto, NoteEntity> {

    @Override
    public NoteDto mapEntityToDto(NoteEntity source) {
        return NoteDto.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .title(source.getTitle())
                .content(source.getContent())
                .accessType(source.getAccessType())
                .userId(source.getUserId())
                .build();
    }
}
