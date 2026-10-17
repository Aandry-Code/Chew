
 // Configuracion de las API de Chew

(function () {

    // Backend anterior de Node.js
    const LOCAL_API = "http://localhost:4000/api";

    const PRODUCCION_API =
        "https://Chew-backend.onrender.com/api";

    // Detectar si estamos trabajando localmente
    const host = window.location.hostname;

    const esLocal =
        !host ||
        host === "localhost" ||
        host === "127.0.0.1" ||
        host.startsWith("192.168.");

    // Conservar la conexion anterior de Node.js
    window.Chew_API = window.Chew_API ||
        (esLocal ? LOCAL_API : PRODUCCION_API);

    // Nueva API de Java Spring Boot
    window.Chew_API_SPRING = esLocal
        ? "http://localhost:8081/api"
        : null;

})();
