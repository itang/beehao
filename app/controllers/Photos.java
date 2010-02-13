package controllers;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import controllers.api.PageController;
import models.entity.Photo;
import play.data.Upload;

import java.io.ByteArrayInputStream;

public class Photos extends PageController {
    static ImagesService imagesService = ImagesServiceFactory.getImagesService();

    public static void index() {
        renderArgs.put("photos", Photo.getAll());
        render();
    }

    public static void upload(String name, Upload upload) {
        byte[] oldImageData = upload.asBytes();

        Photo.add(name, new Blob(oldImageData));
        index();
    }

    public static void view_smallphoto(Long id) {
        Photo photo = Photo.get(id);
        Image normal = ImagesServiceFactory.makeImage(photo.content.getBytes());
        Image small = scale2Small(normal);

        byte[] newImageData = small.getImageData();
        renderBinary(new ByteArrayInputStream(newImageData));

        index();
    }

    public static void view_photo(Long id) {
        Photo photo = Photo.get(id);

        renderBinary(new ByteArrayInputStream(photo.content.getBytes()));

        index();
    }

    private static Image scale2Small(Image oldImage) {
        if (oldImage.getWidth() > oldImage.getHeight()) {
            return imagesService.applyTransform(ImagesServiceFactory.makeResize(120, 90), oldImage);
        }
        return imagesService.applyTransform(ImagesServiceFactory.makeResize(90, 120), oldImage);
    }

}
