CREATE TABLE IF NOT EXISTS Escuderias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    version BIGINT,
    nombre VARCHAR(255),
    numeroVictorias VARCHAR(255),
    numeroPilotos VARCHAR(255),
    color VARCHAR(255)
);
