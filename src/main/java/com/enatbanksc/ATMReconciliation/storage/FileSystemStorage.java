/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Core file related tasks are handled on this class
 *
 * @author btinsae
 * @version 1.0
 */
public class FileSystemStorage implements StorageService {

    @Autowired
    StorageProperties properties;
    private final Path rootLocation;

    public FileSystemStorage(Path rootLocation) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * To create file storage directory using StorageProperties getLocation
     * method.
     */
    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Given multipartfile and not empty this method will store the file to the
     * directory created on init() method.
     *
     * @param file
     */
    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    /**
     *
     * @return stream of path on the root directory
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    /**
     * Given string filename resolve to path
     *
     * @param filename
     * @return path object from string filename
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * Given string filename return resource file
     *
     * @param filename
     * @return resource from string filename
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /**
     * Deletes all the files in the root directory recursively.
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

}
