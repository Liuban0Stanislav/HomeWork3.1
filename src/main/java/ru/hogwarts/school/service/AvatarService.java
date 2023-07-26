package ru.hogwarts.school.service;

import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
//        Optional<Avatar> optionalAvatar = avatarRepository.findAvatarByStudentId(studentId);
//        if (optionalAvatar.isEmpty()) {
//            return new Avatar();
//        }
//        return optionalAvatar.get();
    }
}
