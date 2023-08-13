package ru.hogwarts.school.service;

import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatarFromInternet(Long studentId, String path, String avatarName) throws IOException {
        File avatarFile = new File(path);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);//получаем студента по id
        if (optionalStudent.isPresent() && avatarFile == null) { //проверяем что не получили ноль
            throw new IllegalArgumentException();
        }
        Student student = optionalStudent.orElse(null); //извлекаем студента из обертки Optional
        String originalFileName = avatarName;//получаем имя файла аватарки

        if (originalFileName == null) { //проверяем что имя аватарки действительно получено
            throw new IllegalArgumentException();
        }

//        Path filePath = Path.of(avatarFile.getPath(), "аватар из интернета1");
        Path filePath = Path.of(path);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = Files.newInputStream(avatarFile.toPath());//открываем поток из аватарки MultipartFile
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);//получаем аватар по id студента
        avatar.setStudent(student);// присваиваем в аватар id студента
        avatar.setFilePath(filePath.toString()); //путь к файлу
        avatar.setFileSize(avatarFile.length()); //размер файла

        String fileType = Files.probeContentType(Paths.get(String.valueOf(filePath)));
        avatar.setMediaType(fileType); //тип файла

        byte[] fileBytes = Files.readAllBytes(Paths.get(String.valueOf(filePath)));
        avatar.setData(fileBytes); //сохраняем массив байт в аватар (сохраняем саму картинку)
        avatarRepository.save(avatar); //сохраняем в БД

    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);//получаем студента по id
        if (optionalStudent.isPresent() && avatarFile == null) { //проверяем что не получили ноль
            throw new IllegalArgumentException();
        }
        Student student = optionalStudent.orElse(null); //извлекаем студента из обертки Optional
        String originalFileName = avatarFile.getOriginalFilename();//получаем имя файла аватарки

        if (originalFileName == null) { //проверяем что имя аватарки действительно получено
            throw new IllegalArgumentException();
        }
        //формируем путь к файлу аватарки - путь + имя файла + расширение
        Path filePath = Path.of(avatarsDir, studentId + getExtensions(originalFileName));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        //создаем потоки исходящий и входящий, а так же буферизированные на их основе
        try (
                InputStream is = avatarFile.getInputStream();//открываем поток из аватарки MultipartFile
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);//
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos); //подаем входящий поток в исходящий, записывая аватарку с КП в БД
        }
        Avatar avatar = findAvatar(studentId);//получаем аватар по id студента
        avatar.setStudent(student);// присваиваем в аватар id студента
        avatar.setFilePath(filePath.toString()); //путь к файлу
        avatar.setFileSize(avatarFile.getSize()); //размер файла
        avatar.setMediaType(avatarFile.getContentType()); //тип файла
        avatar.setData(avatarFile.getBytes()); //размер файла
        avatarRepository.save(avatar); //сохраняем в БД
    }



    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    public List<Avatar> getAllAvatars(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
