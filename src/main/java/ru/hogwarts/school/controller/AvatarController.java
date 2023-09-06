package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /**
     * Endpoint for uploading an avatar from a PC. Returns status 200 if upload was successful.
     * The method takes a student id and an avatar file as a parameter.
     */
    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,
                                               @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint works in the same way as the previous one,
     * but to save the file it uses not a multipart file,
     * but a string with an address, which allows you to save
     * pictures from the Internet.
     */
    @PostMapping(value = "/{studentId}/avatarFromInternet", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<String> uploadAvatarFromInternet(@PathVariable Long studentId,
                                                           @RequestParam String path,
                                                           @RequestParam String avatarName) throws IOException {
        avatarService.uploadAvatarFromInternet(studentId, path, avatarName);
        return ResponseEntity.ok().build();
    }

    /**
     * The endpoint takes a student id as a parameter,
     * and returns ResponseEntities with an avatar byte array inside.
     * It also returns headers with information about the file and status 200.
     */
    @GetMapping("/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);//the searching avatar by id in database
        HttpHeaders headers = new HttpHeaders();
        //sets the title of the avatar file type from the database
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        //sets the length of the byte array from the database to the header
        headers.setContentLength(avatar.getData().length);
        //then the line of code returns the avatar as a byte array with headers and a response status of 200
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    /**
     *
     */
    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);//the searching avatar by id in database
        Path path = Path.of(avatar.getFilePath());
        //
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("get_avatars")
    public ResponseEntity<List<Avatar>> getAllAvatars(@RequestParam("page") int pageNumber,
                                                      @RequestParam("size") int pageSize) {
        List<Avatar> avatars = avatarService.getAllAvatars(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }
}
