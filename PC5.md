# PC5 CC3S2

## Ejercicio 1

Hay pequeñísimos avances, están en la carpeta SQL

Pero más que código son anotaciones sobre las pre y post condiciones, no está muy avanzado. Así que la verdad no recomiendo verlo porque no creo que se merezca calificación

## Ejercicio 2

Cada método tiene su carpeta eg: Ejercicio2/Metodo2 tiene los archivos que se crearon/usaron en dicho método

## Método 1

El DockerFile contiene lo siguiente:

```docker
FROM alpine:latest
LABEL maintainer="Cesar Lara Avila <checha@claraa.io>"
LABEL description="Este Dockerfile de ejemplo instala NGINX"
RUN apk add --update nginx && \
rm -rf /var/cache/apk/* && \
mkdir -p /tmp/nginx/
COPY files/nginx.conf /etc/nginx/nginx.conf
COPY files/default.conf /etc/nginx/conf.d/default.conf
ADD files/html.tar.gz /usr/share/nginx/
EXPOSE 80/tcp
ENTRYPOINT ["nginx"]
CMD ["-g", "daemon off;"]
```

Con ayuda de la extensión de docker de visual studio code, construí la imagen:

![Untitled](PC5%20CC3S2/Untitled.png)

Con el comando:

```bash
docker container run -d --name dockerfile-example -p 8080:80 dockerfile-example:latest
```

Obtenemos:

![Untitled](PC5%20CC3S2/Untitled%201.png)

Y abrimos en el navegador (en este caso usé la extensión de docker de visual studio code):

![Untitled](PC5%20CC3S2/Untitled%202.png)

Y le di click en la opción Open in Browser

![Untitled](PC5%20CC3S2/Untitled%203.png)

### Método 2

Descargué la imagen para usar como base

![Untitled](PC5%20CC3S2/Untitled%204.png)

Para que 

```bash
docker container run -it --name alpine-test alpine / bin / sh
```

Pudiera correr en mi pc con windows se tuvo que cambiar a:

```bash
docker container run -it --name alpine-test alpine sh
```

Y se puede ver en la imagen de abajo junto con los demás pasos

Esto será algo más difícil de ver. Seguí estos pasos:

```bash
$ apk update
$ apk upgrade
$ apk add --update nginx
$ rm -rf /var/cache/apk/*
$ mkdir -p /tmp/nginx/
$ exit
```

![Untitled](PC5%20CC3S2/Untitled%205.png)

Luego procedí a crear el container y guardarlo

![Untitled](PC5%20CC3S2/Untitled%206.png)

![Untitled](PC5%20CC3S2/Untitled%207.png)

### Método 3

Descargamos la imagen de Alpine Linux para 64bits

![Untitled](PC5%20CC3S2/Untitled%208.png)

Respondiendo a la pregunta de por qué no se usó el link externo es porque se busca no depender de los servidores al momento de usar la imagen

Con el Dockerfile:

```docker
FROM scratch
ADD files/alpine-minirootfs-3.18.2-x86_64.tar.gz/
CMD ["/bin/sh"]
```

Y con el comando:

```bash
docker image build --tag local:fromscratch .
```

Obtenemos:

![Untitled](PC5%20CC3S2/Untitled%209.png)

Aquí van todas las imágenes de Docker que tengo :D

![Untitled](PC5%20CC3S2/Untitled%2010.png)

Para correr el contenedor uso:

```bash
docker container run -it --name alpine-test -p 8080:80 local:fromscratch
```

Pero primero borro el contenedor que ya tiene ese nombre:

```bash
docker rm alpine-test
```

Lo hice de la misma forma que con el primer metodo porque quería ver que funciona la página:

![Untitled](PC5%20CC3S2/Untitled%2011.png)

## Ejercicio 3

> La carpeta que tiene sus archivos es “demo”
> 

Después de seguir los pasos para lanzar la página web obtenemos:

![Untitled](PC5%20CC3S2/Untitled%2012.png)

Agregando la clase Video, e importando Model con:

```java
import org.springframework.ui.Model;
```

Obtenemos:

![Untitled](PC5%20CC3S2/Untitled%2013.png)

Clase Video:

```java
package com.example.demo;

public class Video {
    public Video(String name) {}
    
}
```

Clase ServicioVideo

```java
package com.example.demo.microservicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Video;

@Service
public class ServicioVideo {
    List<Video> videos = List.of(
        new Video("Video de Persona 3"),
        new Video("https://www.youtube.com/watch?v=hTVeFmHqZnk"),
        new Video("no se me ocurrió qué más poner"));
}
```

En ControladorBase, usamos el autowired para que pueda usar la dependencia de Video

```java
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ControladorBase {

    @Autowired
    private ServicioVideo servicioVideo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", servicioVideo.videos);
        return "index";
    }
}
```

Los cambios que realicé fueron:

```java
package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ServicioVideo {
    List<Video> videos = List.of(
        new Video("Video de Persona 3"),
        new Video("https://www.youtube.com/watch?v=hTVeFmHqZnk"),
        new Video("no se me ocurrió qué más poner"));
    public Video crear(Video nuevoVideo) {
        List<Video> extend = new ArrayList<>(videos);
        extend.add(nuevoVideo);
        this.videos = List.copyOf(extend);
        return nuevoVideo;
    }
}
```

Aquí se añade videos a la lista, primero creando una nueva lista llamada extend, que almacena videos. Luego se le agrega el video que se quiere, posteriormente se añade la lista de videos ya existentes a esta nueva lista, luego se actualiza nuestro atributo videos (lista) con la nueva lista que tienen el nuevo video y los anteriores.

Al final devuelve la lista nueva.

Y:

```java
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ControladorBase {

    @Autowired
    private ServicioVideo servicioVideo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", servicioVideo.videos);
        return "index";
    }

    @PostMapping("/nuevo-video")
    public String nuevoVideo(@ModelAttribute Video nuevoVideo) {
        servicioVideo.crear(nuevoVideo);
        return "redirect:/";
    }
}
```

Aquí lo que se hace es agregar la opción de nuevo video

Había un error, lo que faltaba era actualizar la clase video. Pues el constructor no hacía nada y tampoco tenía algún getter

```java
package com.example.demo;

public class Video {
    private String name;

    public Video(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

Y con eso, lancé la página y me puse a ~~jugar como niño chiquito~~ experimentar

![Untitled](PC5%20CC3S2/Untitled%2014.png)

![Untitled](PC5%20CC3S2/Untitled%2015.png)

Los cambios para rest fueron mínimos:

```java
public List<Video> getVideos(){
        return videos;
    }
```

Agregué ese getter a ServicioVideos, pues es el que usará ControllerApi

```java
package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorApi {
    private final ServicioVideo servicioVideo;
    public ControladorApi(ServicioVideo servicioVideo) {
        this.servicioVideo = servicioVideo;
    }
    @GetMapping("/api/videos")
    public List<Video> all() {
    return servicioVideo.getVideos();
    }
}
```

Luego de ejecutar la página y mientras está activa, hacer el curl:

![Untitled](PC5%20CC3S2/Untitled%2016.png)

Comparación de terminal con navegador

![Untitled](PC5%20CC3S2/Untitled%2017.png)

Después de muchos errores y una muy larga leída a la documentación y stackoverflow, descubrí que me faltaba un constructor vacío en la clase Video para que Spring construya el objeto en base al cuerpo del JSON que se envía en la petición

```java
package com.example.demo;

public class Video {
    private String name;

    public Video(String name) {
        this.name = name;
    }

    public Video() {
    }

    public String getName() {
        return name;
    }
}
```

Podemos ver en la página web que las solicitudes que hicimos con el cuerpo de JSON creo los videos “Video 1” y “… 3”

![Untitled](PC5%20CC3S2/Untitled%2018.png)

![Untitled](PC5%20CC3S2/Untitled%2019.png)

Al ejecutar el comando, obtenemos todos los objetos video en formato JSON

![Untitled](PC5%20CC3S2/Untitled%2020.png)