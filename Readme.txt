RESTAURANT MJP

El nostre projecte és un projecte que utilitza Maven. Està format per tres subprojectes: un pel servidor, un per a l'aplicació d'escriptori i un altre per a l'aplicació mòbil.
Cada projecte disposa del seu propi arxiu pom.xml amb la seva configuració.
Per tal de fer funcionar els projectes, s'ha d'accedir amb l'IDE a cadascun d'ells i prémer RUN. 
El primer que s'ha d'iniciar és el servidor, perquè les altres aplicacions hi puguin accedir i funcionar correctament.

SERVIDOR
S'encarrega de gestionar la informació del restaurant i de permetre que les altres aplicacions hi
accedeixin per a consulta o modi cació, segons convingui.
El servidor funciona al port 8080.
És possible modi car el port per mitjà del fitxer main/server/src/main/resources . 
Per tal de modificar-lo, s'ha d'afegir una línia amb server.port=<port> .
Un cop el servidor ha acabat d'inicialitzar-se, a la consola apareix el missatge --- Servidor iniciat --- .
El codi on es troben els endpoints per a les aplicacions clients es troba al directori:
MJPRestaurant\main\server\src\main\java\mjp\server\ServerMJP\Controller$

ESCRIPTORI
Aquesta aplicació està pensada per ser usada per usuaris administradors. Permet donar d'alta i
baixa altres usuaris, introduir els plats de la carta que poden demanar els clients i gestionar les taules.
S'ha utilitzat el patró Vista-Model-Controlador (VMC)
El codi on es gestionen les comunicacions amb el servidor es troba als directoris:
MJPRestaurant\main\desktop\src\main\java\com\mjprestaurant\controller\

MÒBIL
Aquesta aplicació està pensada per ser usada amb mòbils o tauletes principalment. La idea és que un usuari sense rol d'administrador, com pot ser un cambrer, entri a l'aplicació i configuri un àpat en una taula. 
Un cop feta la con guració inicial, el cambrer lliurarà el dispositiu als clients i aquests podran començar a fer les comandes que desitgin.
S'ha utilitzat el patró Model-Vista-ViewModel (MVVM)
El codi on es gestionen les comunicacions amb el servidor es troba als directoris:
MJPRestaurant\main\mobile\app\src\main\java\com\example\mjprestaurant\network