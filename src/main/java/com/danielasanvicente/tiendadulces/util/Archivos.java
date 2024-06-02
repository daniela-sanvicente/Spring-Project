package com.danielasanvicente.tiendadulces.util;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archivos {
    public static String almacenar(MultipartFile multipartFile,String direccion){
        String nombre=multipartFile.getOriginalFilename();
        Path path= Paths.get(direccion);
        direccion=path.toFile().getAbsolutePath();

        File imagen=new File(direccion+"/"+nombre);
        Path finalRuta=Paths.get(imagen.getAbsolutePath());
        try {
            byte[] bytes=multipartFile.getBytes();
            Files.write(finalRuta,bytes);

            //multipartFile.transferTo(imagen);
            return nombre;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al subir archivo");
            return null;
        }
    }
    public static void renombrar(String direccion,String viejo,String nuevo){
        File archivoViejo=new File(direccion+"/"+viejo);
        File archivoNuevo=new File(direccion+"/"+nuevo);
        archivoViejo.renameTo(archivoNuevo);
    }
}
