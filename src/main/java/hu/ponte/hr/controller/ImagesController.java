package hu.ponte.hr.controller;


import hu.ponte.hr.controller.upload.UploadController;
import hu.ponte.hr.services.ImageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for listing image meta data and get image files
 *
 * @author  Zoltán Hoffmann
 * @version 1.0
 * @since   2023-08-07
 */
@RestController()
@RequestMapping("api/images")
public class ImagesController {

    Logger logger = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        logger.info("ImagesController.listImages()");
        return imageStore.listImages();
    }

    @GetMapping("preview/{id}")
    public  ResponseEntity<?> getImage(@PathVariable("id") String id) {
        logger.info("ImagesController.getImage() with id {}", id);

        ImageMeta imageMeta = imageStore.getImage(Long.parseLong(id));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageMeta.getMimeType()))
                .body(imageMeta.getData());

	}

}
