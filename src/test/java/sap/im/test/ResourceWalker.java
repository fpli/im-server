package sap.im.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class ResourceWalker {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URI uri = ResourceWalker.class.getResource("/myqq.sql").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            myPath = fileSystem.getPath("/myqq.sql");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            Path path = it.next();
            Files.readAllLines(path).forEach(line -> {
                System.out.println(line);
            });
        }
    }

}
