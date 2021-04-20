package com.tombow.board.service;

import com.tombow.board.domain.entity.File;
import com.tombow.board.domain.repository.FileRepository;
import com.tombow.board.dto.FileDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//saveFile()은 업로드한 파일에 대한 정보를 기록하고, getFile()는 id 값을 사용하여 파일에 대한 정보를 가져옴
@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileDTO fileDTO) {
        return fileRepository.save(fileDTO.toEntity()).getId();
    }

    @Transactional
    public FileDTO getFile(Long id) {
        File file = fileRepository.findById(id).get();

        FileDTO fileDTO = FileDTO.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDTO;
    }
}
