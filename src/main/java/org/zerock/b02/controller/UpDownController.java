package org.zerock.b02.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b02.dto.UploadFileDTO;
import org.zerock.b02.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
@RestController
public class UpDownController {


    @Value("${org.zerock.upload.path}") // import 시에 springframework으로 시작하는 value
    private String uploadPath;

    @Operation(summary = "Upload POST", description = "POST 방식으로 파일 등록")
    @PostMapping(value="/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(@Parameter(
            description = "Files to be uploaded",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
    )UploadFileDTO uploadFileDTO){
        log.info(uploadFileDTO);

        if(uploadFileDTO.getFiles()!=null){

            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originialName = multipartFile.getOriginalFilename();
                log.info(multipartFile.getOriginalFilename());

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid+"_"+originialName);

                boolean image = false;

                try{
                    multipartFile.transferTo(savePath); // 실제 파일 저장

                    // 이미지 파일 종류라면
                    if(Files.probeContentType(savePath).startsWith("image")){
                        image = true;

                        File thumbFile = new File(uploadPath, "s_" + uuid+"_" +originialName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originialName)
                                .img(image)
                        .build());
            });
            return list;
        }
        return null;
    }

    @Operation(summary = "view 파일", description = "GET 방식으로 첨부 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName){
        log.info(fileName);
        Resource resource = new FileSystemResource(uploadPath + File.separator+ fileName);

        String resourceName = resource.getFilename();

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @DeleteMapping("/view/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable("fileName") String fileName){
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String recourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();

        boolean removed = false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = true;

            // 섬네일이 존재한다면
            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath + File.separator + "s_"+ fileName);
                thumbnailFile.delete();
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }

        resultMap.put("result", removed);

        return resultMap;
    }


}
