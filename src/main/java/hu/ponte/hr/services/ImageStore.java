package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.ImageEntity;
import hu.ponte.hr.mapper.ImageMapper;
import hu.ponte.hr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageStore {

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SignService signService;

    @Transactional
    public ImageMeta uploadImage(MultipartFile file) throws Exception {

        byte[] imageData = file.getBytes();
        ImageMeta imageMeta = ImageMeta.builder()
                .name(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .size(imageData.length)
                .digitalSign(signService.signSHA256RSA(imageData)).build();

        ImageEntity imageEntity = imageMapper.toEntity(imageMeta);
        imageEntity.setData(imageData);

        imageRepository.save(imageEntity);

        return imageMeta;
    }

    public ImageMeta getImage(Long id) {
        Optional<ImageEntity> dbImage = imageRepository.findById(id);
        return imageMapper.toDto(dbImage.get());
    }

    public List<ImageMeta> listImages() {
        List<ImageEntity> dbImages = imageRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<ImageMeta> imageMetaList = dbImages.stream().map( e -> imageMapper.toDto(e)).collect(Collectors.toList());

        return imageMetaList;
    }
}
