package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        return imageStore.listImages();
    }

    @GetMapping("preview/{id}")
    public  ResponseEntity<?> getImage(@PathVariable("id") String id) {
        ImageMeta imageMeta = imageStore.getImage(Long.parseLong(id));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageMeta.getMimeType()))
                .body(imageMeta.getData());

	}

}
