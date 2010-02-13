package controllers;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import controllers.api.PageController;
import models.entity.Album;
import models.entity.Photo;
import play.data.Upload;
import play.data.validation.Required;
import play.data.validation.Validation;

import java.io.ByteArrayInputStream;
import java.util.List;

import static models.manage.AlbumManage.instance;

/**
 * Album 相册 Action.
 *
 * @author itang
 */
public class Albums extends PageController {
    private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();

    public static void index() {
        renderArgs.put("albums", instance(currUser().email).getAll());
        render();
    }

    public static void blank() {
        flash.put("album", "照片");
        render();
    }

    public static void create(@Required String name, String notes) {
        if (Validation.hasErrors()) {
            flash.error("输入有误");
        } else {
            instance(currUser().email).add(name, notes);
        }
        index();
    }

    public static void show(@Required Long id) {
        Album album = instance(currUser().email).get(id);
        List<Photo> photos = album.photos();
        render(album, photos);
    }

    public static void edit(@Required Long id) {
        Album album = instance(currUser().email).get(id);
        List<Photo> photos = album.photos();
        render(album, photos);
    }

    public static void delete(@Required Long id) {
        Album album = instance(currUser().email).get(id);
        notFoundIfNull(album);
        album.delete();
        flash.success("相册 %s 已经删除了", album);
        index();
    }

    public static void save(@Required Long id, @Required String name, String notes) {
        if (Validation.hasErrors()) {
            flash.error("输入有误!");
            edit(id);
        }
        Album album = instance(currUser().email).get(id);
        notFoundIfNull(album);
        album.name = name;
        album.notes = notes;
        album.update();

        show(id);
    }

    public static void upload(@Required Long id, @Required String name, @Required Upload upload, String notes) {
        if (Validation.hasErrors()) {
            flash.error("请输入名称");
            show(id);
        }

        Album album = instance(currUser().email).get(id);
        byte[] oldImageData = upload.asBytes();

        Photo.add(album, name, new Blob(oldImageData), notes);

        show(id);
    }

    public static void delete_photo(@Required Long id, @Required Long photoId) {
        Photo photo = Photo.get(photoId);
        photo.delete();

        flash.success(String.format("成功删除照片:%s", photo.name));
        edit(id);
    }

    public static void set_cover(@Required Long id, @Required Long photoId) {
        Album album = instance(currUser().email).get(id);
        Photo photo = Photo.get(photoId);
        album.cover = Photo.get(photoId);
        album.update();
        flash.success(String.format("成功设置%s为封面照片!", photo.name));

        edit(id);
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
