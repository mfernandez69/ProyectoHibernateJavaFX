
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_pokemon`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenador`
--

CREATE TABLE `entrenador` (
  `idEntrenador` int(11) NOT NULL,
  `contrasena` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `entrenador`
--

INSERT INTO `entrenador` (`idEntrenador`, `contrasena`, `nombre`) VALUES
(1, '1234', 'marquitos'),
(2, '1234', 'usuario2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habilidad`
--

CREATE TABLE `habilidad` (
  `idHabilidad` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habilidad`
--

INSERT INTO `habilidad` (`idHabilidad`, `descripcion`, `nombre`) VALUES
(1, 'Aumenta la potencia de los movimientos de tipo Fuego cuando el Pokémon tiene poca salud', 'Mar Llamas'),
(2, 'Aumenta la potencia de los movimientos de tipo Agua cuando el Pokémon tiene poca salud.', 'Torrente'),
(3, 'Aumenta la potencia de los movimientos de tipo Planta cuando el Pokémon tiene poca salud.', 'Espesura'),
(4, 'Evita que el Pokémon se duerma.', 'Insomnio'),
(5, 'El Pokémon no puede ser paralizado.', 'Cuerpo Puro'),
(6, 'Aumenta la Velocidad del Pokémon bajo la lluvia.', 'Nado Rápido'),
(7, 'Aumenta la precisión del Pokémon en un 30%.', 'Ojoavizor'),
(8, 'Impide que los stats del Pokémon sean reducidos por movimientos enemigos.', 'Agallas'),
(9, 'El Pokémon absorbe los movimientos de tipo Eléctrico, aumentando su Ataque Especial.', 'Pararrayos'),
(10, 'Aumenta el Ataque del Pokémon cuando recibe un golpe crítico.', 'Ímpetu');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

CREATE TABLE `pokemon` (
  `entrenador_id` int(11) DEFAULT NULL,
  `habilidad_id` int(11) DEFAULT NULL,
  `idPokemon` int(11) NOT NULL,
  `tipo_id` int(11) DEFAULT NULL,
  `nombre_pokemon` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pokemon`
--

INSERT INTO `pokemon` (`entrenador_id`, `habilidad_id`, `idPokemon`, `tipo_id`, `nombre_pokemon`) VALUES
(1, 1, 1, 1, 'Palkia'),
(1, 3, 12, 10, 'arzeus'),
(2, 2, 13, 2, 'Bolbasaur');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE `tipo` (
  `idTipo` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`idTipo`, `nombre`) VALUES
(1, 'dragon'),
(2, 'Agua'),
(3, 'Bicho'),
(4, 'Eléctrico'),
(5, 'Fantasma'),
(6, 'Fuego'),
(7, 'Hada'),
(8, 'Hielo'),
(9, 'Lucha'),
(10, 'Normal'),
(11, 'Planta'),
(12, 'Psíquico'),
(13, 'Roca'),
(14, 'Siniestro'),
(15, 'Tierra'),
(16, 'Veneno'),
(17, 'Volador'),
(18, 'Acero');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  ADD PRIMARY KEY (`idEntrenador`);

--
-- Indices de la tabla `habilidad`
--
ALTER TABLE `habilidad`
  ADD PRIMARY KEY (`idHabilidad`);

--
-- Indices de la tabla `pokemon`
--
ALTER TABLE `pokemon`
  ADD PRIMARY KEY (`idPokemon`),
  ADD KEY `FKom5uk0jdk70urwieqxxsqm420` (`entrenador_id`),
  ADD KEY `FKtju9v6pdsuajty2ab6pe92rq6` (`habilidad_id`),
  ADD KEY `FKebybqohtcb575ax7vimcr3e45` (`tipo_id`);

--
-- Indices de la tabla `tipo`
--
ALTER TABLE `tipo`
  ADD PRIMARY KEY (`idTipo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  MODIFY `idEntrenador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `habilidad`
--
ALTER TABLE `habilidad`
  MODIFY `idHabilidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `pokemon`
--
ALTER TABLE `pokemon`
  MODIFY `idPokemon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `tipo`
--
ALTER TABLE `tipo`
  MODIFY `idTipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pokemon`
--
ALTER TABLE `pokemon`
  ADD CONSTRAINT `FKebybqohtcb575ax7vimcr3e45` FOREIGN KEY (`tipo_id`) REFERENCES `tipo` (`idTipo`),
  ADD CONSTRAINT `FKom5uk0jdk70urwieqxxsqm420` FOREIGN KEY (`entrenador_id`) REFERENCES `entrenador` (`idEntrenador`),
  ADD CONSTRAINT `FKtju9v6pdsuajty2ab6pe92rq6` FOREIGN KEY (`habilidad_id`) REFERENCES `habilidad` (`idHabilidad`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
