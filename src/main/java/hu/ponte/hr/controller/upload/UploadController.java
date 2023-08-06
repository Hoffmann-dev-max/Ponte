package hu.ponte.hr.controller.upload;

import hu.ponte.hr.services.ImageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller whose method can be used to upload images
 *
 * @author  Zoltán Hoffmann
 * @version 1.0
 * @since   2023-08-07
 */
@Component
@RequestMapping("api/file")
public class UploadController
{
    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    ImageStore imageStore;

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(@RequestParam("file") MultipartFile file) {
        logger.info("UploadController.handleFormUpload(), fileName: {}", file.getName());

        try {
            imageStore.uploadImage(file);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "ok";
    }
}
